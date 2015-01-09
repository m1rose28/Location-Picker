package us.scoreme.locationpicker;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;

public class secondActivity extends Activity implements httpReply {

    private PendingIntent pendingIntent;


    @Override
    public void updateActivity(String result){
        Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
        appendLog(result);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_activity);
    }

    public void sendUrl1(View view){
        String a="43";
        String url="http://www.scoreme.us/a.php?r=3";
        Intent myServiceIntent = new Intent(getApplicationContext(), httpRequest2.class);
        myServiceIntent.putExtra("STRING_I_NEED", a);
        myServiceIntent.putExtra("URL", url);
        getApplicationContext().startService(myServiceIntent);
        new httpRequest(this).execute("http://www.scoreme.us/a.php?r=2",a,"apple");
    }

    public void viewLog(View view){
        FileInputStream fis;
        final StringBuffer storedString = new StringBuffer();

        try {
            fis = openFileInput("log1.txt");
            DataInputStream dataIO = new DataInputStream(fis);
            String strLine = null;

            if ((strLine = dataIO.readLine()) != null) {
                storedString.append(strLine);
            }

            dataIO.close();
            fis.close();
            String S=storedString.toString();
            Log.e("test",S);
        }
        catch  (Exception e) {
        }
    }

    public void appendLog(String text)
    {
        String filePath = getFilesDir().getPath().toString() + "/log1.txt";
        File logFile = new File(filePath);
        if (!logFile.exists())
        {
            try
            {
                logFile.createNewFile();
            }
            catch (IOException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        try
        {
            //BufferedWriter for performance, true to set append to file flag

            BufferedWriter buf = new BufferedWriter(new FileWriter(logFile, true));
            buf.append(text);
            buf.newLine();
            buf.close();
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


}


