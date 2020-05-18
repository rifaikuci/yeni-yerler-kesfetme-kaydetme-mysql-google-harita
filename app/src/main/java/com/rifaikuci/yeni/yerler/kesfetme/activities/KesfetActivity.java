package com.rifaikuci.yeni.yerler.kesfetme.activities;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.LocationSettingsStatusCodes;
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
import com.rifaikuci.yeni.yerler.kesfetme.R;
import com.rifaikuci.yeni.yerler.kesfetme.datas.dataTur;
import com.rifaikuci.yeni.yerler.kesfetme.login_islemleri.SessionManager;
import com.rifaikuci.yeni.yerler.kesfetme.login_islemleri.signIn;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KesfetActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    FloatingActionButton btnPlant, btnBird, btnPlaceAdd, btnPlaceSelect;

    boolean birdState = false, plantState = false;

    static ArrayList<dataTur> data;

    LocationManager locationManager;
    LocationListener locationListener;

    public static double lat, log;
    ProgressDialog progressDialog;
    ApiInterface apiInterface;
    static Marker gecici;
    ArrayList<Marker> bitkiler, kuslar;
    static int idKullanici = 0;
    String turDetay = "", aktivite = "";
    int id = 0;
    Intent intent;
    private static final int REQUEST_CHECK_SETTINGS = 0x1;
    private static GoogleApiClient mGoogleApiClient;
    private static final int ACCESS_FINE_LOCATION_INTENT_ID = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kesfet);
        transparanEkran();
        //değişkenleri tanımlama
        variableDesc();

        aktivite = intent.getStringExtra("aktivite");


        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<dataTur>> call = apiInterface.getKesfet();

        call.enqueue(new Callback<List<dataTur>>() {
            @Override
            public void onResponse(Call<List<dataTur>> call, Response<List<dataTur>> response) {
                progressDialog.dismiss();

                if (response.isSuccessful() && response.body() != null) {

                    data = (ArrayList<dataTur>) response.body();


                    for (int i = 0; i < data.size(); i++) {

                        if (data.get(i).getTurDetay().toString().length() > 25) {
                            turDetay = data.get(i).getTurDetay().substring(0, 25);
                        } else {
                            turDetay = data.get(i).getTurDetay();
                        }

                        if (data.get(i).getTur().equalsIgnoreCase("Bitki")) {

                            gecici = mMap.addMarker(new MarkerOptions().position(new LatLng(data.get(i).getTurEnlem(), data.get(i).getTurBoylam()))
                                    .title(data.get(i).getTurAd())
                                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.leaf))
                                    .snippet(turDetay).visible(false));

                            bitkiler.add(gecici);


                        } else {


                            Marker gedici = mMap.addMarker(new MarkerOptions().position(new LatLng(data.get(i).getTurEnlem(), data.get(i).getTurBoylam()))
                                    .title(data.get(i).getTurAd())
                                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.dove))
                                    .snippet(turDetay).visible(false));

                            kuslar.add(gedici);

                        }
                    }
                }
            }


            @Override
            public void onFailure(Call<List<dataTur>> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), "internet bağlantınızı kontrol ediniz!!!", Toast.LENGTH_SHORT).show();
            }
        });


        btnPlant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnPlantClick();
            }
        });

        btnBird.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnBirdClick();
            }
        });

        btnPlaceAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnPlaceAddClick();
            }
        });

        btnPlaceSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnPlaceSelectClick();
            }
        });

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        //google izinleri
        initGoogleAPIClient();
        checkPermissions();
        //bu ikisi alundığında izinlerin çağrılma işlemleri tamamlanacak

        try {
            mMap.setMyLocationEnabled(true);
        } // haritada konumu gösterme
        catch (Exception e) {
            e.toString();
        }


        //locations
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                lat = location.getLatitude();
                log = location.getLongitude();
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

        mMap.setOnInfoWindowLongClickListener(new GoogleMap.OnInfoWindowLongClickListener() {
            @Override
            public void onInfoWindowLongClick(Marker marker) {

                Intent intent = new Intent(getApplicationContext(), dataDetail.class);
                intent.putExtra("tur", marker.getId().substring(1));
                intent.putExtra("kesfet", "kesfet");
                startActivity(intent);
            }

        });

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 101);
        } else {
            try {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3000, 1, locationListener);
            } catch (Exception e) {
                e.toString();
            }

            try {
                Location lastLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if (lastLocation != null) {
                    lat = lastLocation.getLatitude();
                    lat = lastLocation.getLongitude();
                }
            } catch (Exception e) {
                e.toString();
            }
        }

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(38.6921974, 32.0653447), 5));
        //location izinleri
    }


    private void btnPlaceSelectClick() {
        Intent intent = new Intent(getApplicationContext(), placeSelection.class);
        startActivity(intent);
    }

    private void btnPlaceAddClick() {
        Intent intent = new Intent(getApplicationContext(), data_add.class);
        startActivity(intent);
    }

    private void btnBirdClick() {
        if (birdState == false) {
            for (int i = 0; i < kuslar.size(); i++) {
                kuslar.get(i).setVisible(true);
            }

            btnBird.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.white)));
            birdState = true;
        } else {
            for (int i = 0; i < kuslar.size(); i++) {
                kuslar.get(i).setVisible(false);

            }

            btnBird.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.inverse)));
            birdState = false;
        }
    }

    private void btnPlantClick() {
        if (plantState == false) {
            for (int i = 0; i < bitkiler.size(); i++) {
                bitkiler.get(i).setVisible(true);
            }

            btnPlant.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.white)));
            plantState = true;
        } else {
            for (int i = 0; i < bitkiler.size(); i++) {
                bitkiler.get(i).setVisible(false);
            }

            btnPlant.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.inverse)));
            plantState = false;
        }
    }


    private void variableDesc() {

        btnPlant = (FloatingActionButton) findViewById(R.id.btnPlant);
        btnBird = (FloatingActionButton) findViewById(R.id.btnBird);
        btnPlaceAdd = (FloatingActionButton) findViewById(R.id.btnPlaceAdd);
        btnPlaceSelect = (FloatingActionButton) findViewById(R.id.btnPlaceSelect);
        bitkiler = new ArrayList<>();
        kuslar = new ArrayList<>();
        data = new ArrayList<>();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Lütfen Bekleyiniz...");

        intent = getIntent();


    }


    //  Konum izni için User Permission
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == ACCESS_FINE_LOCATION_INTENT_ID) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(KesfetActivity.this, "İzin verildi", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(KesfetActivity.this, "İzin verilmedi", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CHECK_SETTINGS) {

            if (resultCode == RESULT_OK) {

                Toast.makeText(KesfetActivity.this, "GPS Aktif", Toast.LENGTH_LONG).show();
            } else {

                Toast.makeText(KesfetActivity.this, "GPS Pasif", Toast.LENGTH_LONG).show();
            }

        }
    }

    private void checkPermissions() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(KesfetActivity.this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED)
                requestLocationPermission();
            else
                showLocationState();
        } else
            showLocationState();

    }

    private void requestLocationPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(KesfetActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION)) {
            ActivityCompat.requestPermissions(KesfetActivity.this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    ACCESS_FINE_LOCATION_INTENT_ID);

        } else {
            ActivityCompat.requestPermissions(KesfetActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    ACCESS_FINE_LOCATION_INTENT_ID);
        }
    }

    private void initGoogleAPIClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(KesfetActivity.this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    private void showLocationState() {
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);//Setting priotity of Location request to high
        locationRequest.setInterval(30 * 1000);
        locationRequest.setFastestInterval(5 * 1000);//5 sec Time interval for location update
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);
        builder.setAlwaysShow(true); //this is the key ingredient to show dialog always when GPS is off

        PendingResult<LocationSettingsResult> result =
                LocationServices.SettingsApi.checkLocationSettings(mGoogleApiClient, builder.build());
        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(LocationSettingsResult result) {
                final Status status = result.getStatus();
                final LocationSettingsStates state = result.getLocationSettingsStates();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        //Konum ayarları etkin ise buraya
                        // istekler burda
                        //updateGPSStatus("GPS is Enabled in your device");
                        Log.d("locationEnable", "SUCCESS");
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        // Konum ayarları etkin değil fakat dialog gösterip konum açılmasını sağlıyor isek buraya
                        // Dialog göster
                        try {
                            // startResolutionForResult(), çağırıp kontrol edilir
                            Log.d("locationEnable", "RESOLUTION_REQUIRED");
                            status.startResolutionForResult(KesfetActivity.this, REQUEST_CHECK_SETTINGS);
                        } catch (IntentSender.SendIntentException e) {
                            e.printStackTrace();
                            // Ignore the error.
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        // Konum eğer açılamıyor ise buraya düşer
                        Log.d("locationEnable", "SETTINGS_CHANGE_UNAVAILABLE");
                        break;
                }
            }
        });
    }
    // Konum izinleri için tüm işlevler bitiş


    //ekranı transpan yapar
    public void transparanEkran() {
        if (Build.VERSION.SDK_INT >= 19) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        } else {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    @Override
    public void onBackPressed() {
        if ("signin".equalsIgnoreCase(aktivite)) {
            intent = new Intent(getApplicationContext(), signIn.class);
        } else {
            intent = new Intent(getApplicationContext(), Ana_ekran.class);
        }
        startActivity(intent);
    }
}
