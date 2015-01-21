package us.scoreme.locationpicker;

import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;



public class addLocation extends FragmentActivity {


    public GoogleMap mMap; // Might be null if Google Play services APK is not available.
    public String name;
    public double lat;
    public double lng;
    public double newlat;
    public double newlng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        String mode = intent.getStringExtra("mode");

        //show error dialog if GoolglePlayServices not available
        if (!isGooglePlayServicesAvailable()) {
            finish();
            Log.e("need google play service", "reported as not avai");
        }
        //fusedLocationService = new FusedLocationService(this);
        //Location location = fusedLocationService.getLocation();

        if (mode.equals("new")) {
            setTitle("Add a new location");
            LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
            Criteria criteria = new Criteria();
            String provider = locationManager.getBestProvider(criteria, true);
            Location location1 = locationManager.getLastKnownLocation(provider);

            if (null != location1) {
                lat = location1.getLatitude();
                lng = location1.getLongitude();

                String lats = String.valueOf(lat);
                String lngs = String.valueOf(lng);

                Log.e("test co", lats + "," + lngs);
            } else {
                Log.e("test co", "rats: still no location");
                lat = 0;
                lng = 0;
            }
        }

        if (mode.equals("edit")) {
            name = intent.getStringExtra("name");
            String lats = intent.getStringExtra("lat");
            String lngs = intent.getStringExtra("lng");
            lat=Double.valueOf(lats);
            lng=Double.valueOf(lngs);
            setTitle("Edit "+name);
        }

        setContentView(R.layout.add_location);
        setUpMapIfNeeded();
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
                Log.i("mapsactivity:", my);

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

    public void nextButton(View view) {
        ImageView overlay1= (ImageView) findViewById(R.id.myOverlay);
        overlay1.setVisibility(View.VISIBLE);
        Toast.makeText(this, "clicked next...", Toast.LENGTH_SHORT).show();
    }

    public void backButton(View view) {
        ImageView overlay1= (ImageView) findViewById(R.id.myOverlay);
        overlay1.setVisibility(View.GONE);
        Toast.makeText(this, "clicked back...", Toast.LENGTH_SHORT).show();
    }
}
