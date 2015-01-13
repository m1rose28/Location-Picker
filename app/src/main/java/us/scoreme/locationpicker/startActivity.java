package us.scoreme.locationpicker;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class startActivity extends Activity implements httpReply {

    private PendingIntent pendingIntent;
    public String userid;

    @Override
    public void updateActivity(String result){
        Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userid=sph.getSharedPreferenceString(this,"userid","0");

        if(userid.equals("0")){
            Intent intent = new Intent(this, coverActivity.class);
            startActivity(intent);
        }

        setContentView(R.layout.start_page);

        //Intent intent = new Intent(this, wifiService.class);
        //startService(intent);
    }

    public void scheduleAlarm(View V) {
        Intent alarmIntent = new Intent(startActivity.this, alarm.class);
        pendingIntent = PendingIntent.getBroadcast(startActivity.this, 0, alarmIntent, 0);

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        int interval=1000*60*1; // every 15 minutes

        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), interval, pendingIntent);
        Toast.makeText(this, "Alarm Set", Toast.LENGTH_SHORT).show();
    }

    public void startMaps(View view) {
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }

    public void startScan(View view) {
        Toast.makeText(this, "starting scan...", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, WiFiDemo.class);
        startActivity(intent);
    }

    public void startloginActivity(View view) {
        Intent intent = new Intent(this, gloginActivity.class);
        startActivity(intent);
    }

    public void coverActivity(View view) {
        Intent intent = new Intent(this, coverActivity.class);
        startActivity(intent);
    }

    public void viewdata(View view) {
        Intent intent = new Intent(this, webview.class);
        startActivity(intent);
    }

    public void sendUrl(View view){
        String a="43";
        new httpRequest(this).execute("http://www.scoreme.us/a.php?r=1",a,"apple");
    }

}


