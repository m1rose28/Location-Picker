package us.scoreme.locationpicker;

import android.app.Activity;
import android.app.PendingIntent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class secondActivity extends Activity implements httpReply {

    private PendingIntent pendingIntent;

    @Override
    public void updateActivity(String result){
        Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_activity);
    }

    public void sendUrl1(View view){
        String a="43";
        new httpRequest(this).execute("http://www.scoreme.us/a.php?r=2",a,"apple");
    }

}


