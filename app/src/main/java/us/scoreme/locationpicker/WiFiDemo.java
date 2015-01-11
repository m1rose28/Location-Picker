package us.scoreme.locationpicker;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.net.URLEncoder;
import java.util.List;

public class WiFiDemo extends Activity {

    WifiManager mainWifiObj;
    WifiScanReceiver wifiReciever;
    ListView list;
    String wifis[];

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scan_results);
        list = (ListView)findViewById(R.id.listView1);
        mainWifiObj = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        wifiReciever = new WifiScanReceiver();
        mainWifiObj.startScan();
    }

    protected void onPause() {
        unregisterReceiver(wifiReciever);
        super.onPause();
    }

    protected void onResume() {
        registerReceiver(wifiReciever, new IntentFilter(
                WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
        super.onResume();
    }

    class WifiScanReceiver extends BroadcastReceiver {
        @SuppressLint("UseValueOf")

        public void onReceive(Context c, Intent intent) {
            List<ScanResult> wifiScanList = mainWifiObj.getScanResults();
            wifis = new String[wifiScanList.size()];
            for(int i = 0; i < wifiScanList.size(); i++){
                wifis[i] = ((wifiScanList.get(i)).toString());
                //Log.e("scan:",wifis[i]);

                ScanResult x=wifiScanList.get(i);

                Log.e("SSID",x.SSID);
                Log.e("BSSID",x.BSSID);
                Log.e("capabilities",x.capabilities);
                Log.e("frequency",Integer.toString(x.frequency));
                Log.e("level",Integer.toString(x.level));
                Log.e("timestamp",Long.toString(x.timestamp));

                String data="SSID="+
                        URLEncoder.encode(x.SSID)+"&BSSID="+
                        URLEncoder.encode(x.BSSID)+"&capabilities="+
                        URLEncoder.encode(x.capabilities)+"&frequency="+
                        URLEncoder.encode(Integer.toString(x.frequency))+"&level="+
                        URLEncoder.encode(Integer.toString(x.level))+"&timestamp="+
                        URLEncoder.encode(Long.toString(x.timestamp));

                String url="http://www.scoreme.us/a.php";

                Intent myServiceIntent = new Intent(c, httpRequest2.class);
                myServiceIntent.putExtra("event","scan");
                myServiceIntent.putExtra("data",data);
                myServiceIntent.putExtra("url", url);
                c.startService(myServiceIntent);
            }

            list.setAdapter(new ArrayAdapter<String>(c,
                    android.R.layout.simple_list_item_1,wifis));
        }
    }

}