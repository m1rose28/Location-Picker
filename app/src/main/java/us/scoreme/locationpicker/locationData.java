package us.scoreme.locationpicker;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by lynnprose16 on 2/8/15.
 */
public class locationData {

    public double lat;
    public double lng;
    public double latdiff;
    public double lngdiff;
    public String mylocation;
    public String ssid;
    public JSONArray bssid=new JSONArray();
    public double match=0;
    public String name;
    public String addressList;
    public Integer id;
    public String userid;
    public JSONArray addresses;

    public String getUserID(Context c){
        userid=sph.getSharedPreferenceString(c,"userid","0");
        return userid;
    }

    public JSONArray addLocation(Context c, String userid, String lat,String lng,String name, String ssid, JSONArray bssid){

        addressList=sph.getSharedPreferenceString(c,"addressList","[]");

        try {
            addresses = new JSONArray(addressList);
            JSONObject newadd = new JSONObject();
            newadd.put("userid", userid);
            newadd.put("name", name);
            newadd.put("lat", lat);
            newadd.put("lng", lng);
            newadd.put("ssid", ssid);
            newadd.put("bssid", bssid);
            addresses.put(addresses.length(), newadd);
            String newaddarray = addresses.toString();
            Log.e("array",newaddarray);
            sph.setSharedPreferenceString(c, "addressList", newaddarray);

        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        return addresses;
    }

    public JSONArray editLocation(Context c, String userid, String lat,String lng,String name, String ssid, JSONArray bssid){

        addressList=sph.getSharedPreferenceString(c,"addressList","[]");

        try {
            addresses = new JSONArray(addressList);

            for(int i=0;i<addresses.length(); i++){

                try {
                    JSONObject adddetail = addresses.getJSONObject(i);

                    String id1 = adddetail.getString("userid");

                    if(id1.equals(userid)){
                        adddetail.put("lat",lat);
                        adddetail.put("lng",lng);
                        adddetail.put("name",name);
                        addresses.put(i, adddetail);
                        String newaddarray=addresses.toString();
                        sph.setSharedPreferenceString(c,"addressList",newaddarray);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        } catch (JSONException e) {
                e.printStackTrace();
        }

        return addresses;
    }

    public JSONArray deleteLocation(Context c, String name){
        addressList=sph.getSharedPreferenceString(c,"addressList","[]");

        try{
            addresses = new JSONArray(addressList);

            for(int i=0;i<addresses.length(); i++){

                try {
                    JSONObject adddetail = addresses.getJSONObject(i);

                    String name1 = adddetail.getString("name");

                    Log.e("d",name1+":"+name);

                    if(name1.equals(name)){
                        addresses.remove(i);
                        String newaddarray=addresses.toString();
                        sph.setSharedPreferenceString(c,"addressList",newaddarray);
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return addresses;
    }

    public String getLocations(Context c){
        addressList=sph.getSharedPreferenceString(c,"addressList","[]");
        return addressList;
    }

    public String amIInNetwork(Context c,List bssid){
        addressList=sph.getSharedPreferenceString(c,"addressList","[]");
        return name;
    }

    public void append_bssid(Context c,int id, String ssid, List bssid){
        addressList=sph.getSharedPreferenceString(c,"addressList","[]");

    }

    void addValidLocation(Context c, double lat,double lng, String ssid, List bssid){
        addressList=sph.getSharedPreferenceString(c,"addressList","[]");

        try {
            JSONArray addresses = new JSONArray(addressList);

            for(int i=0;i<addresses.length(); i++){

                try {
                    JSONObject adddetail = addresses.getJSONObject(i);

                    String name1 = adddetail.getString("name");
                    Double lat1= Double.valueOf(adddetail.getString("lat"));
                    Double lng1= Double.valueOf(adddetail.getString("lng"));
                    //String ssid1= adddetail.getString("ssid");
                    //JSONArray bssid1=new JSONArray(adddetail.getString("bssid"));

                    //calculate abs difference between lat and lng values in lat lng diff
                    latdiff=((lat1-lat)<0)?-(lat1-lat):(lat1-lat);
                    lngdiff=((lng1-lng)<0)?-(lng1-lng):(lng1-lng);



                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


}
