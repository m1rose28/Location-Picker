package us.scoreme.locationpicker;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by markrose on 1/16/15.
 */
public class myLocations {

    String json = "{"
            + "  \"query\": \"Pizza\", "
            + "  \"locations\": [ 94043, 90210 ] "
            + "}";

    public void myapp() {

        Log.e("json:","part 1");

            try {
                JSONObject jsonObj = new JSONObject(json);
                String x=jsonObj.getString("query");
                Log.e("json:",x);

            } catch (JSONException e) {
                e.printStackTrace();
            }


    }
}