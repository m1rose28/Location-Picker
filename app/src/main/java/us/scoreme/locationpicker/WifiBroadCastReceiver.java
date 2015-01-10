package us.scoreme.locationpicker;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import java.net.URLEncoder;

public class WifiBroadCastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent)
    {
        String i=intent.toString();
        String link="http://www.scoreme.us/a.php?view=1";

        Bundle bundle=intent.getExtras();

        for (String key : bundle.keySet()) {
            Object value = bundle.get(key);
            Log.e("test", String.format("%s %s (%s)", key, value.toString(), value.getClass().getName()));
            i=i+":"+key+"->"+value.toString()+":";
        }

        Log.e("text", "wifi change detected!" + i);

        Toast.makeText(context, "wifi change detected", Toast.LENGTH_SHORT).show();

        Intent resultIntent = new Intent(Intent.ACTION_VIEW);
        resultIntent.setData(Uri.parse(link));

        Context appContext = context.getApplicationContext();

        PendingIntent pending = PendingIntent.getActivity(context, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(appContext)
                        .setSmallIcon(R.drawable.myicon)
                        .setContentTitle("New wifi change")
                        .setContentText("view latest details...")
                        .setContentIntent(pending);

        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        mNotificationManager.notify(1, mBuilder.build());

        String url="http://www.scoreme.us/a.php?data=1&event=";
        String encodedData= URLEncoder.encode(i);

        Intent myServiceIntent = new Intent(appContext, httpRequest2.class);
        myServiceIntent.putExtra("STRING_I_NEED",encodedData);
        myServiceIntent.putExtra("URL", url);
        appContext.startService(myServiceIntent);

        Intent scan = new Intent(appContext, WiFiDemo.class);
        scan.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        appContext.startActivity(scan);

        Log.e("test","ok moving on");


    }


}
