package us.scoreme.locationpicker;

import android.os.AsyncTask;

/**
 * Created by markrose on 12/28/14.
 */

//new getAddress().execute(url1, url2, url3);
//Params, the type of the parameters sent to the task upon execution.
//Progress, the type of the progress units published during the background computation.
//Result, the type of the result of the background computation.

public class getAddress extends AsyncTask<Long, Integer, Long> {

    public double mylat=0;
    public double mylng=0;

    protected Long doInBackground(Long... addresses) {
        int count = addresses.length;
        long totalSize = 0;
        for (int i = 0; i < count; i++) {
            publishProgress((int) ((i / (float) count) * 100));
            // Escape early if cancel() is called
            if (isCancelled()) break;
        }

        mylat=addresses[0];
        mylng=addresses[1];
        //getMyLocationAddress();
        return totalSize;
    }

    protected void onProgressUpdate(Integer... progress) {
    }

    protected void onPostExecute(Long result) {
    }


}



