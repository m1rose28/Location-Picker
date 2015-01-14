package us.scoreme.locationpicker;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.net.URLEncoder;

public class WifiBroadCastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent)
    {

        String i=intent.toString();

        Bundle bundle=intent.getExtras();

        for (String key : bundle.keySet()) {
            Object value = bundle.get(key);
            i=i+":"+key+"->"+value.toString()+":";
            Log.e("key|"+key+":", value.toString());
        }

        Log.e("wifi change detected!", i);


        //Toast.makeText(context, "wifi change detected", Toast.LENGTH_SHORT).show();

        Context appContext = context.getApplicationContext();

        Intent resultIntent = new Intent(appContext, webview.class);
        resultIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        //Intent resultIntent = new Intent(Intent.ACTION_VIEW);
        //resultIntent.setData(Uri.parse(link));

        PendingIntent pending = PendingIntent.getActivity(context, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(appContext)
                        .setSmallIcon(R.drawable.myicon)
                        .setContentTitle("New wifi change")
                        .setContentText("view latest details...")
                        .setContentIntent(pending);

        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        mNotificationManager.notify(1, mBuilder.build());

        String url="http://www.scoreme.us/a.php";
        String ts=String.valueOf(System.currentTimeMillis() / 1000L);
        String userid=sph.getSharedPreferenceString(appContext,"userid","0");

        String data= "userid="+userid+"&ts="+ts+"&changeevent="+URLEncoder.encode(i);

        Intent myServiceIntent = new Intent(appContext, httpRequest2.class);
        myServiceIntent.putExtra("event","change");
        myServiceIntent.putExtra("data",data);
        myServiceIntent.putExtra("url", url);
        appContext.startService(myServiceIntent);

        String lastScanTimeString=sph.getSharedPreferenceString(appContext,"scantime","0");

        Log.e("lastScanTimeString",lastScanTimeString);
        int elapsedTime=0;

        if(!lastScanTimeString.equals("0")) {
            int lastScanTimeLong = Integer.valueOf(lastScanTimeString);
            int timeNowLong = Integer.valueOf(ts);
            elapsedTime = timeNowLong-lastScanTimeLong;
            Log.e("elapsed time",String.valueOf(elapsedTime));
         }
        if(elapsedTime>60 || elapsedTime==0){
            Intent scan = new Intent(appContext, WiFiDemo.class);
            scan.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            appContext.startActivity(scan);
            sph.setSharedPreferenceString(appContext,"scantime",ts);
            Log.e("lastscan",ts);
            Log.e("WifiBroadCastReceiver:","ok moving on");
        }

        else {
            Log.e("scan aborted", "you already have a fresh scan - go fish");
        }

    }

}
