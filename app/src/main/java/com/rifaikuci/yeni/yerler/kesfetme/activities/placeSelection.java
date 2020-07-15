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
import android.widget.Toast;

import com.rifaikuci.yeni.yerler.kesfetme.R;
import com.rifaikuci.yeni.yerler.kesfetme.classAdapter;
import com.rifaikuci.yeni.yerler.kesfetme.datas.dataTur;
import com.rifaikuci.yeni.yerler.kesfetme.mapbox;

import java.util.ArrayList;


public class placeSelection extends AppCompatActivity {

    TextView txtBack;
    Button btnRota;
    classAdapter adapter;
    ListView listPlace;
    ArrayList<dataTur> gecici, aktar;
    dataTur model;
    public static ArrayList<dataTur> gidilecekYerler;

    EditText search;
    Intent intent;
    String gelis = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_selection);
        transparanEkran();
        variableDesc();
        gidilecekYerler = new ArrayList<>();

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

                model = aktar.get(i);
                System.out.println("Model " + model.getid());

                if (model.isSelected()) {
                    model.setSelected(false);
                    Toast.makeText(getApplicationContext(), "Rotadan " + model.getTurAd() + " Çıkarıldı.", Toast.LENGTH_SHORT).show();

                } else {
                    if (adapter.secilmis() < 12) {
                        model.setSelected(true);
                        Toast.makeText(getApplicationContext(), "Rotaya " + model.getTurAd() + " Eklendi.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Rotaya en fazla 12 yere gidebilirsiniz.", Toast.LENGTH_SHORT).show();
                    }
                }

                aktar.set(i, model);
                adapter.updateRecords(aktar);
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

        gidilecekYerler.clear();

        for (int i = 0; i < aktar.size(); i++) {

            if (aktar.get(i).isSelected() == true) {
                gidilecekYerler.add(new dataTur(aktar.get(i).getTurAd(), aktar.get(i).getTurDetay(), aktar.get(i).getTurEnlem(), aktar.get(i).getTurBoylam()));
            }
        }

        if (gidilecekYerler.size() <= 12 && gidilecekYerler.size() >= 2) {
            Intent intent = new Intent(getApplicationContext(), mapbox.class);

            startActivity(intent);
        } else {
            Toast.makeText(this, "En Az 2 yer Seçmeniz Gerekir", Toast.LENGTH_SHORT).show();
        }
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