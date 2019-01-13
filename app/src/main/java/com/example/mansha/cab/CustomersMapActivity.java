package com.example.mansha.cab;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.telecom.DisconnectCause;
import android.view.View;
import android.widget.Button;

import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CustomersMapActivity extends FragmentActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        com.google.android.gms.location.LocationListener {

    private GoogleMap mMap;
    GoogleApiClient googleApiClient;
    Location lastLocation;
    LocationRequest locationRequest;

    private Button LogoutCustomerbtn;
    private Button SettingCustomerbtn;
    private FirebaseAuth mAuth;
    private FirebaseUser currentuser;
    private boolean currentLogoutcustomerStatus=false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customers_map);

       mAuth=FirebaseAuth.getInstance();
        currentuser=mAuth.getCurrentUser();

        LogoutCustomerbtn= (Button) findViewById(R.id.customer_logout_btn);
        SettingCustomerbtn=(Button) findViewById(R.id.customer_settimg_btn);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        LogoutCustomerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
           public void onClick(View v)
            {
                logoutCustomer();
                mAuth.signOut();


            }
        });
    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng islamabad=new LatLng(33.785022,72.721992);
        mMap.addMarker(new MarkerOptions().position(islamabad).title("Wah Cant"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(islamabad,10f));
        //buildGoogleAPIClient();
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        mMap.setMyLocationEnabled(true);

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        locationRequest = new LocationRequest();
        locationRequest.setInterval(1000);
        locationRequest.setFastestInterval(1000);
        locationRequest.setPriority(550000);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, this.locationRequest, this);


            }

            @Override
            public void onConnectionSuspended(int i)
            {

            }

            @Override
            public void onConnectionFailed(@NonNull ConnectionResult connectionResult)
            {

            }

            @Override
            public void onLocationChanged(Location location)
            {
                lastLocation=location;
                LatLng latLng=new LatLng(location.getLatitude(),location.getLongitude());
                mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                mMap.animateCamera(CameraUpdateFactory.zoomTo(12));

//                String userId=FirebaseAuth.getInstance().getCurrentUser().getUid();
//                DatabaseReference cusromerAvailibiltyRef=FirebaseDatabase.getInstance().getReference().child("customer");
//                GeoFire geoFire=new GeoFire(cusromerAvailibiltyRef);
//                geoFire.setLocation(userId,new GeoLocation(location.getLatitude(),location.getLongitude()));

            }
//            protected synchronized void buildGoogleAPIClient()
//            {
//                googleApiClient=new GoogleApiClient.Builder(this)
//                        .addConnectionCallbacks(this)
//                        .addOnConnectionFailedListener(this)
//                        .addApi(LocationServices.API)
//                        .build();
//
//                googleApiClient.connect();
//            }

    @Override
    protected void onStop() {
        super.onStop();
        if (!currentLogoutcustomerStatus)
        {
            DisconnectTheCustomer();
        }

    }

    private void DisconnectTheCustomer()
    {
        String userId=FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference cusromerAvailibiltyRef=FirebaseDatabase.getInstance().getReference().child("customer move");
        GeoFire geoFire=new GeoFire(cusromerAvailibiltyRef);
        geoFire.removeLocation(userId);

    }
    private void logoutCustomer()
    {
        Intent welcomeIntent=new Intent(CustomersMapActivity.this,WelcomeActivity.class);
        //welcomeIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        startActivity(welcomeIntent);
        finish();
    }

}
