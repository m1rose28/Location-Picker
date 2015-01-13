package us.scoreme.locationpicker;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class alarm extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        Toast.makeText(context, "Alarm Triggered hoss!", Toast.LENGTH_LONG).show();
        Log.e("test","alarm set!");

        Intent scan = new Intent(context, WiFiDemo.class);
        scan.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(scan);

    }


}
