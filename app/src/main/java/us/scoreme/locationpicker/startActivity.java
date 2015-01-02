package us.scoreme.locationpicker;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.GregorianCalendar;

/**
 * Created by markrose on 12/29/14.
 */

public class startActivity extends Activity {

    private PendingIntent pendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_page);
    }

    public void scheduleAlarm(View V) {
        Long time = new GregorianCalendar().getTimeInMillis() + 10 * 1000;
        Intent alarmIntent = new Intent(startActivity.this, alarm.class);
        pendingIntent = PendingIntent.getBroadcast(startActivity.this, 0, alarmIntent, 0);

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        int interval=1000*60*60*24;

        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), interval, pendingIntent);
        Toast.makeText(this, "Alarm Set", Toast.LENGTH_SHORT).show();
    }

    public void startMaps(View view) {
        // Do something in response to button
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }

    public void sendUrl(View view){
        String a="43";
        new httpRequest().execute("http://www.scoreme.us/a.html",a,"apple");
    }
}