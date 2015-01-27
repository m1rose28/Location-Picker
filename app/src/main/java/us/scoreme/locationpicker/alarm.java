package us.scoreme.locationpicker;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;
import android.widget.Toast;

import java.net.URLEncoder;

public class alarm extends BroadcastReceiver {

    WifiManager wifiManager;
    public String connect;
    public String BSSID;
    public String SSID;
    public String lats;
    public String lngs;

    @Override
    public void onReceive(Context context, Intent intent) {

        Toast.makeText(context, "Geo-wifi ping posted hoss!", Toast.LENGTH_LONG).show();
        Log.e("test","alarm set!");

        //get last known location data for context
        LocationManager locationManager = (LocationManager) context.getSystemService(context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        String provider = locationManager.getBestProvider(criteria, true);
        Log.e("provider", provider);
        Location location1 = locationManager.getLastKnownLocation(provider);

        if (null != location1) {
            lats = String.valueOf(location1.getLatitude());
            lngs = String.valueOf(location1.getLongitude());
        } else {
            Log.e("no location", "rats");
            lats="0";
            lngs="0";
        }

        //get wifi connection
        wifiManager = (WifiManager) context.getSystemService(context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        if(wifiInfo.getSupplicantState()!=null) {
            String supState = wifiInfo.getSupplicantState().toString();

            if(wifiInfo.getBSSID()!=null){
                BSSID = wifiInfo.getBSSID();
                SSID = wifiInfo.getSSID();
                Log.e("wifi state",supState+":"+BSSID);
                Log.e("wifi state",supState+":"+SSID);
            }
            else {
                Log.e("wifi state","not connected");
                BSSID="nada";
                SSID="nada";
            }
        }

        String network = String.valueOf(wifiInfo.getNetworkId());
        Log.e("network",network);
        long unixTime = System.currentTimeMillis() / 1000L;

        String data = "SSID=" + URLEncoder.encode(SSID) +
                "&BSSID=" + URLEncoder.encode(BSSID) +
                "&userid=" + sph.getSharedPreferenceString(context, "userid", "0")+
                "&lat="+lats +
                "&lng="+lngs +
                "&ts=" + URLEncoder.encode(Long.toString(unixTime));

        Log.e("senddata", data);

        String url = "http://www.scoreme.us/a.php";

        Intent myServiceIntent = new Intent(context, httpRequest2.class);
        myServiceIntent.putExtra("event","geowifiping");
        myServiceIntent.putExtra("data",data);
        myServiceIntent.putExtra("url", url);
        context.startService(myServiceIntent);
    }

}



