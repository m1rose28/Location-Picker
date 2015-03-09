package us.scoreme.locationpicker;

import android.app.Activity;
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

        Log.e("T", userid);

        if(userid.equals("0")){
            Intent intent = new Intent(this, loginScreen.class);
            startActivity(intent);
        }

        setContentView(R.layout.start_page);

    }


    public void addNewLocation(View view) {
        Intent intent = new Intent(this, addnewLocation.class);
        startActivity(intent);
    }

    public void startScan(View view) {
        mainWifi = (WifiManager) this.getSystemService(Context.WIFI_SERVICE);
        mainWifi.startScan();
        Toast.makeText(this, "Scanning for wifi...", Toast.LENGTH_SHORT).show();
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


