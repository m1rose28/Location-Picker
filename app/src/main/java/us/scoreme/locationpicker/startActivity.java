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

public class startActivity extends Activity implements httpReply {

    private PendingIntent pendingIntent;

    @Override
    public void updateActivity(String result){
        Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
    }

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
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }

    public void startSecondActivity(View view) {
        Intent intent = new Intent(this, secondActivity.class);
        startActivity(intent);
    }

    public void startTonyaActivity(View view) {
        Intent intent = new Intent(this, tonyaActivity.class);
        startActivity(intent);
    }

    public void startloginActivity(View view) {
        Intent intent = new Intent(this, gloginActivity.class);
        startActivity(intent);
    }

    public void sendUrl(View view){
        String a="43";
        new httpRequest(this).execute("http://www.scoreme.us/a.php",a,"apple");
    }

}


