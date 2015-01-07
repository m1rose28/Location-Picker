package us.scoreme.locationpicker;

import android.app.Activity;
import android.app.PendingIntent;
import android.os.Bundle;

public class gloginActivity extends Activity {
    private PendingIntent pendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.glogin_activity);
    }
}