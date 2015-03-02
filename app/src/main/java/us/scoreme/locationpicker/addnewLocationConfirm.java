package us.scoreme.locationpicker;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.net.URLEncoder;

public class addnewLocationConfirm extends Activity implements httpReply {

    private PendingIntent pendingIntent;
    public String userid;
    public String T=this.getClass().getSimpleName();
    public ProgressBar spinner;
    WifiManager mainWifi;

    @Override
    public void updateActivity(String result){
        Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
        sph.setSharedPreferenceString(this, "newscangroup", "0");
        Intent intent = new Intent(this, whereamI.class);
        startActivity(intent);
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

        setContentView(R.layout.confirm_new_location);
        spinner = (ProgressBar)findViewById(R.id.progressBar1);
        spinner.setVisibility(View.VISIBLE);

        sendUrl();
    }

    public void sendUrl(){

        String url1="http://www.scoreme.us/a.php?view=6&userid=113006890674403997127" +
                "&SSID=" + URLEncoder.encode(sph.getSharedPreferenceString(this, "newwifi", "0")) +
                "&BSSID=" + URLEncoder.encode(sph.getSharedPreferenceString(this, "newwifibssid", "0")) +
                "&locationtype=" + URLEncoder.encode(sph.getSharedPreferenceString(this, "locationtype", "general")) +
                "&locationname=" + URLEncoder.encode(sph.getSharedPreferenceString(this, "locationname", "0")) +
                "&newscangroup="+sph.getSharedPreferenceString(this, "newscangroup", "0") +
                "&lat="+sph.getSharedPreferenceString(this, "newlat", "0") +
                "&lng="+sph.getSharedPreferenceString(this, "newlng", "0");
                Log.e(T,url1);

        new httpRequest(this).execute(url1);
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


