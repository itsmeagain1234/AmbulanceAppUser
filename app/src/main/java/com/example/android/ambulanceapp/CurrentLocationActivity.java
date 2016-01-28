package com.example.android.ambulanceapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class CurrentLocationActivity extends FragmentActivity  {

    private GoogleMap mMap;
    private Button mTakePhoto, mLogOut;
    private static int CAMERA_REQUEST_CODE = 1;
    Bitmap bm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_location);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        setUpMapIfNeeded();

        mTakePhoto = (Button) findViewById(R.id.take_photo);
        mTakePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, CAMERA_REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == CAMERA_REQUEST_CODE) {
            if(resultCode == RESULT_OK) {
                bm = (Bitmap) data.getExtras().get("data");
                Intent i = new Intent(CurrentLocationActivity.this, UploadImage.class);
                i.putExtra("img", bm);
                i.putExtra("lat", mMap.getMyLocation().getLatitude());
                i.putExtra("long", mMap.getMyLocation().getLongitude());
                startActivity(i);
            }
        }
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

    private void setUpMapIfNeeded() {
        if(mMap == null)
            mMap=((SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
        if(mMap !=null)
            setUpMap();
    }
    private  void setUpMap() {
        mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));
        mMap.setMyLocationEnabled(true);


    }

    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    public void logOut(View v) {
        SharedPreferences sharedPreferences = getSharedPreferences("user_session", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.remove("username");
        edit.remove("userid");
        edit.apply();
        Intent intent = new Intent(CurrentLocationActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

}