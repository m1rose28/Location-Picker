package us.scoreme.locationpicker;

import android.app.NotificationManager;
import android.app.PendingIntent;
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
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import java.net.URLEncoder;
import java.util.List;

public class WifiBroadCastReceiver extends BroadcastReceiver {

    WifiManager mainWifi;
    ConnectivityManager connection;
    public String lats;
    public String lngs;

    @Override
    public void onReceive(Context context, Intent intent) {
        String i1 = intent.getAction();
        Bundle bundle = intent.getExtras();
        String x1="details:";
        String bundleDetails;
        String myLocation = sph.getSharedPreferenceString(context, "myLocation", "unknown");

        if(bundle!=null){
            for (String key : bundle.keySet()) {
                Object value = bundle.get(key);
                bundleDetails=String.format("%s %s (%s)", key,
                        value.toString(), value.getClass().getName());
                Log.d("wifitag", bundleDetails);
                x1=x1+bundleDetails+",";
            }
        }

        mainWifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);

        connection = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo activeNetwork = connection.getActiveNetworkInfo();
        NetworkInfo.State state=activeNetwork.getState();
        String state1=String.valueOf(state);
        String type=activeNetwork.getTypeName();
        Log.e("state",state1+type);

        if(state1.equals("CONNECTED") && type.equals("WIFI")){
            final WifiInfo wifinfo = mainWifi.getConnectionInfo();
            String mynet = wifinfo.getSSID();
            Log.e("state",mynet);
            if(!myLocation.equals(mynet)){
                sph.setSharedPreferenceString(context, "myLocation", mynet);
                x1=x1+"welcometo:"+mynet;
            }
         }

        if(type.equals("MOBILE")){
            Log.e("state","you're connected to mobile");
            int myLocationCheck=Integer.parseInt((sph.getSharedPreferenceString(context, "myLocationCheck", "0")));
            myLocationCheck++;
            sph.setSharedPreferenceString(context, "myLocationCheck", String.valueOf(myLocationCheck));
            x1=x1+"seeya?";
        }

        String scannow = sph.getSharedPreferenceString(context, "scannow", "no");

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

        //if the intent has scan results go ahead and get that

        if (i1.equals("android.net.wifi.SCAN_RESULTS") && scannow.equals("yes")) {

            List<ScanResult> wifiList = mainWifi.getScanResults();
            Log.e("wifilist", wifiList.toString());
            String locationFound="0";

            for (int i = 0; i < wifiList.size(); i++) {

                ScanResult x = wifiList.get(i);

                if(x.BSSID.equals(myLocation)){
                    locationFound="1";
                    Log.e("state","not connected but I found you");
                }

                Log.e("scan", x.BSSID);
                Log.e("WifiBroadCastReceiver-SSID", x.SSID);
                Log.e("WifiBroadCastReceiver-BSSID", x.BSSID);
                Log.e("WifiBroadCastReceiver-capabilities", x.capabilities);
                Log.e("WifiBroadCastReceiver-frequency", Integer.toString(x.frequency));
                Log.e("WifiBroadCastReceiver-level", Integer.toString(x.level));
                Log.e("WifiBroadCastReceiver-timestamp", Long.toString(x.timestamp));
                long unixTime = System.currentTimeMillis() / 1000L;

                String data = "SSID=" +
                        URLEncoder.encode(x.SSID) + "&BSSID=" +
                        URLEncoder.encode(x.BSSID) + "&capabilities=" +
                        URLEncoder.encode(x.capabilities) + "&frequency=" +
                        URLEncoder.encode(Integer.toString(x.frequency)) + "&level=" +
                        URLEncoder.encode(Integer.toString(x.level)) + "&ts=" +
                        URLEncoder.encode(Long.toString(unixTime)) +
                        "&userid=" + sph.getSharedPreferenceString(context, "userid", "0")+
                        "&lat="+lats +
                        "&lng="+lngs;

                Log.e("getlatlng?", data);

                String url = "http://www.scoreme.us/a.php";

                Intent myServiceIntent = new Intent(context, httpRequest2.class);
                myServiceIntent.putExtra("event","scan");
                myServiceIntent.putExtra("data",data);
                myServiceIntent.putExtra("url", url);
                context.startService(myServiceIntent);
                sph.setSharedPreferenceString(context, "scannow", "no");
            }

            if(locationFound.equals("0")){
                sph.setSharedPreferenceString(context, "myLocation", "unknown");
                Log.e("state","looks like you really exited");
            }

        }

