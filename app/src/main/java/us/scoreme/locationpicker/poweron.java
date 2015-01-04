package us.scoreme.locationpicker;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by markrose on 1/2/15.
 */
public class poweron extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // assumes WordService is a registered service
        int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
        String s=String.valueOf(status);
        Log.e("mytest", "power:"+s);
        Toast.makeText(context, "Power:"+s, Toast.LENGTH_SHORT).show();
    }
}