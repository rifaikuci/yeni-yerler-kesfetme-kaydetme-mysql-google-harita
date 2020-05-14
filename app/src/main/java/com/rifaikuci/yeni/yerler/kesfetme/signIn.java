package com.rifaikuci.yeni.yerler.kesfetme;

import androidx.appcompat.app.AppCompatActivity;

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

public class signIn extends AppCompatActivity {

    EditText txtKullaniciAdi,txtSifre;
    Button   btnGiris,btnYeniHesap;

    TextView txtKayitOlmadan;
    String kullaniciAdi,sifre;
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
    }

    private void btnYeniHesapClick() {
        Intent intent = new Intent(getApplicationContext(),signUp.class);
        startActivity(intent);
    }

    private void btnGirisClick() {
        kullaniciAdi =txtKullaniciAdi.getText().toString().trim();
        sifre = txtSifre.getText().toString().trim();

        if(kullaniciAdi.isEmpty()){
            System.out.println( "Kullanıcı Adı giriniz");
        }
        else  if (sifre.isEmpty()){
            System.out.println("Sifre giriniz.");
        }else{
            Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
            startActivity(intent);
            finish();
        }
        //Toast.makeText(getApplicationContext(),"Btn giriş ",Toast.LENGTH_SHORT).show();
    }


    private void variableDesc() {

        txtKullaniciAdi = (EditText) findViewById(R.id.txtKullaniciAdi);
        txtSifre        = (EditText) findViewById(R.id.txtSifre);
        txtKayitOlmadan = (TextView) findViewById(R.id.txtKayitOlmadan);

        btnGiris        = (Button) findViewById(R.id.btnGiris);
        btnYeniHesap        = (Button) findViewById(R.id.btnYeniHesap);

        txtKayitOlmadan.setPaintFlags(txtKayitOlmadan.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
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
