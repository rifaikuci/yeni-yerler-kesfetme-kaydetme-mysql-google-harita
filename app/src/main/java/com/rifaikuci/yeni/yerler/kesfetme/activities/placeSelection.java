package com.rifaikuci.yeni.yerler.kesfetme.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.rifaikuci.yeni.yerler.kesfetme.R;
import com.rifaikuci.yeni.yerler.kesfetme.classAdapter;
import com.rifaikuci.yeni.yerler.kesfetme.datas.dataTur;

import java.sql.SQLOutput;
import java.util.ArrayList;


public class placeSelection extends AppCompatActivity {


    TextView txtBack;
    Button btnRota;
    classAdapter adapter;
    ListView listPlace;
    ArrayList<dataTur> gecici;
    ArrayList<dataTur> aktar;
    EditText search;
    Intent intent;
    String gelis = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_selection);
        transparanEkran();
        variableDesc();

        intent = getIntent();
        gelis = intent.getStringExtra("gelis");

        if ("koleksiyon".equalsIgnoreCase(gelis)) {
            System.out.println("Gelis türü koleksiyon");
            aktar = Koleksiyonlarim.data;

        } else {
            System.out.println("Gelis türü kesfet");
            aktar = KesfetActivity.data;
        }

        gecici = new ArrayList<>();
        txtBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtBackClick();
            }
        });

        for (dataTur data : aktar) {
            gecici.add(data);
        }

        adapter = new classAdapter(this, aktar);
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
                for (dataTur data : aktar) {

                    if (data.getTurAd().contains(search.getText().toString().trim()) == true) {
                        gecici.add(data);
                    }
                }
                adapter.updateRecords(gecici);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        adapter.updateRecords(aktar);


        //Satır click İslemleri
        listPlace.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                dataTur model = gecici.get(i);

                if (model.isSelected()) {
                    model.setSelected(false);
                } else {
                    model.setSelected(true);
                }

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
        int a = 0;
        for (dataTur fe : aktar) {
            if (fe.isSelected()) {
                a++;
            }
        }
        System.out.println("Seçilen sayı" + a);

    }

    // geri butonu
    private void txtBackClick() {

        if ("koleksiyon".equalsIgnoreCase(gelis)) {
            intent = new Intent(getApplicationContext(), Koleksiyonlarim.class);
        } else {
            intent = new Intent(getApplicationContext(), KesfetActivity.class);
        }
        startActivity(intent);

    }

    //Değişkenleri Tanımlama
    private void variableDesc() {

        txtBack = (TextView) findViewById(R.id.txtBack);
        btnRota = (Button) findViewById(R.id.btnRota);
        listPlace = (ListView) findViewById(R.id.listPlace);
        search = (EditText) findViewById(R.id.search);

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