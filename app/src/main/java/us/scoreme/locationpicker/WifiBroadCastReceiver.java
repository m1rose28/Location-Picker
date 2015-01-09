package us.scoreme.locationpicker;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import java.net.URLEncoder;

public class WifiBroadCastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent)
    {
        Context appContext = context.getApplicationContext();

        String i=intent.toString();
        String e=intent.getExtras().toString();
        String link="http://www.scoreme.us/a.php?view=1";

        Log.e("on receiver", "wifi change detected:"+i+e);
        Toast.makeText(context, "wifi change detected:"+i+e, Toast.LENGTH_SHORT).show();


        Intent resultIntent = new Intent(Intent.ACTION_VIEW);
        resultIntent.setData(Uri.parse(link));

        PendingIntent pending = PendingIntent.getActivity(appContext, 0, resultIntent, PendingIntent.FLAG_ONE_SHOT);

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.myicon)
                        .setContentTitle("New wifi change")
                        .setContentText("View latest details...")
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
