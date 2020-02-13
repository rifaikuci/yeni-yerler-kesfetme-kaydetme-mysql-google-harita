package com.rifaikuci.yeni.yerler.kesfetme;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.model.LatLng;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class placeSelection extends AppCompatActivity {


    TextView txtBack;
    Button btnRota;
    classAdapter adapter;
    ListView  listPlace;
    ArrayList<dataInfo> gecici;
    EditText search;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_selection);
        transparanEkran();
        variableDesc();

        gecici = new ArrayList<>();
        txtBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { txtBackClick(); }});

        for (dataInfo data : MapsActivity.data){ gecici.add(data); }

        adapter = new classAdapter(this, MapsActivity.data);
        listPlace.setAdapter(adapter);


        //edittext değerine göre view değeri değişir.
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            //Yazılar değiştikçe liste güncelleniyor.
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                gecici.clear();
                for (dataInfo data : MapsActivity.data){

                    if(data.getTurAd().contains(search.getText().toString().trim())==true){
                        gecici.add(data);
                    }
                }
                adapter.updateRecords(gecici);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        adapter.updateRecords(MapsActivity.data);


        //Satır click İslemleri
        listPlace.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                dataInfo model = gecici.get(i);

                if (model.isSelected()) {
                    model.setSelected(false);
                }
                else { model.setSelected(true); }

                gecici.set(i, model);

                adapter.updateRecords(gecici);
            }
        });

        btnRota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnRotaClick();
            }
        });

    }

    private void btnRotaClick() {
        int a =0;
        for (dataInfo fe: MapsActivity.data)
        {
            if(fe.isSelected()){
                a++;
            }
        }
        System.out.println("Seçilen sayı"+a);

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
        search    = (EditText) findViewById(R.id.search);

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