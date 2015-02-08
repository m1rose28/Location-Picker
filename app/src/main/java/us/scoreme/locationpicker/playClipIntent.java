package us.scoreme.locationpicker;

import android.app.IntentService;
import android.content.Intent;

/**
 * Created by markrose on 1/8/15.
 */
public class playClipIntent extends IntentService {

    String toSpeak1;
    playClip p2=new playClip();

    public playClipIntent() {
        super(playClipIntent.class.getName());
    }

    protected void onHandleIntent(Intent intent) {
        toSpeak1=intent.getStringExtra("toSpeak");
        p2.init(this,toSpeak1);
    }

}

