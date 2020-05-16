package com.rifaikuci.yeni.yerler.kesfetme.login_islemleri;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.rifaikuci.yeni.yerler.kesfetme.API.ApiClient;
import com.rifaikuci.yeni.yerler.kesfetme.API.ApiInterface;
import com.rifaikuci.yeni.yerler.kesfetme.activities.Ana_ekran;
import com.rifaikuci.yeni.yerler.kesfetme.R;
import com.rifaikuci.yeni.yerler.kesfetme.datas.dataKullanici;
import com.rifaikuci.yeni.yerler.kesfetme.datas.dataTur;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static java.lang.Integer.parseInt;

public class signIn extends AppCompatActivity {

    EditText txtKullaniciAdi, txtSifre;
    Button btnGiris, btnYeniHesap;

    TextView txtKayitOlmadan, txtKapat;
    String kullaniciAdi, sifre;
    ProgressDialog progressDialog;
    ApiInterface apiInterface;
    ArrayList<dataTur> data;

    SessionManager sessionManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        transparanEkran();
        variableDesc();

        btnGiris.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnGirisClick();
            }
        });

        btnYeniHesap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnYeniHesapClick();
            }
        });

        txtKapat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtKapatClick();
            }
        });
    }

    private void txtKapatClick() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Uyarı")
                .setMessage("Uygulamayı kapatmak ister misiniz?")
                .setPositiveButton("Evet", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                        Toast.makeText(getApplicationContext(), "Uygulamayı kapatılıyo...", Toast.LENGTH_SHORT).show();

                    }
                })
                .setNegativeButton("Hayır", null)
                .show();
    }

    private void btnYeniHesapClick() {
        Intent intent = new Intent(getApplicationContext(), signUp.class);
        startActivity(intent);
    }

    private void btnGirisClick() {
        kullaniciAdi = txtKullaniciAdi.getText().toString().trim();
        sifre = txtSifre.getText().toString().trim();

        if (kullaniciAdi.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Kullanıcı Adı giriniz!!!", Toast.LENGTH_SHORT).show();
        } else if (sifre.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Sifre giriniz!!!", Toast.LENGTH_SHORT).show();

        } else {

            progressDialog.show();
            apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
            Call<dataKullanici> call = apiInterface.login(kullaniciAdi, sifre);

            call.enqueue(new Callback<dataKullanici>() {

                @Override
                public void onResponse(Call<dataKullanici> call, Response<dataKullanici> response) {
                    progressDialog.dismiss();
                    Boolean success = response.body().getSuccess();

                    if (response.isSuccessful() && response.body() != null) {

                        if (success) {

                            if (response.body().getMessage().equalsIgnoreCase("mailyok")) {
                                Toast.makeText(getApplicationContext(), "Böyle bir hesap bulunmamaktadır!!!", Toast.LENGTH_SHORT).show();
                            } else if (response.body().getMessage().equalsIgnoreCase("sifrehata")) {
                                Toast.makeText(getApplicationContext(), "Şifre hatalı girilmiştir!!!", Toast.LENGTH_SHORT).show();
                            } else if (response.body().getMessage().equalsIgnoreCase("pasifkullanici")) {
                                Toast.makeText(getApplicationContext(), "Kullanıcı durumu pasif edilmiştir!!!", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getApplicationContext(), "Başarılı", Toast.LENGTH_SHORT).show();

                                int id = response.body().getId();
                                String adsoyad = response.body().getAdSoyad();
                                String resim = response.body().getResim();

                                sessionManager.createSession(adsoyad, resim, String.valueOf(id));
                                Intent intent = new Intent(getApplicationContext(), Ana_ekran.class);
                                startActivity(intent);
                            }

                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "internet bağlantınızı kontrol ediniz!!!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<dataKullanici> call, Throwable t) {

                }
            });
        }
    }


    private void variableDesc() {

        txtKullaniciAdi = (EditText) findViewById(R.id.txtKullaniciAdi);
        txtSifre = (EditText) findViewById(R.id.txtSifre);
        txtKayitOlmadan = (TextView) findViewById(R.id.txtKayitOlmadan);
        txtKapat = (TextView) findViewById(R.id.txtKapat);

        btnGiris = (Button) findViewById(R.id.btnGiris);
        btnYeniHesap = (Button) findViewById(R.id.btnYeniHesap);

        txtKayitOlmadan.setPaintFlags(txtKayitOlmadan.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        txtKapat.setPaintFlags(txtKapat.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Lütfen Bekleyiniz...");

        sessionManager = new SessionManager(this);
    }

    //ekranı transpan yapar
    public void transparanEkran() {
        if (Build.VERSION.SDK_INT >= 19) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        } else {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

}
