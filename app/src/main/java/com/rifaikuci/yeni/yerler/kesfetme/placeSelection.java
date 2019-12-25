package com.rifaikuci.yeni.yerler.kesfetme;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.model.LatLng;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;


public class placeSelection extends AppCompatActivity {


    TextView txtBack;
    Button btnRota;
    ListView listPlace;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_selection);
        transparanEkran();
        variableDesc();

        txtBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { txtBackClick(); }});

        dataInfo[] datas = new dataInfo[]{
          new dataInfo("Tavus Kuşu ","yumurtlayan omurgalılardan, akciğerli, sıcak kanlı, vücudu tüylerle örtülü, gagalı, iki ayaklı, iki kanatlı uçucu hayvanların ortak adı",R.drawable.bird,  new LatLng(37.717430, 30.286363),"h"),
          new dataInfo("Tahta Kurusu","yumurtlayan omurgalılardan, akciğerli, sıcak kanlı, vücudu tüylerle örtülü, gagalı, iki ayaklı, iki kanatlı uçucu hayvanların ortak adı",R.drawable.bird,  new LatLng(37.717430, 30.286363),"h"),
          new dataInfo("deneme","yumurtlayan omurgalılardan, akciğerli, sıcak kanlı, vücudu tüylerle örtülü, gagalı, iki ayaklı, iki kanatlı uçucu hayvanların ortak adı",R.drawable.bird,  new LatLng(37.717430, 30.286363),"h"),
          new dataInfo("deneke","yumurtlayan omurgalılardan, akciğerli, sıcak kanlı, vücudu tüylerle örtülü, gagalı, iki ayaklı, iki kanatlı uçucu hayvanların ortak adı",R.drawable.dene,  new LatLng(37.717430, 30.286363),"h"),
        };


        classAdapter adapter = new classAdapter(getApplicationContext(),R.layout.custom_view,datas);
        listPlace.setAdapter(adapter);
    }


    // geri butonu
    private void txtBackClick() {
        Intent intent = new Intent(getApplicationContext(),MapsActivity.class);
        startActivity(intent);
    }


    //Değişkenleri Tanımlama
    private void variableDesc() {
        txtBack   = (TextView) findViewById(R.id.txtBack);
        btnRota   = (Button) findViewById(R.id.btnRota);
        listPlace = (ListView) findViewById(R.id.listPlace);


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