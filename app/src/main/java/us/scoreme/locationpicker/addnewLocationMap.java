package us.scoreme.locationpicker;

import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

import java.util.Random;


public class addnewLocationMap extends FragmentActivity {

    public GoogleMap mMap; // Might be null if Google Play services APK is not available.
    public String name;
    public double lat;
    public double lng;
    public double newlat;
    public double newlng;
    public String mode;
    public String userid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        mode = intent.getStringExtra("mode");
        mode ="new";

        //show error dialog if GoolglePlayServices not available
        if (!isGooglePlayServicesAvailable()) {
            finish();
            Log.e("need gpd", "google play reported as not avail");
        }

        if (mode.equals("new")) {
            setTitle("Add a new location");
            LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
            Criteria criteria = new Criteria();
            String provider = locationManager.getBestProvider(criteria, true);
            Location location1 = locationManager.getLastKnownLocation(provider);
            name="";
            Random r = new Random();
            double i1 = r.nextInt(9000000 - 1000000) + 1000000;
            userid=String.valueOf((i1));

            if (null != location1) {
                lat = location1.getLatitude();
                lng = location1.getLongitude();

                String lats = String.valueOf(lat);
                String lngs = String.valueOf(lng);

            } else {
                Log.e("no location", "rats");
                lat = 0;
                lng = 0;
            }
        }

        if (mode.equals("edit")) {
            userid = intent.getStringExtra("userid");
            name = intent.getStringExtra("name");
            String lats = intent.getStringExtra("lat");
            String lngs = intent.getStringExtra("lng");
            lat=Double.valueOf(lats);
            lng=Double.valueOf(lngs);
            setTitle("Edit "+name);
        }

        setContentView(R.layout.add_location);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        setUpMapIfNeeded();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.addlocation_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.delete:
                Intent intent = new Intent(getApplicationContext(), myLocations.class);
                intent.putExtra("name",name);
                intent.putExtra("mode","delete");
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }


    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    private void setUpMap() {
        LatLng home = new LatLng(lat, lng);

        //mMap.setMyLocationEnabled(true);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(home, 18));

        mMap.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(CameraPosition cameraPosition) {
                String my = mMap.getCameraPosition().target.toString();
                LatLng move = mMap.getCameraPosition().target;
                newlat = move.latitude;
                newlng = move.longitude;
            }
        });

    }

    private boolean isGooglePlayServicesAvailable() {
        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (ConnectionResult.SUCCESS == status) {
            return true;
        } else {
            GooglePlayServicesUtil.getErrorDialog(status, this, 0).show();
            return false;
        }

    }

    public void nextButton(View view){
        String lat=String.valueOf(newlat);
        String lng=String.valueOf(newlng);
        sph.setSharedPreferenceString(this, "newlat", lat);
        sph.setSharedPreferenceString(this, "newlng", lng);
        Log.e("test",lat+lng);
    }


}
