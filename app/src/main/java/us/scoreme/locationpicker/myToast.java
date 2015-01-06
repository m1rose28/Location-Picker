package us.scoreme.locationpicker;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

public class myToast extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toast.makeText(this, "It finally worked", Toast.LENGTH_SHORT).show();
        moveTaskToBack(true);
    }
}