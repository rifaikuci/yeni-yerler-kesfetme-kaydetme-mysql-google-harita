package com.rifaikuci.yeni.yerler.kesfetme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

public class signUp extends AppCompatActivity {

    EditText txtName,txtMail,txtSifre;
    Button btnDevam;
    LinearLayout linearBack;

    String name, mail,sifre;
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
                btnDevamClick();
            }
        });
    }

    private void btnDevamClick() {

            name=txtName.getText().toString().trim();
            sifre =txtSifre.getText().toString().trim();
            mail = txtMail.getText().toString().trim();

        if(name.isEmpty()){
            System.out.println( "Ad soyad hesabı giriniz");
        }
        else  if (sifre.isEmpty()){
            System.out.println("Sifre giriniz.");
        }else if(mail.isEmpty()){
            System.out.println( "E mail hesabı giriniz");
        }else
        {
            if(android.util.Patterns.EMAIL_ADDRESS.matcher(mail).matches())
            {
                System.out.println("Mail formatında ");

            }else
            {
                System.out.println("Mail formatında değil ");

            }
        }
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
