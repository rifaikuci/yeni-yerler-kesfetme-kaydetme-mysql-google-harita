package com.rifaikuci.yeni.yerler.kesfetme;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.rifaikuci.yeni.yerler.kesfetme.API.ApiClient;
import com.rifaikuci.yeni.yerler.kesfetme.API.ApiInterface;
import com.rifaikuci.yeni.yerler.kesfetme.datas.dataKullanici;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class signUp extends AppCompatActivity {

    EditText txtName, txtMail, txtSifre;
    Button btnDevam;
    LinearLayout linearBack;
    ProgressDialog progressDialog;
    ImageView resim;

    String adSoyad, mail, sifre, kisiResim;

    ApiInterface apiInterface;

    Uri resultUri;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        transparanEkran();
        variableDesc();
        temizle();

        linearBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearBackClick();
            }
        });

        resim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resimClick();
            }
        });

        btnDevam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                adSoyad = txtName.getText().toString().trim();
                sifre = txtSifre.getText().toString().trim();
                mail = txtMail.getText().toString().trim();

                //Resmi yazıya çevirme
                try {
                    kisiResim = imageToString();
                } catch (Exception e) {
                    kisiResim = "";
                }

                if (adSoyad.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Ad soyad bilgisi giriniz", Toast.LENGTH_SHORT).show();
                } else if (sifre.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Sifre giriniz.", Toast.LENGTH_SHORT).show();
                } else if (kisiResim.equalsIgnoreCase("") == true) {
                    Toast.makeText(getApplicationContext(), "Resim Seçilmedi!!!", Toast.LENGTH_SHORT).show();
                } else if (mail.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "E mail hesabı giriniz", Toast.LENGTH_SHORT).show();
                } else {
                    if (android.util.Patterns.EMAIL_ADDRESS.matcher(mail).matches()) {
                        btnDevamClick(adSoyad, sifre, mail, kisiResim);
                    } else {
                        Toast.makeText(getApplicationContext(), "Mail formatında değil ", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });
    }

    private void resimClick() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        CropImage.activity().setGuidelines(CropImageView.Guidelines.ON).start(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {

                resultUri = result.getUri();
                resim.setImageURI(resultUri);

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }

    private void btnDevamClick(final String adSoyad, final String mail, final String sifre, final String kisiResim) {

        progressDialog.show();
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<dataKullanici> call = apiInterface.saveKullanici(adSoyad, sifre, mail, kisiResim);

        call.enqueue(new Callback<dataKullanici>() {
            @Override
            public void onResponse(@NonNull Call<dataKullanici> call, @NonNull Response<dataKullanici> response) {
                progressDialog.dismiss();


                if (response.isSuccessful() && response.body() != null) {

                    Boolean success = response.body().getSuccess();
                    String isSame = response.body().getMessage().toString();

                    if (isSame.equalsIgnoreCase("same") && success) {
                        Toast.makeText(getApplicationContext(), "Daha önceden aynı mail kullanılmıştır!!!", Toast.LENGTH_LONG).show();

                    } else if (isSame.equalsIgnoreCase("Basarili") && success) {
                        Toast.makeText(getApplicationContext(), adSoyad + " Başarılı bir şekilde eklendi", Toast.LENGTH_LONG).show();
                        temizle();
                        linearBackClick();
                    } else {
                        Toast.makeText(getApplicationContext(), adSoyad + " Kullanıcısı eklenirken bir hata oluştu", Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<dataKullanici> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), "internet bağlantınızı kontrol ediniz!!!", Toast.LENGTH_SHORT).show();
            }

        });

    }

    private void linearBackClick() {
        Intent intent = new Intent(getApplicationContext(), signIn.class);
        startActivity(intent);
    }

    private void variableDesc() {

        txtName = (EditText) findViewById(R.id.txtName);
        txtMail = (EditText) findViewById(R.id.txtMail);
        txtSifre = (EditText) findViewById(R.id.txtSifre);

        resim = (ImageView) findViewById(R.id.resim);

        btnDevam = (Button) findViewById(R.id.btnDevam);

        linearBack = (LinearLayout) findViewById(R.id.linearBack);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Lütfen Bekleyiniz...");
    }

    //ekranı transpan yapar
    public void transparanEkran() {
        if (Build.VERSION.SDK_INT >= 19) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        } else {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    private String imageToString() {
//Resim işlemleri resultUri -> resim yoluu bildirme
        try {
            bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), resultUri);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Resmi yazıya çevirme sunucuya post etme
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
        byte[] imgByte = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imgByte, Base64.DEFAULT);
    }

    private void temizle() {
        txtMail.setText("");
        txtName.setText("");
        txtSifre.setText("");
        resim.setImageResource(R.drawable.selection);
    }


}
