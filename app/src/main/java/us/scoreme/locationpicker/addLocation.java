package us.scoreme.locationpicker;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

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
    public String mode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        mode = intent.getStringExtra("mode");

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
                Intent intent = new Intent(getApplicationContext(), startApp.class);
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
        // get prompts.xml view
        LayoutInflater li = LayoutInflater.from(this);
        View promptsView = li.inflate(R.layout.fragment_edit_name, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);


        // set prompts.xml to alertdialog builder
        alertDialogBuilder.setView(promptsView);

        final EditText userInput = (EditText) promptsView
                .findViewById(R.id.txt_your_name);

        userInput.setText(name);

        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // get user input and set it to result
                                // edit text
                                name = userInput.getText().toString();
                                if (name.length() > 0) {
                                    Log.e("text entered",name);
                                    Log.e("lat",String.valueOf(newlat));
                                    Log.e("lng",String.valueOf(newlng));
                                    Intent intent = new Intent(getApplicationContext(), startApp.class);
                                    intent.putExtra("name",name);
                                    intent.putExtra("lat",String.valueOf(newlat));
                                    intent.putExtra("lng", String.valueOf(newlng));
                                    intent.putExtra("mode", mode);
                                    startActivity(intent);
                                }
                        }
    })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();

    }


}
