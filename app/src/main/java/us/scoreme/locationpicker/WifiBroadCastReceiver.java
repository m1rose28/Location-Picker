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
import java.util.Iterator;
import java.util.Set;

public class WifiBroadCastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent2)
    {
        String i=intent2.toString();
        String e=intent2.getExtras().toString();
        String link="http://www.scoreme.us/a.php?view=1";

        Bundle extras = intent2.getExtras();
        Set<String> ks = extras.keySet();
        Iterator<String> iterator = ks.iterator();
        while (iterator.hasNext()) {
            Log.e("KEY", iterator.next());
        }

        Log.e("text", "wifi change detected!" + i);
        Log.e("text", "details:"+e);

        Toast.makeText(context, "wifi change detected:"+i+e, Toast.LENGTH_SHORT).show();

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
        Intent myServiceIntent = new Intent(appContext, httpRequest2.class);
        String data=i+e;
        String encodedData= URLEncoder.encode(data);

        myServiceIntent.putExtra("STRING_I_NEED",encodedData);
        myServiceIntent.putExtra("URL", url);
        appContext.startService(myServiceIntent);

    }


}
