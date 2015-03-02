package us.scoreme.locationpicker;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

public class addnewLocation extends Activity {

    private PendingIntent pendingIntent;
    public String userid;
    WifiManager mainWifi;
    public String T=this.getClass().getSimpleName();
    EditText locationame;
    ConnectivityManager connection;
    public String mynet="0";
    public String mynetb="0";
    public String ts;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        locationData locationData=new locationData();
        userid=locationData.getUserID(this);
        long unixTime = System.currentTimeMillis() / 1000L;
        ts = String.valueOf(unixTime);

        Log.e("T", userid);

        if(userid.equals("0")){
            Intent intent = new Intent(this, loginScreen.class);
            startActivity(intent);
        }

        sph.setSharedPreferenceString(this, "newscangroup", ts);
        sph.setSharedPreferenceString(this, "locationtype", "general");
        sph.setSharedPreferenceString(this, "newwifi", "0");
        sph.setSharedPreferenceString(this, "newwifibssid", "0");
        sph.setSharedPreferenceString(this, "scannow", "0");

        mainWifi = (WifiManager) this.getSystemService(Context.WIFI_SERVICE);
        connection = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        mainWifi.startScan();

        final NetworkInfo activeNetwork = connection.getActiveNetworkInfo();
        NetworkInfo.State state=activeNetwork.getState();
        String state1=String.valueOf(state);
        String type=activeNetwork.getTypeName();

        if(state1.equals("CONNECTED") && type.equals("WIFI")){
            WifiInfo wifinfo = mainWifi.getConnectionInfo();
            mynet = wifinfo.getSSID();
            mynet = mynet.replace("\"", "");
            mynetb = wifinfo.getBSSID();
            sph.setSharedPreferenceString(this, "newwifi", mynet);
            sph.setSharedPreferenceString(this, "newwifibssid", mynetb);
        }

        setContentView(R.layout.add_new_location);

        TextView t=new TextView(this);

        t=(TextView)findViewById(R.id.connection);
        t.setText("Wifi: "+mynet);

    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.general:
                if (checked)
                    Log.e(T,"general");
                    sph.setSharedPreferenceString(this, "locationtype", "general");
                    break;
            case R.id.microlocation:
                if (checked)
                    Log.e(T,"microlocation");
                    sph.setSharedPreferenceString(this, "locationtype", "microlocation");
                break;
        }
    }

    public void addnewLocationMap(View view) {

        String locationtype=sph.getSharedPreferenceString(this, "locationtype", "general");
        locationame = (EditText)findViewById(R.id.locationname);
        String name= locationame.getText().toString();
        sph.setSharedPreferenceString(this, "locationname", name);
        Log.e(T,name+locationtype+name.length());

        if(name.length()>0) {
            Intent intent = new Intent(this, addnewLocationMap.class);
            startActivity(intent);
        }
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


