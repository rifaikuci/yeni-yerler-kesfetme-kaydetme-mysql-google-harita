package com.rifaikuci.yeni.yerler.kesfetme;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;


public class data_add extends AppCompatActivity  {

    ImageView imageSelect, locationIcon;
    TextInputLayout layoutTurAdi, layoutTurDetayi, layoutKonum;
    TextInputEditText editTextTurAdi, editTextTurDetayi, editTextKonum;
    RadioGroup tur, gonder;
    RadioButton radioBitki, radioKus, radioAktif, radioPasif;
    Button btnKaydet, btnVazgec;
    LocationManager locationManager;
    LocationListener locationListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_add);
        transparanEkran();
        variableDesc();

        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                MapsActivity.lat = location.getLatitude();
                MapsActivity.log = location.getLongitude();
                layoutKonum.getEditText().setText("Enlem :"+MapsActivity.lat+"\nBoylam :"+MapsActivity.log);

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {
                Toast.makeText(getApplicationContext(),"Konum Açık",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onProviderDisabled(String provider) {
                Toast.makeText(getApplicationContext(),"Konum Açık Olmalı!",Toast.LENGTH_LONG).show();
            }
        };
        if(ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},101);
        }else{
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,3000,1,locationListener);

            Location lastLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if(lastLocation!= null){
                MapsActivity.lat = lastLocation.getLatitude();
                MapsActivity.log = lastLocation.getLongitude();
                layoutKonum.getEditText().setText("Enlem :"+MapsActivity.lat+"\nBoylam :"+MapsActivity.log);
            }
        }

        btnVazgec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnVazgecClick();
            }
        });

        imageSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageSelectClick();
            }
        });

        btnKaydet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnKaydetClick();
            }
        });

        locationIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locationIconClick();
            }
        });

    }

    //ikonc click işlemleri
    private void locationIconClick() {
        layoutKonum.getEditText().setText("Enlem :"+MapsActivity.lat+"\nBoylam :"+MapsActivity.log);

    }

    //Kaydetme işlemleri
    private void btnKaydetClick() {

    }

    //geri butonu
    private void btnVazgecClick() {
        Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
        startActivity(intent);
    }

    private void variableDesc() {
        imageSelect = (ImageView) findViewById(R.id.imageSelect);
        locationIcon = (ImageView) findViewById(R.id.locationIcon);

        layoutTurAdi = (TextInputLayout) findViewById(R.id.layoutTurAdi);
        layoutTurDetayi = (TextInputLayout) findViewById(R.id.layoutTurDetayi);
        layoutKonum = (TextInputLayout) findViewById(R.id.layoutKonum);

        editTextTurAdi = (TextInputEditText) findViewById(R.id.editTextTurAdi);
        editTextTurDetayi = (TextInputEditText) findViewById(R.id.editTextTurDetayi);
        editTextKonum = (TextInputEditText) findViewById(R.id.editTextKonum);

        tur = (RadioGroup) findViewById(R.id.tur);
        gonder = (RadioGroup) findViewById(R.id.gonder);

        radioBitki = (RadioButton) findViewById(R.id.radioBitki);
        radioKus = (RadioButton) findViewById(R.id.radioKus);
        radioAktif = (RadioButton) findViewById(R.id.radioAktif);
        radioPasif = (RadioButton) findViewById(R.id.radioPasif);

        btnKaydet = (Button) findViewById(R.id.btnKaydet);
        btnVazgec = (Button) findViewById(R.id.btnVazgec);
    }

    //Crop İmage İşlemleri
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                imageSelect.setImageURI(resultUri);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(grantResults.length>0){
            if(requestCode==101){
                if(ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,3000,1,locationListener);

                }

            }
        }
    }

    //İmageSelectClick işlemleri
    private void imageSelectClick() {
        ActivityCompat.requestPermissions(data_add.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        CropImage.activity().setGuidelines(CropImageView.Guidelines.ON).start(this);
    }

    public void transparanEkran() {
        if (Build.VERSION.SDK_INT >= 19) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        } else {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    //Bitiş
}
