package us.scoreme.locationpicker;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;
import android.widget.Toast;

public class alarm extends BroadcastReceiver {

    WifiManager wifiManager;

    @Override
    public void onReceive(Context context, Intent intent) {

        Toast.makeText(context, "Alarm Triggered hoss!", Toast.LENGTH_LONG).show();
        Log.e("test","alarm set!");

        wifiManager = (WifiManager) context.getSystemService(context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        if(wifiInfo.getSupplicantState()!=null) {
            String supState = wifiInfo.getSupplicantState().toString();

            if(wifiInfo.getBSSID()!=null){
                String BSSID = wifiInfo.getBSSID();
                Log.e("wifi state",supState+":"+BSSID);
            }
            else {
                Log.e("wifi state","not connected");
            }
        }
        String network = String.valueOf(wifiInfo.getNetworkId());
        Log.e("network",network);

    }


        //Intent scan = new Intent(context, WiFiDemo.class);
        //scan.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //context.startActivity(scan);

}



