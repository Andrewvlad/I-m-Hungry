package com.example.finalproject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;

import com.example.finalproject.data.LocationInfo;
import com.example.finalproject.data.TempInfo;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements GoogleMap.OnInfoWindowClickListener, OnMapReadyCallback, LocationListener {


    private GoogleMap mMap;
    private MarkerOptions options = new MarkerOptions();
    private ArrayList<LatLng> latlngs = new ArrayList<>();
    private SeekBar ratingSeekBar;
    private SeekBar radiusSeekBar;
    private TextView mapHeader;
    private FusedLocationProviderClient fusedLocationClient;
    LocationTrack locationTrack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        ratingSeekBar = findViewById(R.id.ratingBar);
        ratingSeekBar.setProgress(TempInfo.getRatingPreference() - 3);
        radiusSeekBar = findViewById(R.id.radiusBar);
        switch ((int) ((TempInfo.getRadius()) / 1609.0)){
            case 1:
                radiusSeekBar.setProgress(0);
                break;
            case 3:
                radiusSeekBar.setProgress(1);
                break;
            case 5:
                radiusSeekBar.setProgress(2);
                break;
            case 7:
                radiusSeekBar.setProgress(3);
                break;
            case 10:
                radiusSeekBar.setProgress(4);
                break;
            case 15:
                radiusSeekBar.setProgress(5);
                break;
            case 20:
                radiusSeekBar.setProgress(6);
            break;
        }
        mapHeader = findViewById(R.id.mapHeader);
        mapHeader.setText(TempInfo.getSearch() + " Near You");

        locationTrack = new LocationTrack(MapsActivity.this);

        if (locationTrack.canGetLocation()) {
            TempInfo.setCurrentLongitude(locationTrack.getLongitude());
            TempInfo.setCurrentLatitude(locationTrack.getLatitude());
        } else {
            locationTrack.showSettingsAlert();
        }

        ratingSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                TempInfo.setRatingPreference(i + 3);
                Intent intent = getIntent();
                finish();
                startActivity(intent);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        radiusSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                switch (i) {
                    case 0:
                        TempInfo.setRadius(1 * 1609);
                        TempInfo.setZoomLevel(13.6f);
                        break;
                    case 1:
                        TempInfo.setRadius(3 * 1609);
                        TempInfo.setZoomLevel(12);
                        break;
                    case 2:
                        TempInfo.setRadius(5 * 1609);
                        TempInfo.setZoomLevel(11.27f);
                        break;
                    case 3:
                        TempInfo.setRadius(7 * 1609);
                        TempInfo.setZoomLevel(10.8f);
                        break;
                    case 4:
                        TempInfo.setRadius(10 * 1609);
                        TempInfo.setZoomLevel(10.25f);
                        break;
                    case 5:
                        TempInfo.setRadius(15 * 1609);
                        TempInfo.setZoomLevel(9.68f);
                        break;
                    case 6:
                        TempInfo.setRadius(20 * 1609);
                        TempInfo.setZoomLevel(9.25f);
                        break;
                }
                Intent intent = getIntent();
                finish();
                startActivity(intent);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        for (int i = 0; i < LocationInfo.getName().size(); i++) {
            MarkerOptions marker = new MarkerOptions();
            if (LocationInfo.getRating().get(i) >= TempInfo.getRatingPreference()) {
                marker.position(new LatLng(LocationInfo.getLatitude().get(i), LocationInfo.getLongitude().get(i)));
                marker.zIndex(i);
                marker.title(LocationInfo.getName().get(i));
                marker.snippet(String.valueOf(LocationInfo.getDistance().get(i)));
                CustomInfoWindowAdapter adapter = new CustomInfoWindowAdapter(MapsActivity.this);
                mMap.setInfoWindowAdapter(adapter);
                mMap.addMarker(marker).showInfoWindow();
            }
        }

        LatLng currentLocation = new LatLng(TempInfo.getCurrentLatitude(), TempInfo.getCurrentLongitude());
        mMap.addMarker(new MarkerOptions().position(currentLocation)
                .title("My Location")
        );
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, TempInfo.getZoomLevel() + .5f));
        mMap.setOnInfoWindowClickListener(this);
        drawCircle(currentLocation);
    }



    @Override
    public void onInfoWindowClick(final Marker marker) {
        final Uri uri =  Uri.parse("google.navigation:q=" + marker.getPosition().latitude + "," + marker.getPosition().longitude);
        Activity context = MapsActivity.this;
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Open Directions in Google Maps?")
                .setCancelable(true)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        Activity context = MapsActivity.this;
                        context.startActivity(intent);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    private void drawCircle( LatLng location ) {
        CircleOptions options = new CircleOptions();
        options.center( location );
        //Radius in meters
        options.radius(TempInfo.getRadius());
        options.strokeColor( getResources()
                .getColor( R.color.colorPrimary ) );
        options.strokeWidth( 10 );
        mMap.addCircle(options);
    }

    @Override
    public void onLocationChanged(Location location) {
        TempInfo.setCurrentLatitude(location.getLatitude());
        TempInfo.setCurrentLongitude(location.getLongitude());
    }
}