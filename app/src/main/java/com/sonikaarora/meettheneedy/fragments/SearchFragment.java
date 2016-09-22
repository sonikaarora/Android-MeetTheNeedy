package com.sonikaarora.meettheneedy.fragments;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.google.android.gms.common.api.GoogleApiClient;

import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;


import com.google.android.gms.location.places.Places;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.sonikaarora.meettheneedy.MainActivity;
import com.sonikaarora.meettheneedy.R;
import com.sonikaarora.meettheneedy.activities.DetailsActivity;


/**
 * Created by sonikaarora on 6/26/16.
 */
public class SearchFragment extends Fragment  {

    private static View view;

    private static GoogleMap mMap;
    private static Double latitude, longitude;
    private SupportMapFragment mSupportMapFragment;
    private GoogleApiClient mGoogleApiClient;
    private final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 0;
    private LocationRequest lr;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (container == null) {
            return null;
        }
        view = (RelativeLayout) inflater.inflate(R.layout.fragment_search, container, false);
        // Passing harcoded values for latitude & longitude. Please change as per your need. This is just used to drop a Marker on the Map
        latitude = 26.78;
        longitude = 72.56;

        setUpMapIfNeeded(); // For setting up the MapFragment

        return view;
    }

    /***** Sets up the map if it is possible to do so *****/
    public void setUpMapIfNeeded() {


        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            setUpMap();
        }
    }

    private void setUpMap() {

        mSupportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.location_map);

        GoogleMapOptions options = new GoogleMapOptions();
        options.mapType(GoogleMap.MAP_TYPE_NORMAL)
                .compassEnabled(false)
                .rotateGesturesEnabled(false)
                .tiltGesturesEnabled(false)
                .compassEnabled(true)
                .zoomControlsEnabled(true)
                .zoomGesturesEnabled(true);


        if (mSupportMapFragment == null) {
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            mSupportMapFragment = SupportMapFragment.newInstance(options);
            fragmentTransaction.replace(R.id.location_map, mSupportMapFragment).commit();
        }

        if (mSupportMapFragment != null) {
            mSupportMapFragment.getMapAsync(new OnMapReadyCallback() {


                @Override
                public void onMapReady(GoogleMap googleMap) {


                    googleMap.getUiSettings().setZoomControlsEnabled(true);

                    

                    GoogleMapOptions options = new GoogleMapOptions();
                    options.mapType(GoogleMap.MAP_TYPE_NORMAL)
                            .compassEnabled(false)
                            .rotateGesturesEnabled(false)
                            .tiltGesturesEnabled(false);


                    mGoogleApiClient = new GoogleApiClient.Builder(getActivity())

                            .addApi(LocationServices.API)
                            .addApi(Places.GEO_DATA_API)
                            .addApi(Places.PLACE_DETECTION_API)
                            .build();
                    mGoogleApiClient.connect();


                    int permissionCheck = ContextCompat.checkSelfPermission(getActivity(),
                            Manifest.permission.ACCESS_FINE_LOCATION);


                    // Here, thisActivity is the current activity
                    if (ContextCompat.checkSelfPermission(getActivity(),
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            != PackageManager.PERMISSION_GRANTED) {

                        // Should we show an explanation?
                        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                                Manifest.permission.ACCESS_FINE_LOCATION)) {

                            // Show an expanation to the user *asynchronously* -- don't block
                            // this thread waiting for the user's response! After the user
                            // sees the explanation, try again to request the permission.

                        } else {

                            // No explanation needed, we can request the permission.

                            ActivityCompat.requestPermissions(getActivity(),
                                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                    MY_PERMISSIONS_REQUEST_READ_CONTACTS);

                            // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                            // app-defined int constant. The callback method gets the
                            // result of the request.
                        }
                    }


                    googleMap.setMyLocationEnabled(true);

                    googleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                        @Override
                        public void onInfoWindowClick(Marker marker) {
                            Bundle bundle = new Bundle();
                            bundle.putBoolean("login",MainActivity.loginflag);

                            Intent ngoDonationActivityIntent = new Intent(getActivity(), DetailsActivity.class);
                            ngoDonationActivityIntent.putExtras(bundle);
                            startActivity(ngoDonationActivityIntent);

                        }
                    });


                 /*
                 Latitude and Longitude are hardcoded temporary only for demo purpose of Lab2. This lab is part of real implementation for the project and need to be integrated with
                 other part of project that is being developed by my project partner.
                 This hard-coding would be removed in final project.
                  */
                    latitude = 37.3365033;
                    longitude = -121.8954576;


                    googleMap.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)).title("San Jose Library Foundation"));
                    // For zooming automatically to the Dropped PIN Location
                    googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude,
                            longitude), 12.0f));


                    if(MainActivity.getActionBarTitle().equals("Non Profit Organizations")) {
                        googleMap.addMarker(new MarkerOptions().position(new LatLng(37.3360759, -121.9061165)).title("Catholic Charities"))
                                .setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
                        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(37.3360759,
                                -121.9061165), 12.0f));

                        googleMap.addMarker(new MarkerOptions().position(new LatLng(37.3377885, -121.8929087)).title("Hope with Sudan"))
                                .setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
                        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(37.3377885,
                                -121.8929087), 12.0f));

                        googleMap.addMarker(new MarkerOptions().position(new LatLng(37.347057, -121.8934609)).title("Filipino Youth Coalition Inc"))
                                .setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
                        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(37.347057,
                                -121.8934609), 12.0f));

                        googleMap.addMarker(new MarkerOptions().position(new LatLng(37.3269414, -121.9026436)).title("San Jose Youth Symphony, Park"))
                                .setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
                        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(37.3269414,
                                -121.9026436), 12.0f));
                    }

                    else if(MainActivity.getActionBarTitle().equals("Libraries"))
                    {
                        googleMap.addMarker(new MarkerOptions().position(new LatLng(37.3778175, -121.9923136)).title("Rose Garden Branch Library"))
                                .setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
                        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(37.3778175,
                                -121.9923136), 12.0f));

                        googleMap.addMarker(new MarkerOptions().position(new LatLng(37.378198, -121.9923144)).title("Dr. Martin Luther King, Jr. Library"))
                                .setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
                        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(37.378198,
                                -121.9923144), 12.0f));

                        googleMap.addMarker(new MarkerOptions().position(new LatLng(37.3785784, -121.9923151)).title("Willow Glen Branch Library"))
                                .setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
                        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(37.3785784,
                                -121.9923151), 12.0f));

                        googleMap.addMarker(new MarkerOptions().position(new LatLng(37.3789589, -121.9923159)).title("Tully Community Branch Library"))
                                .setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
                        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(37.3789589,
                                -121.9923159), 12.0f));
                    }

//
//                    Uri gmmIntentUri = Uri.parse("geo:37.7749,-122.4194?q=non+profit+organizations");
//
//                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
//                    mapIntent.setPackage("com.google.android.apps.maps");
//
//                    startActivity(mapIntent);


                }
            });
        }


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_CONTACTS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {


                } else {

                    // permission denied
                }
                return;
            }

        }
    }



    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        if (mMap != null)
            setUpMap();

        if (mMap == null) {

               setUpMap();

       }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mMap != null) {
            MainActivity.fragmentManager.beginTransaction()
                    .remove(MainActivity.fragmentManager.findFragmentById(R.id.location_map)).commit();
            mMap = null;
        }
    }

}