        //this checks for supplicant state change

        if(i1.equals("android.net.wifi.supplicant.STATE_CHANGE")){
            String url = "http://www.scoreme.us/a.php";
            String ts = String.valueOf(System.currentTimeMillis() / 1000L);
            String userid = sph.getSharedPreferenceString(context, "userid", "0");

            String data = "userid=" + userid +
                    "&ts=" + ts +
                    "&changeevent=" + URLEncoder.encode(i1)+URLEncoder.encode(" supplicant change ")+
                    "&lat="+lats +
                    "&lng="+lngs;

            Intent myServiceIntent = new Intent(context, httpRequest2.class);
            myServiceIntent.putExtra("event", "change");
            myServiceIntent.putExtra("data", data);
            myServiceIntent.putExtra("url", url);
            context.startService(myServiceIntent);

            String lastScanTimeString = sph.getSharedPreferenceString(context, "scantime", "0");

            int elapsedTime = 0;
            sph.setSharedPreferenceString(context, "scannow", "yes");


            if (!lastScanTimeString.equals("0")) {
                int lastScanTimeLong = Integer.valueOf(lastScanTimeString);
                int timeNowLong = Integer.valueOf(ts);
                elapsedTime = timeNowLong - lastScanTimeLong;
            }
            if (elapsedTime > 60 || elapsedTime == 0) {

                mainWifi.startScan();

            } else {
                Log.e("scan aborted", "you already have a fresh scan - go fish");
            }

        }

        if (i1.equals("android.net.conn.CONNECTIVITY_CHANGE")) {

            Toast.makeText(context, "wifi change detected...", Toast.LENGTH_SHORT).show();

            Intent resultIntent = new Intent(context, webview.class);
            resultIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            PendingIntent pending = PendingIntent.getActivity(context, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);

            NotificationCompat.Builder mBuilder =
                    new NotificationCompat.Builder(context)
                            .setSmallIcon(R.drawable.myicon)
                            .setContentTitle("New wifi change")
                            .setContentText("view latest details...")
                            .setContentIntent(pending);

            NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

            mNotificationManager.notify(1, mBuilder.build());

            String url = "http://www.scoreme.us/a.php";
            String ts = String.valueOf(System.currentTimeMillis() / 1000L);
            String userid = sph.getSharedPreferenceString(context, "userid", "0");

            String data = "userid=" + userid +
                    "&ts=" + ts +
                    "&changeevent=" + URLEncoder.encode(i1) + URLEncoder.encode(x1)+
                    "&lat="+lats +
                    "&lng="+lngs;

            Intent myServiceIntent = new Intent(context, httpRequest2.class);
            myServiceIntent.putExtra("event", "change");
            myServiceIntent.putExtra("data", data);
            myServiceIntent.putExtra("url", url);
            context.startService(myServiceIntent);

            String lastScanTimeString = sph.getSharedPreferenceString(context, "scantime", "0");

            int elapsedTime = 0;
            sph.setSharedPreferenceString(context, "scannow", "yes");


            if (!lastScanTimeString.equals("0")) {
                int lastScanTimeLong = Integer.valueOf(lastScanTimeString);
                int timeNowLong = Integer.valueOf(ts);
                elapsedTime = timeNowLong - lastScanTimeLong;
            }
            if (elapsedTime > 60 || elapsedTime == 0) {

                mainWifi.startScan();

            } else {
                Log.e("scan aborted", "you already have a fresh scan - go fish");
            }

        }
    }
}
