package us.scoreme.locationpicker;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import java.net.URLEncoder;
import java.util.List;

public class WifiBroadCastReceiver extends BroadcastReceiver {

    WifiManager mainWifi;

    @Override
    public void onReceive(Context context, Intent intent) {
        String i1 = intent.toString();
        mainWifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);

        String scannow = sph.getSharedPreferenceString(context, "scannow", "no");

        if (i1.equals("Intent { act=android.net.wifi.SCAN_RESULTS flg=0x4000010 cmp=us.scoreme.locationpicker/.WifiBroadCastReceiver }") && scannow.equals("yes")) {

            Toast.makeText(context, "getting wifiscan results...", Toast.LENGTH_SHORT).show();

            List<ScanResult> wifiList = mainWifi.getScanResults();
            Log.e("wifilist", wifiList.toString());

            for (int i = 0; i < wifiList.size(); i++) {

                ScanResult x = wifiList.get(i);

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
                        "&userid=" + sph.getSharedPreferenceString(context, "userid", "0");

                String url = "http://www.scoreme.us/a.php";

                Intent myServiceIntent = new Intent(context, httpRequest2.class);
                myServiceIntent.putExtra("event","scan");
                myServiceIntent.putExtra("data",data);
                myServiceIntent.putExtra("url", url);
                context.startService(myServiceIntent);
                sph.setSharedPreferenceString(context, "scannow", "no");
            }

        }

        if (i1.equals("Intent { act=android.net.conn.CONNECTIVITY_CHANGE flg=0x4000010 cmp=us.scoreme.locationpicker/.WifiBroadCastReceiver (has extras) }")) {

            Log.e("wifi change detected!", i1);

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

            String data = "userid=" + userid + "&ts=" + ts + "&changeevent=" + URLEncoder.encode(i1);

            Intent myServiceIntent = new Intent(context, httpRequest2.class);
            myServiceIntent.putExtra("event", "change");
            myServiceIntent.putExtra("data", data);
            myServiceIntent.putExtra("url", url);
            context.startService(myServiceIntent);

            String lastScanTimeString = sph.getSharedPreferenceString(context, "scantime", "0");

            Log.e("lastScanTimeString", lastScanTimeString);
            int elapsedTime = 0;
            sph.setSharedPreferenceString(context, "scannow", "yes");


            if (!lastScanTimeString.equals("0")) {
                int lastScanTimeLong = Integer.valueOf(lastScanTimeString);
                int timeNowLong = Integer.valueOf(ts);
                elapsedTime = timeNowLong - lastScanTimeLong;
                Log.e("elapsed time", String.valueOf(elapsedTime));
            }
            if (elapsedTime > 60 || elapsedTime == 0) {
                //Intent scan = new Intent(context, WiFiDemo.class);
                //scan.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                //context.startActivity(scan);
                //sph.setSharedPreferenceString(context, "scantime", ts);
                //Log.e("lastscan", ts);
                //Log.e("WifiBroadCastReceiver:", "ok moving on");
            } else {
                Log.e("scan aborted", "you already have a fresh scan - go fish");
            }

            mainWifi.startScan();


        }
    }
}
