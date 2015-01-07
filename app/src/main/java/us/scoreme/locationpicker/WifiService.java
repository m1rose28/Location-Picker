package us.scoreme.locationpicker;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class WifiService extends Service
{

    WifiBroadCastReceiver brod;
    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }
    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);

        //brod=new WifiBroadCastReceiver();
        //this.registerReceiver(brod, new IntentFilter(WifiManager.SUPPLICANT_STATE_CHANGED_ACTION));
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
    }
}