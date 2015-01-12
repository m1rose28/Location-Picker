package us.scoreme.locationpicker;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.IBinder;
import android.util.Log;

import java.net.URLEncoder;
import java.util.List;

public class wifiService extends Service {

    WifiManager mainWifiObj;
    WifiScanReceiver wifiReciever;
    String wifis[];
    int mStartMode;       // indicates how to behave if the service is killed
    IBinder mBinder;      // interface for clients that bind
    boolean mAllowRebind; // indicates whether onRebind should be used

    @Override
    public void onCreate() {
        // The service is being created
        Log.e("scan:","creating scan service");
        mainWifiObj = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        wifiReciever = new WifiScanReceiver();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // The service is starting, due to a call to startService()
        Log.e("scan:","starting scan service");
        mainWifiObj.startScan();
        return mStartMode;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // A client is binding to the service with bindService()
        return mBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        // All clients have unbound with unbindService()
        return mAllowRebind;
    }

    @Override
    public void onRebind(Intent intent) {
        // A client is binding to the service with bindService(),
        // after onUnbind() has already been called
    }

    @Override
    public void onDestroy() {
        // The service is no longer used and is being destroyed
    }

    public class WifiScanReceiver extends BroadcastReceiver {

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
        }
    }

}