package us.scoreme.locationpicker;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;

import java.net.URLEncoder;
import java.util.List;

public class WifiBroadCastReceiver extends BroadcastReceiver {

    WifiManager mainWifi;
    ConnectivityManager connection;
    public String lats="0";
    public String lngs="0";
    public String ts = String.valueOf(System.currentTimeMillis() / 1000L);
    public String mynet="none";
    public String mynetb="none";
    public String T=this.getClass().getSimpleName();
    public String bssidlist="";
    public String changetype;

    @Override
    public void onReceive(Context context, Intent intent) {
        String i1 = intent.getAction();
        Bundle bundle = intent.getExtras();
        String x1="";
        String bundleDetails;
        String userid = sph.getSharedPreferenceString(context, "userid", "0");

        if(bundle!=null){
            for (String key : bundle.keySet()) {
                Object value = bundle.get(key);
                bundleDetails=String.format("%s %s (%s)", key,
                        value.toString(), value.getClass().getName());
                x1=x1+":"+bundleDetails+",";
            }
        }

        mainWifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        connection = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        final NetworkInfo activeNetwork = connection.getActiveNetworkInfo();
        NetworkInfo.State state=activeNetwork.getState();
        String state1=String.valueOf(state);
        String type=activeNetwork.getTypeName();

        if(state1.equals("CONNECTED") && type.equals("WIFI")){
            WifiInfo wifinfo = mainWifi.getConnectionInfo();
            mynet = wifinfo.getSSID();
            mynet = mynet.replace("\"", "");
            mynetb = wifinfo.getBSSID();
            x1=x1+":connected to wifi";
         }

        if(type.equals("MOBILE")){
            x1=x1+":connected to mobile";

        }

        if(i1.equals("android.net.conn.CONNECTIVITY_CHANGE")){
            changetype="Connectivity change";
        }

        if(i1.equals("android.net.wifi.SCAN_RESULTS")){
            changetype="New scan";
        }

        //get last known location data for context

        LocationManager locationManager = (LocationManager) context.getSystemService(context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        String provider = locationManager.getBestProvider(criteria, true);
        Location location1 = locationManager.getLastKnownLocation(provider);

        if (null != location1) {
            lats = String.valueOf(location1.getLatitude());
            lngs = String.valueOf(location1.getLongitude());
        } else {
            Log.e(T, "no location: rats");
        }

        //let's go ahead and get scan results

        List<ScanResult> wifiList = mainWifi.getScanResults();

        for (int i = 0; i < wifiList.size(); i++) {
            ScanResult x = wifiList.get(i);
            if (x.level < -75){
                bssidlist = bssidlist + x.SSID + "|" + x.BSSID + "|" + x.level + "##";
            }
        }

        // let's post what we learned to the server

        String url = "http://www.scoreme.us/a.php";

        String data = "userid=" + userid +
                    "&ts=" + ts +
                    "&bssidlist=" + URLEncoder.encode(bssidlist) +
                    "&changeevent=" + URLEncoder.encode(changetype) + URLEncoder.encode(x1) +
                    "&SSIDConnection=" + URLEncoder.encode(mynet) +
                    "&BSSIDConnection=" + URLEncoder.encode(mynetb) +
                    "&lat="+lats +
                    "&lng="+lngs;

         Intent myServiceIntent = new Intent(context, httpRequest2.class);
         myServiceIntent.putExtra("event", "newscan");
         myServiceIntent.putExtra("data", data);
         myServiceIntent.putExtra("url", url);
         context.startService(myServiceIntent);

        }
    }

