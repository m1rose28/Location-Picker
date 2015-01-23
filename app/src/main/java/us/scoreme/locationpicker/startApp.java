package us.scoreme.locationpicker;

import android.app.ListActivity;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class startApp extends ListActivity implements httpReply {

    private PendingIntent pendingIntent;

    public String userid;
    ArrayList<String> addressList1 = new ArrayList<String>();

    String addressList = "[\n" +
            "    {\n" +
            "        \"name\": \"home\",\n" +
            "        \"lat\": \"37.238\",\n" +
            "        \"lng\": \"-121.927\"\n" +
            "    },\n" +
            "   \n" +
            " {\n" +
            "        \"name\": \"work\",\n" +
            "        \"lat\": \"37.40\",\n" +
            "        \"lng\": \"-122.036\"\n" +
            "    }\n" +
            "]";


    @Override
    public void updateActivity(String result){
        Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userid=sph.getSharedPreferenceString(this,"userid","0");

        addressList="[]";
        if(userid.equals("0")){
            Intent intent = new Intent(this, coverActivity.class);
            startActivity(intent);
        }

        Intent intent = getIntent();
        String mode = intent.getStringExtra("mode");

        if(mode==null){mode="nothing";}

        if(mode.equals("edit")) {
            String lat = intent.getStringExtra("lat");
            String lng = intent.getStringExtra("lng");
            String name = intent.getStringExtra("name");
            Log.e("edited",lat+lng+name);
            }

        try {
            JSONArray addresses = new JSONArray(addressList);

            if(mode.equals("new")) {
                String lat = intent.getStringExtra("lat");
                String lng = intent.getStringExtra("lng");
                String name = intent.getStringExtra("name");
                addressList1.add(name);
                Log.e("added",lat+lng+name);
            }

            for(int i=0;i<addresses.length(); i++){

                try {
                    JSONObject adddetail = addresses.getJSONObject(i);
                    String name1 = adddetail.getString("name");
                    String lat = adddetail.getString("lat");
                    String lng = adddetail.getString("lng");
                    addressList1.add(name1);
                    Log.e("data",name1+lat+lng);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, addressList1);
        setListAdapter(adapter);
        setContentView(R.layout.address_list);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        String item = (String) getListAdapter().getItem(position);
        Log.e("item clicked",item);

        try {
            JSONArray addresses = new JSONArray(addressList);

            for(int i=0;i<addresses.length(); i++){

                try {
                    JSONObject adddetail = addresses.getJSONObject(i);
                    String name = adddetail.getString("name");

                    if(name.equals(item)){
                        String lat = adddetail.getString("lat");
                        String lng = adddetail.getString("lng");
                        Log.e("found a match",name+lat+lng);
                        Intent intent = new Intent(this, addLocation.class);
                        intent.putExtra("name",name);
                        intent.putExtra("lat",lat);
                        intent.putExtra("lng", lng);
                        intent.putExtra("mode", "edit");
                        startActivity(intent);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    public void newLocation(View view) {
        Intent intent = new Intent(this, addLocation.class);
        intent.putExtra("mode", "new");
        startActivity(intent);
    }


}


