package com.rifaikuci.yeni.yerler.kesfetme;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.model.LatLng;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class placeSelection extends AppCompatActivity {


    TextView txtBack;
    Button btnRota;
    classAdapter adapter;
    ListView  listPlace;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_selection);
        transparanEkran();
        variableDesc();

        txtBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { txtBackClick(); }});





        adapter = new classAdapter(this, MapsActivity.data);
        listPlace.setAdapter(adapter);
        adapter.updateRecords(MapsActivity.data);


        //Satır click İslemleri
        listPlace.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                dataInfo model = MapsActivity.data.get(i);

                if (model.isSelected()) {
                    model.setSelected(false);
                }
                else { model.setSelected(true); }

                MapsActivity.data.set(i, model);

                adapter.updateRecords(MapsActivity.data);
            }
        });

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