package com.rifaikuci.yeni.yerler.kesfetme;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {


    private GoogleMap mMap;
    FloatingActionButton btnPlant,btnBird,btnPlaceAdd,btnPlaceSelect;
    boolean birdState  = false;
    boolean plantState = false;
    static ArrayList<dataInfo> data ;
    LocationManager locationManager;
    LocationListener locationListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        transparanEkran();
        data = new ArrayList<>();
        data.add(new dataInfo("hayvan","hayvanAciklama",R.drawable.b1, new LatLng(37.717430, 30.286363),"h"));
        data.add(new dataInfo("bitki","bitkiAciklama",R.drawable.p1, new LatLng(     37.717124, 30.288768),"b"));
        data.add(new dataInfo("bitki2","hayvanAciklama",R.drawable.p1, new LatLng(     37.659385, 30.374563),"b"));

        //değişkenleri tanımlama
        variableDesc();


        btnPlant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { btnPlantClick() ; } });

        btnBird.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { btnBirdClick(); } });

        btnPlaceAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { btnPlaceAddClick(); }});

        btnPlaceSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { btnPlaceSelectClick(); }});


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        //locations
        locations();

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(38.6921974,32.0653447),5));

        for (dataInfo object: data) {
            if (object.getTur() == "h") {
                int height = 75;
                int width = 75;
                BitmapDrawable bitmapdraw = (BitmapDrawable) getResources().getDrawable(object.getImage());
                Bitmap b = bitmapdraw.getBitmap();
                Bitmap smallMarker = Bitmap.createScaledBitmap(b, width, height, false);
                mMap.addMarker(new MarkerOptions()
                        .position(object.getLatLng())
                        .title((object.getName()))
                        .snippet(object.getDesc().substring(0, 7))
                        .icon(BitmapDescriptorFactory.fromBitmap(smallMarker))
                );
            }
        }
       //location izinleri
        locationPermission();
    }

    private void btnPlaceSelectClick() {
        Intent intent = new Intent(getApplicationContext(),placeSelection.class);
        startActivity(intent);
    }

    private void btnPlaceAddClick() {
        Toast.makeText(getApplicationContext(),"yer Eklenme ekranına gidecek",Toast.LENGTH_LONG).show();
    }

    private void btnBirdClick() {
        if(birdState==false) {

            btnBird.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.white)));
            birdState=true;
        }
        else {
            btnBird.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.inverse)));
            birdState=false;
        }
    }

    private void btnPlantClick() {
        if(plantState==false) {

            btnPlant.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.white)));
            plantState=true;
        }
        else {
            btnPlant.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.inverse)));
            plantState=false;
        }

    }

    private void variableDesc() {

        btnPlant       = (FloatingActionButton) findViewById(R.id.btnPlant);
        btnBird        = (FloatingActionButton) findViewById(R.id.btnBird);
        btnPlaceAdd    = (FloatingActionButton) findViewById(R.id.btnPlaceAdd);
        btnPlaceSelect = (FloatingActionButton) findViewById(R.id.btnPlaceSelect);

    }

    private void locations() {
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                mMap.setMyLocationEnabled(true);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };


    }

    //konum izni
    private void locationPermission() {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
        }else {
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if(grantResults.length > 0 ) {
            if(requestCode == 1 ){
                if(ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){

                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    //ekranı transpan yapar
     public  void transparanEkran(){
        if(Build.VERSION.SDK_INT>=19)
        {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        else
        {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }
}
