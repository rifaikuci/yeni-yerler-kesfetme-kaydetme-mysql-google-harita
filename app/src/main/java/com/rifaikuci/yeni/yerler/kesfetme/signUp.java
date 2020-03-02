package com.rifaikuci.yeni.yerler.kesfetme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.rifaikuci.yeni.yerler.kesfetme.API.ApiClient;
import com.rifaikuci.yeni.yerler.kesfetme.API.ApiInterface;
import com.rifaikuci.yeni.yerler.kesfetme.datas.dataKullanici;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class signUp extends AppCompatActivity {

    EditText txtName,txtMail,txtSifre;
    Button btnDevam;
    LinearLayout linearBack;
    ProgressDialog progressDialog;

    String adSoyad, mail,sifre;

    ApiInterface apiInterface;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        transparanEkran();
        variableDesc();

        linearBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearBackClick();
            }
        });

        btnDevam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                adSoyad=txtName.getText().toString().trim();
                sifre =txtSifre.getText().toString().trim();
                mail = txtMail.getText().toString().trim();

                if(adSoyad.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Ad soyad hesabı giriniz",Toast.LENGTH_SHORT).show();
                }

                else  if (sifre.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Sifre giriniz.",Toast.LENGTH_SHORT).show();
                }

                else if(mail.isEmpty()){
                    Toast.makeText(getApplicationContext(),"E mail hesabı giriniz",Toast.LENGTH_SHORT).show();
                }
                else {
                    if(android.util.Patterns.EMAIL_ADDRESS.matcher(mail).matches())
                        { btnDevamClick(adSoyad,sifre,mail); }

                    else
                    { Toast.makeText(getApplicationContext(),"Mail formatında değil ",Toast.LENGTH_SHORT).show(); }

                }
            }
        });
    }

    private void btnDevamClick(final String adSoyad,final String mail,final String sifre) {

        progressDialog.show();
        apiInterface  = ApiClient.getApiClient().create(ApiInterface.class);
        Call<dataKullanici> call= apiInterface.saveKullanici(adSoyad,sifre,mail);

        call.enqueue(new Callback<dataKullanici>() {
            @Override
            public void onResponse(@NonNull Call<dataKullanici> call, @NonNull Response<dataKullanici> response) {
                progressDialog.dismiss();


                if( response.isSuccessful() && response.body() !=null){

                    Boolean success  = response.body().getSuccess();

                    if(success){
                        Toast.makeText(getApplicationContext(),adSoyad+ " Başarılı bir şekilde eklendi",Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(getApplicationContext(),adSoyad+" Kullanıcısı eklenirken bir hata oluştu",Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<dataKullanici> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(),"internet bağlantınızı kontrol ediniz!!!",Toast.LENGTH_SHORT).show();

            }

        });

    }

    private void linearBackClick() {
        Intent intent = new Intent(getApplicationContext(),signIn.class);
        startActivity(intent);
    }

    private void variableDesc() {

        txtName  = (EditText) findViewById(R.id.txtName);
        txtMail  = (EditText) findViewById(R.id.txtMail);
        txtSifre = (EditText) findViewById(R.id.txtSifre);

        btnDevam = (Button)   findViewById(R.id.btnDevam);

        linearBack = (LinearLayout) findViewById(R.id.linearBack);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Lütfen Bekleyiniz...");
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
