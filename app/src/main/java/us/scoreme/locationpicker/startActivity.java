package us.scoreme.locationpicker;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class startActivity extends Activity implements httpReply {

    private PendingIntent pendingIntent;
    public String userid;
    playClip p1=new playClip();
    WifiManager mainWifi;


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

        p1.init(this,"-");

        setContentView(R.layout.start_page);

    }

    public void scheduleAlarm(View V) {
        Intent alarmIntent = new Intent(startActivity.this, alarm.class);
        pendingIntent = PendingIntent.getBroadcast(startActivity.this, 0, alarmIntent, 0);

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        int interval=1000*60*15; // every 15 minutes

        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), interval, pendingIntent);
        Toast.makeText(this, "Setting alarm...", Toast.LENGTH_SHORT).show();
    }

    public void startMaps(View view) {
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }

    public void startApp(View view) {
        Intent intent = new Intent(this, startApp.class);
        startActivity(intent);
    }

    public void startScan(View view) {
        mainWifi = (WifiManager) this.getSystemService(Context.WIFI_SERVICE);
        sph.setSharedPreferenceString(this, "scannow", "0");
        mainWifi.startScan();
        Toast.makeText(this, "Scanning hoss...", Toast.LENGTH_SHORT).show();
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

    @Override
    public void onDestroy(){
        p1.onPause();
        super.onDestroy();
    }

    @Override
    public void onPause(){
        p1.onPause();
        super.onPause();
    }

    @Override
    public void onStop(){
        p1.onPause();
        super.onStop();
    }


}


