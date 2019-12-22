package com.rifaikuci.yeni.yerler.kesfetme;

import androidx.fragment.app.FragmentActivity;

import android.content.res.ColorStateList;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {


    private GoogleMap mMap;
    FloatingActionButton btnPlant,btnBird,btnPlaceAdd,btnPlaceSelect;
    boolean birdState  = false;
    boolean plantState = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        transParanEkran();

        btnPlant       = (FloatingActionButton) findViewById(R.id.btnPlant);
        btnBird        = (FloatingActionButton) findViewById(R.id.btnBird);
        btnPlaceAdd    = (FloatingActionButton) findViewById(R.id.btnPlaceAdd);
        btnPlaceSelect = (FloatingActionButton) findViewById(R.id.btnPlaceSelect);



        btnPlant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(plantState==false) {

                    btnPlant.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.white)));
                    plantState=true;
                }
                else {
                    btnPlant.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.inverse)));
                    plantState=false;
                }

            }
        });

        btnBird.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(birdState==false) {

                    btnBird.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.white)));
                    birdState=true;
                }
                else {
                    btnBird.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.inverse)));
                    birdState=false;
                }
            }
        });


        btnPlaceAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"yer Eklenme ekranına gidecek",Toast.LENGTH_LONG).show();
            }
        });

        btnPlaceSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"gidilecek yerleri seçme ekranına gidecek",Toast.LENGTH_LONG).show();
            }
        });





        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    //ekranı transpan yapar
    public  void transParanEkran(){
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
