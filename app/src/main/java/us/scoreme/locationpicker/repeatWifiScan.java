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

import java.net.URLEncoder;

public class repeatWifiScan extends BroadcastReceiver {

    WifiManager wifiManager;
    public String BSSID;
    public String SSID;
    public String lats;
    public String lngs;
    public String T=this.getClass().getSimpleName();
    public String isConnected="0";

    @Override
    public void onReceive(Context context, Intent intent) {

        Log.e(T,"doing a scheduled alarm geo ssid conntected update");
        //get last known location data for context
        LocationManager locationManager = (LocationManager) context.getSystemService(context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        String provider = locationManager.getBestProvider(criteria, true);
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
                SSID = SSID.replace("\"", "");
                isConnected="1";
            }
            else {
                //Log.e("wifi state","not connected");
                BSSID="none";
                SSID="none";
            }
        }

        String network = String.valueOf(wifiInfo.getNetworkId());
        long unixTime = System.currentTimeMillis() / 1000L;

        String data = "SSID=" + URLEncoder.encode(SSID) +
                "&BSSID=" + URLEncoder.encode(BSSID) +
                "&isConnected=" + URLEncoder.encode(isConnected) +
                "&SSIDConnection=" + URLEncoder.encode(SSID) +
                "&BSSIDConnection=" + URLEncoder.encode(BSSID) +
                "&userid=" + sph.getSharedPreferenceString(context, "userid", "0")+
                "&lat="+lats +
                "&lng="+lngs +
                "&ts=" + URLEncoder.encode(Long.toString(unixTime));

        String url = "http://www.scoreme.us/a.php";

        Intent myServiceIntent = new Intent(context, httpRequest2.class);
        myServiceIntent.putExtra("event","geowifiping");
        myServiceIntent.putExtra("data",data);
        myServiceIntent.putExtra("url", url);
        context.startService(myServiceIntent);
    }

}



