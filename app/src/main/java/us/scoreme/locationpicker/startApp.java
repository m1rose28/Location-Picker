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
    public String lat;
    public String lng;
    public String name;

    ArrayList<String> addressList1 = new ArrayList<String>();

    public String addressList;

    @Override
    public void updateActivity(String result){
        Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userid=sph.getSharedPreferenceString(this,"userid","0");
        addressList=sph.getSharedPreferenceString(getApplicationContext(),"addressList","[]");

        if(userid.equals("0")){
            Intent intent = new Intent(this, coverActivity.class);
            startActivity(intent);
        }

        Intent intent = getIntent();
        String mode = intent.getStringExtra("mode");

        if(mode==null){mode="nothing";}

        if(mode.equals("edit")) {
            lat = intent.getStringExtra("lat");
            lng = intent.getStringExtra("lng");
            name = intent.getStringExtra("name");
            }

        try {
            JSONArray addresses = new JSONArray(addressList);

            if(mode.equals("new")) {
                lat = intent.getStringExtra("lat");
                lng = intent.getStringExtra("lng");
                name = intent.getStringExtra("name");

                JSONObject newadd=new JSONObject();
                newadd.put("name",name);
                newadd.put("lat",lat);
                newadd.put("lng",lng);
                addresses.put(addresses.length(), newadd);
                String newaddarray=addresses.toString();
                sph.setSharedPreferenceString(getApplicationContext(),"addressList",newaddarray);
            }

            if(mode.equals("delete")) {
                name = intent.getStringExtra("name");
            }

            for(int i=0;i<addresses.length(); i++){

                try {
                    JSONObject adddetail = addresses.getJSONObject(i);

                    String name1 = adddetail.getString("name");

                    if(name1.equals(name) && mode.equals("edit")){
                        adddetail.put("lat",lat);
                        adddetail.put("lng",lng);
                        addresses.put(i, adddetail);
                        String newaddarray=addresses.toString();
                        sph.setSharedPreferenceString(getApplicationContext(),"addressList",newaddarray);
                    }

                    if(name1.equals(name) && mode.equals("delete")){
                        addresses.remove(i);
                        String newaddarray=addresses.toString();
                        sph.setSharedPreferenceString(getApplicationContext(),"addressList",newaddarray);
                        break;
                    }

                    addressList1.add(name1);


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

        addressList=sph.getSharedPreferenceString(getApplicationContext(),"addressList","[]");

        try {
            JSONArray addressesEdit = new JSONArray(addressList);

            Log.e("array",addressesEdit.toString());

            for(int i=0;i<addressesEdit.length(); i++){

                try {
                    JSONObject adddetailEdit = addressesEdit.getJSONObject(i);
                    name = adddetailEdit.getString("name");

                    if(name.equals(item)){
                        lat = adddetailEdit.getString("lat");
                        lng = adddetailEdit.getString("lng");
                        Log.e("clicked","edit:"+name+lat+lng);

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


