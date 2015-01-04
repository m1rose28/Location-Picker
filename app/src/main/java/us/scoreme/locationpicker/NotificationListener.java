package us.scoreme.locationpicker;

import android.app.Notification;
import android.content.Context;
import android.os.Bundle;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;
/**
 * Created by markrose on 1/1/15.
 */
public class NotificationListener extends NotificationListenerService {

    protected Context context;

    StatusBarNotification[] x=getActiveNotifications();

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        Log.e("yo", "posted");

        for (StatusBarNotification x : this.getActiveNotifications()) {


            Bundle extras = x.getNotification().extras;

            String title = extras.getString(Notification.EXTRA_TITLE);

            if(title!=null){
                Log.e("title",title);
            }

            String mytext = extras.getString(Notification.EXTRA_TEXT);

            if(mytext!=null){
                Log.e("text",mytext);
            }

            //if(extras.get(x.getNotification().EXTRA_TITLE) != null) {
            //    String title = extras.get(x.getNotification().EXTRA_TITLE).toString();
            //    Log.e("title:",title);
            //    String titlepart = title.substring(3, 5);

            //    if(titlepart.equals("Be")) {
            //        Log.e("at beach house:", "bingo");
            //        Intent intent = new Intent(this, myToast.class);
            //        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //        startActivity(intent);
            //    }
            //}


            //if(extras.getString(x.getNotification().EXTRA_TEXT) != null){
            //    String text=extras.getString(x.getNotification().EXTRA_TEXT);
            //    Log.e("text:",text);

            //}

            String nid=String.valueOf(x.getId());
            String app=x.getPackageName();
            String ticker=String.valueOf(x.getNotification().tickerText);

            Log.e("id:",nid);
            Log.e("package:",app);
            Log.e("ticker:",ticker);

        }
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        Log.e("yo", "removed");
    }

    public void onListenerConnected(){
        Log.e("yo", "connected");
    }


}
