package com.rifaikuci.yeni.yerler.kesfetme;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
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
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.rifaikuci.yeni.yerler.kesfetme.API.ApiClient;
import com.rifaikuci.yeni.yerler.kesfetme.API.ApiInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static java.lang.Integer.parseInt;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    FloatingActionButton btnPlant,btnBird,btnPlaceAdd,btnPlaceSelect;

    boolean birdState  = false,plantState = false;

    static ArrayList<dataInfo> data ;

    LocationManager locationManager;
    LocationListener locationListener;

    public static double lat,log;
    ProgressDialog progressDialog;
    ApiInterface apiInterface ;
    static  Marker gecici;
    ArrayList<Marker> bitkiler,kuslar;
    static int idKullanici=0;
    String turDetay ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        transparanEkran();
        //değişkenleri tanımlama
        variableDesc();

        // get komutu ile verilerimizi getirildi.
        apiInterface  = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<dataInfo>> call= apiInterface.getTurler();

        call.enqueue(new Callback<List<dataInfo>>() {
            @Override
            public void onResponse(Call<List<dataInfo>> call, Response<List<dataInfo>> response) {
                progressDialog.dismiss();

                if( response.isSuccessful() && response.body() !=null){

                    data = (ArrayList<dataInfo>) response.body();


                    for (int i =0;i<data.size();i++){

                        if(data.get(i).getTurDetay().toString().length()>25)
                        {
                            turDetay = data.get(i).getTurDetay().substring(0,25);
                        }
                        else {
                            turDetay = data.get(i).getTurDetay();
                        }

                        if(data.get(i).getTur().equalsIgnoreCase("Bitki")) {

                               gecici = mMap.addMarker(new MarkerOptions().position(new LatLng(data.get(i).getTurEnlem(), data.get(i).getTurBoylam()))
                                       .title(data.get(i).getTurAd())
                                       .icon(BitmapDescriptorFactory.fromResource(R.drawable.leaf))
                                       .snippet(turDetay).visible(false));

                               bitkiler.add(gecici);


                        }
                        else {


                                Marker gedici = mMap.addMarker(new MarkerOptions().position(new LatLng(data.get(i).getTurEnlem(), data.get(i).getTurBoylam()))
                                        .title(data.get(i).getTurAd())
                                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.dove))
                                        .snippet(turDetay).visible(false));

                                kuslar.add(gedici);

                             } } } }



            @Override
            public void onFailure(Call<List<dataInfo>> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(),"internet bağlantınızı kontrol ediniz!!!",Toast.LENGTH_SHORT).show();
            }
        });



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
        try{ mMap.setMyLocationEnabled(true); } // haritada konumu gösterme
        catch (Exception e ){ e.toString(); }


        //locations
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                lat = location.getLatitude();
                log = location.getLongitude();
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) { }

            @Override
            public void onProviderEnabled(String provider) {
                Toast.makeText(getApplicationContext(), "Konum Açık", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onProviderDisabled(String provider) {
                Toast.makeText(getApplicationContext(), "Konum Açık Olmalı!", Toast.LENGTH_LONG).show();
            }
        };

       mMap.setOnInfoWindowLongClickListener(new GoogleMap.OnInfoWindowLongClickListener() {
           @Override
           public void onInfoWindowLongClick(Marker marker) {



               Intent intent = new Intent(getApplicationContext(), dataDetail.class);
               intent.putExtra("tur",marker.getId().substring(1));


               startActivity(intent);

           }

       });

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 101); }

        else { try{
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,3000,1,locationListener); }

            catch (Exception e ){ e.toString(); }

            try {
                Location lastLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if (lastLocation != null) {
                    lat = lastLocation.getLatitude();
                    lat = lastLocation.getLongitude(); } }

            catch (Exception e){ e.toString(); } }

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(38.6921974,32.0653447),5));
        //location izinleri
        locationPermission();
    }


    private void btnPlaceSelectClick() {
        Intent intent = new Intent(getApplicationContext(),placeSelection.class);
        startActivity(intent);
    }

    private void btnPlaceAddClick() {
        Intent intent = new Intent(getApplicationContext(),data_add.class);
        startActivity(intent);
    }

    private void btnBirdClick() {
        if(birdState==false) {
            for (int i = 0; i<kuslar.size();i++){ kuslar.get(i).setVisible(true); }

            btnBird.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.white)));
            birdState=true; }

        else {
            for (int i = 0; i<kuslar.size();i++){
             kuslar.get(i).setVisible(false);

            }

            btnBird.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.inverse)));
            birdState=false;
        }
    }

    private void btnPlantClick() {
        if(plantState==false) {
            for (int i = 0; i<bitkiler.size();i++){
                bitkiler.get(i).setVisible(true);
            }

            btnPlant.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.white)));
            plantState=true;
        }

        else {
            for (int i = 0; i<bitkiler.size();i++){
                bitkiler.get(i).setVisible(false);
            }

            btnPlant.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.inverse)));
            plantState=false;
            } }


    private void variableDesc() {

        btnPlant       = (FloatingActionButton) findViewById(R.id.btnPlant);
        btnBird        = (FloatingActionButton) findViewById(R.id.btnBird);
        btnPlaceAdd    = (FloatingActionButton) findViewById(R.id.btnPlaceAdd);
        btnPlaceSelect = (FloatingActionButton) findViewById(R.id.btnPlaceSelect);
        bitkiler = new ArrayList<>();
        kuslar = new ArrayList<>();
        data = new ArrayList<>();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Lütfen Bekleyiniz...");
    }

    private void locations() {

    }

    //konum izni
    private void locationPermission() {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
        }else {
            try{
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,3000,1,locationListener);

            }catch (Exception e ){
                e.toString();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(grantResults.length>0){
            if(requestCode==101){
                if(ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                    try{
                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,3000,1,locationListener);

                    }catch (Exception e ){
                        e.toString();
                    }

                }

            }
        }
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
