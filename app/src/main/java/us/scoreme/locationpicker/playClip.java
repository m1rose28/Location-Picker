package us.scoreme.locationpicker;

import android.content.Context;
import android.speech.tts.TextToSpeech;

import java.util.Locale;

/**
 * Created by lynnprose16 on 2/8/15.
 */
public class playClip {

    TextToSpeech ttobject;

    public void init(Context c,final String toSpeak){
        ttobject=new TextToSpeech(c,
                new TextToSpeech.OnInitListener() {
                    @Override
                    public void onInit(int status) {
                        if(status != TextToSpeech.ERROR){
                            ttobject.setLanguage(Locale.US);
                            if(!toSpeak.equals("-")){
                                ttobject.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null, "1");
                            }
                        }
                    }
                });

    }

    public void playClip(CharSequence toSpeak) {
        ttobject.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null,"1");
    }

    public void onPause(){
        if(ttobject !=null){
            ttobject.stop();
            ttobject.shutdown();
            //Log.e("state","tts shut down");
        }
    }

}


