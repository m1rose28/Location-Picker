package us.scoreme.locationpicker;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by markrose on 12/31/14.
 */

public class alarm extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        Toast.makeText(context, "Alarm Triggered", Toast.LENGTH_LONG).show();
        Log.e("test","alarm set!");
     }


}
