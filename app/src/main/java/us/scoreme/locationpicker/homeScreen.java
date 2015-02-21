package us.scoreme.locationpicker;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class homeScreen extends Activity implements httpReply {

    private PendingIntent pendingIntent;
    public String userid;
    WifiManager mainWifi;
    public String T=this.getClass().getSimpleName();


    @Override
    public void updateActivity(String result){
        Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        locationData locationData=new locationData();
        userid=locationData.getUserID(this);
        scheduleAlarm();

        Log.e("T", userid);

        if(userid.equals("0")){
            Intent intent = new Intent(this, loginScreen.class);
            startActivity(intent);
        }

        setContentView(R.layout.start_page);

    }

    public void scheduleAlarm() {
        Log.e(T,"alarmset");
        Intent alarmIntent = new Intent(homeScreen.this, repeatWifiScan.class);
        pendingIntent = PendingIntent.getBroadcast(homeScreen.this, 0, alarmIntent, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        int interval=1000*60*15; // every 15 minutes
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), interval, pendingIntent);
    }


    public void myLocations(View view) {
        Intent intent = new Intent(this, myLocations.class);
        startActivity(intent);
    }

    public void startScan(View view) {
        mainWifi = (WifiManager) this.getSystemService(Context.WIFI_SERVICE);
        sph.setSharedPreferenceString(this, "scannow", "0");
        mainWifi.startScan();
        Toast.makeText(this, "Scanning for wifi...", Toast.LENGTH_SHORT).show();
    }


    public void startloginActivity(View view) {
        Intent intent = new Intent(this, logout.class);
        startActivity(intent);
    }

    public void coverActivity(View view) {
        Intent intent = new Intent(this, loginScreen.class);
        startActivity(intent);
    }

    public void viewdata(View view) {
        Intent intent = new Intent(this, viewData.class);
        startActivity(intent);
    }

    public void whereamI(View view) {
        Intent intent = new Intent(this, whereamI.class);
        startActivity(intent);
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
    }

    @Override
    public void onPause(){
        super.onPause();
    }

    @Override
    public void onStop(){
        super.onStop();
    }


}


