package com.rifaikuci.yeni.yerler.kesfetme;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.rifaikuci.yeni.yerler.kesfetme.API.ApiClient;
import com.rifaikuci.yeni.yerler.kesfetme.API.ApiInterface;
import com.rifaikuci.yeni.yerler.kesfetme.datas.dataTur;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class data_add extends AppCompatActivity  {

    ImageView imageSelect, locationIcon;
    TextInputLayout layoutTurAdi, layoutTurDetayi, layoutKonum;
    TextInputEditText editTextTurAdi, editTextTurDetayi, editTextKonum;
    TextView txtBaslik;
    RadioGroup groupTur, groupGonder;
    RadioButton radioBitki, radioKus, radioAktif, radioPasif,rbTur,rbGonder;
    Button btnKaydet, btnVazgec;

    LocationManager locationManager;
    LocationListener locationListener;

    ProgressDialog progressDialog;
    ApiInterface apiInterface;

    String turAd,gelen,gelenId,turDetay,turResim,baslik,resim,detay,durum,tur;
    Double enlem,boylam,turEnlem,turBoylam;

    Uri resultUri;
    private Bitmap bitmap;
    Intent intent;
    int radioTurId,radioGonderId,id;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_add);
        transparanEkran();
        variableDesc();

        intent = getIntent();
        try{
            gelen = intent.getStringExtra("gelis");
            gelenId=intent.getStringExtra("guncelleId");
            id = MapsActivity.data.get(Integer.parseInt(gelenId)).getid();

            System.out.println("Sayısal değerler"+ id);

            if(gelen.equalsIgnoreCase("edit")){

                baslik =  MapsActivity.data.get(Integer.parseInt(gelenId)).getTurAd();
                resim =   MapsActivity.data.get(Integer.parseInt(gelenId)).getTurResim();
                detay=    MapsActivity.data.get(Integer.parseInt(gelenId)).getTurDetay();
                enlem=    MapsActivity.data.get(Integer.parseInt(gelenId)).getTurEnlem();
                boylam=   MapsActivity.data.get(Integer.parseInt(gelenId)).getTurBoylam();
                tur      =MapsActivity.data.get(Integer.parseInt(gelenId)).getTur();
                durum =MapsActivity.data.get(Integer.parseInt(gelenId)).getdurum();

                txtBaslik.setText("Güncelleme Formu");
                btnKaydet.setText("Güncelle");

                Picasso.get().load(resim).into(imageSelect);
                editTextTurDetayi.setText(detay);
                editTextTurAdi.setText(baslik);
                // editTextKonum.setText("Enlem: "+ enlem+"\n"+"Boylam: "+boylam);

                locationIcon.setVisibility(View.INVISIBLE);
                layoutKonum.setVisibility(View.INVISIBLE);


                if(tur.equalsIgnoreCase("Bitki")){ groupTur.check(R.id.radioBitki); }
                else { groupTur.check(R.id.radioKus); }


                if(durum.equalsIgnoreCase("1")){ groupGonder.check(R.id.radioAktif); }
                else { groupGonder.check(R.id.radioPasif); } }

        }catch (Exception e){ e.printStackTrace(); }

        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {

            //Konum değiştiğinde yapılacak işlemler.
            @Override
            public void onLocationChanged(Location location) {
                MapsActivity.lat = location.getLatitude();
                MapsActivity.log = location.getLongitude();
                layoutKonum.getEditText().setText("Enlem :"+MapsActivity.lat+"\nBoylam :"+MapsActivity.log);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            //Konum açıkken yapılacak işelmler
            @Override
            public void onProviderEnabled(String provider) {
                Toast.makeText(getApplicationContext(),"Konum Açık",Toast.LENGTH_LONG).show();
            }


            //Konum açıkken yapılacak işelmler
            @Override
            public void onProviderDisabled(String provider) {
                Toast.makeText(getApplicationContext(),"Konum Açık Olmalı!",Toast.LENGTH_LONG).show();
            }
        };

        // konum izin işlemleri
        if(ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},101);
        }else{
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,3000,1,locationListener);

            //bilinen son konumu aldırma
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

                 turAd =editTextTurAdi.getText().toString().trim();
                 turDetay = editTextTurDetayi.getText().toString().trim();

                 //Resmi yazıya çevirme
                 try{ turResim =imageToString(); }
                 catch (Exception e){ turResim =""; }

                 turEnlem = MapsActivity.lat;
                 turBoylam= MapsActivity.log;

                 radioTurId = groupTur.getCheckedRadioButtonId();
                 rbTur = (RadioButton) findViewById(radioTurId);

                 try { tur = rbTur.getText().toString(); }
                 catch (Exception e){ tur=""; }

                 radioGonderId = groupGonder.getCheckedRadioButtonId();
                 rbGonder = (RadioButton) findViewById(radioGonderId);

                 try { durum = rbGonder.getText().toString(); }
                 catch (Exception e){ durum=""; }

                 //gelen veri kaydetme mi , güncelleme mi ?
                if(btnKaydet.getText().equals("Kaydet"))
                {

                //Kontroller
                    if(turAd.isEmpty()){ layoutTurAdi.setError("Tür adı boş geçilemez!!!"); }

                    else if(turDetay.isEmpty()){ layoutTurDetayi.setError("Türün Detayı boş geçilemez!!!"); }

                    else if(editTextKonum.getText().toString().trim().isEmpty()){ layoutKonum.setError("Türün Detayı boş geçilemez!!!"); }

                    else if(turResim.equalsIgnoreCase("")==true){ Toast.makeText(getApplicationContext(),"Resim Seçilmedi!!!",Toast.LENGTH_SHORT).show(); }

                    else if(tur.equalsIgnoreCase("")==true){ Toast.makeText(getApplicationContext(),"Kaydedilen verinin türü seçilmedi!!!",Toast.LENGTH_SHORT).show(); }

                    else if(durum.equalsIgnoreCase("")==true){ Toast.makeText(getApplicationContext(),"Türün Durumu Seçilmedi!!!",Toast.LENGTH_SHORT).show(); }

                    else{ btnKaydetClick(turAd,turDetay,turResim,turEnlem,turBoylam,tur,durum); }

                }else
                {

                    if(turAd.isEmpty()){ layoutTurAdi.setError("Tür adı boş geçilemez!!!"); }

                    else if(turDetay.isEmpty()){ layoutTurDetayi.setError("Türün Detayı boş geçilemez!!!"); }

                    else if(turResim.equalsIgnoreCase("")==true){
                        turResim="";
                        btnUpdateClick();
                    }

                    else { btnUpdateClick(); }

                    //btnKaydet bitis
                } }});

        //Location ikona tıklanırsa
        locationIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locationIconClick();
            }
        });
    }

    private void btnUpdateClick() {

        progressDialog.show();

        apiInterface  = ApiClient.getApiClient().create(ApiInterface.class);
        Call<dataTur> call= apiInterface.updateData(id,turAd,turDetay,turResim,tur,durum);

        //güncelleme işlemleri
        call.enqueue(new Callback<dataTur>() {
            @Override
            public void onResponse(@NonNull  Call<dataTur> call, @NonNull Response<dataTur> response) {
                progressDialog.dismiss();
                if( response.isSuccessful() && response.body() !=null){
                    Boolean success  = response.body().getSuccess();

                    if(success){
                        Toast.makeText(getApplicationContext(),turAd+ " Başarılı bir Şekilde Güncellendi",Toast.LENGTH_SHORT).show();
                        btnVazgecClick();

                    }else {
                        Toast.makeText(getApplicationContext(),"Güncelleme işlemi yapılırken bir hata oluştu.",Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull  Call<dataTur> call, @NonNull Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(),"internet bağlantınızı kontrol ediniz!!!",Toast.LENGTH_SHORT).show();

            }
        });
    }


    //ikonc click işlemleri
    private void locationIconClick() {
        layoutKonum.getEditText().setText("Enlem :"+MapsActivity.lat+"\nBoylam :"+MapsActivity.log);

    }

    //Kaydetme işlemleri
    private void btnKaydetClick(final String turAd,final String turDetay,final String turResim, final Double turEnlem,final Double turBoylam,final String tur,final String durum) {
        progressDialog.show();

        apiInterface  = ApiClient.getApiClient().create(ApiInterface.class);
        Call<dataTur> call= apiInterface.saveData(turAd,turDetay,turResim,turEnlem,turBoylam,tur,durum);

        //Kaydetme işlemleri
        call.enqueue(new Callback<dataTur>() {
            @Override
        public void onResponse(@NonNull  Call<dataTur> call, @NonNull Response<dataTur> response) {
            progressDialog.dismiss();
            if( response.isSuccessful() && response.body() !=null){
                Boolean success  = response.body().getSuccess();

                if(success){
                    Toast.makeText(getApplicationContext(),turAd+ " Başarılı bir Şekilde Kaydedildi",Toast.LENGTH_SHORT).show();
                    btnVazgecClick();

                }else {
                    Toast.makeText(getApplicationContext(),"Kayıt eklenirken bir hata oluştu.",Toast.LENGTH_SHORT).show();
                }
            }
        }

        @Override
        public void onFailure(@NonNull  Call<dataTur> call, @NonNull Throwable t) {
            progressDialog.dismiss();
            Toast.makeText(getApplicationContext(),"internet bağlantınızı kontrol ediniz!!!",Toast.LENGTH_SHORT).show();

        }
    });
    }

    //geri butonu
    private void btnVazgecClick() {
        Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
        startActivity(intent);
        finish();
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

        txtBaslik = (TextView) findViewById(R.id.txtBaslik);

        groupTur = (RadioGroup) findViewById(R.id.groupTur);
        groupGonder = (RadioGroup) findViewById(R.id.groupGonder);

        radioBitki = (RadioButton) findViewById(R.id.radioBitki);
        radioKus = (RadioButton) findViewById(R.id.radioKus);
        radioAktif = (RadioButton) findViewById(R.id.radioAktif);
        radioPasif = (RadioButton) findViewById(R.id.radioPasif);

        btnKaydet = (Button) findViewById(R.id.btnKaydet);
        btnVazgec = (Button) findViewById(R.id.btnVazgec);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Lütfen Bekleyiniz...");


    }

    //Crop İmage İşlemleri
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {

                resultUri = result.getUri();
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

private String imageToString(){
//Resim işlemleri resultUri -> resim yoluu bildirme
    try {
        bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),resultUri);
    } catch (IOException e) {
        e.printStackTrace();
    }

    //Resmi yazıya çevirme sunucuya post etme
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,50,byteArrayOutputStream);
        byte[] imgByte = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imgByte,Base64.DEFAULT);
}

    //Bitiş
}
