package us.scoreme.locationpicker;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class WifiBroadCastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent)
    {
        String id = intent.getStringExtra("id");

        String i=intent.toString();
        String e=intent.getExtras().toString();

        Log.e("on receiver", "wifi change detected:"+i+e);
        Toast.makeText(context, "wifi change detected:"+i+e, Toast.LENGTH_SHORT).show();

        Context appContext = context.getApplicationContext();

        Intent newIntent = new Intent(appContext, secondActivity.class);
        newIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        appContext.startActivity(newIntent);
    }
}
