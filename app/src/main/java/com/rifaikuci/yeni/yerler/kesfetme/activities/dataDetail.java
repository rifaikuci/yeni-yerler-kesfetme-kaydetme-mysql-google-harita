package com.rifaikuci.yeni.yerler.kesfetme.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.rifaikuci.yeni.yerler.kesfetme.API.ApiClient;
import com.rifaikuci.yeni.yerler.kesfetme.API.ApiInterface;
import com.rifaikuci.yeni.yerler.kesfetme.R;
import com.rifaikuci.yeni.yerler.kesfetme.datas.dataTur;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class dataDetail extends AppCompatActivity {

    TextView txtBack, txtBaslik, txtDetail;
    ImageView image, volume, edit, delete;
    boolean sesDurumu = false;
    TextToSpeech textToSpeech;
    Intent intent;
    int id;

    ProgressDialog progressDialog;
    ApiInterface apiInterface;
    String gelenId, baslik, resim, detay, turResim, turResimYol, tarihDuzenli, kesfetDetay = "";
    String[] gecici;
    ArrayList<dataTur> aktar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_detail);
        transparanEkran();
        variableDesc();

        txtBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtBackClick();
            }
        });
        kesfetDetay = intent.getStringExtra("kesfet");
        if ("kesfet".equalsIgnoreCase(kesfetDetay)) {
            aktar = KesfetActivity.data;
        }else{
            aktar =Koleksiyonlarim.data;
        }

            textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    textToSpeech.setLanguage(new Locale("tr-TR"));
                }
            }
        });

        gelenId = intent.getStringExtra("tur");


        try {

            baslik =aktar.get(Integer.parseInt(gelenId)).getTurAd();
            resim = aktar.get(Integer.parseInt(gelenId)).getTurResim();
            detay = aktar.get(Integer.parseInt(gelenId)).getTurDetay();

            tarihDuzenli = tarih(aktar.get(Integer.parseInt(gelenId)).getTurKayitTarih());
            txtBaslik.setText(baslik);
            Picasso.get().load(resim).into(image);

        } catch (Exception e) {
            System.out.println(e.toString());
        }

        if ("kesfet".equalsIgnoreCase(kesfetDetay)) {
            try {
                txtDetail.setText("Kayıt Tarihi : " + tarihDuzenli + " \n" + detay + "\n\n\nPaylaşan Kullanıcı: "
                        + aktar.get(Integer.parseInt(gelenId)).getPaylasanKullanici());

            } catch (Exception e) {
                e.printStackTrace();
            }
            delete.setVisibility(View.INVISIBLE);
            edit.setVisibility(View.INVISIBLE);
        } else {
            try {
                txtDetail.setText("Kayıt Tarihi : " + tarihDuzenli + " \n" + detay);

            } catch (Exception e) {
                e.printStackTrace();
            }

            delete.setVisibility(View.VISIBLE);
            edit.setVisibility(View.VISIBLE);
        }


        volume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                volumeClick();
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editClick();
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteClick();
            }
        });

    }

    private void deleteClick() {

        // progressDialog.show();
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        id = aktar.get(Integer.parseInt(gelenId)).getid();


        AlertDialog.Builder builder = new AlertDialog.Builder(dataDetail.this);
        builder.setTitle("Uyarı");
        builder.setMessage(baslik + " Türünü silmek istediğinizden emin misiniz? ");

        builder.setPositiveButton("Evet", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                progressDialog.show();
                turResim = aktar.get(Integer.parseInt(gelenId)).getTurResim();
                gecici = turResim.split("/");
                turResimYol = gecici[gecici.length - 1];

                Call<dataTur> call = apiInterface.deleteTur(id, turResimYol);

                call.enqueue(new Callback<dataTur>() {
                    @Override
                    public void onResponse(Call<dataTur> call, Response<dataTur> response) {
                        progressDialog.dismiss();

                        if (response.isSuccessful() && response.body() != null) {


                            Boolean success = response.body().getSuccess();
                            if (success) {
                                Toast.makeText(getApplicationContext(), baslik + " Silme işlemi tamamlandı", Toast.LENGTH_SHORT).show();

                                txtBackClick();

                            } else {
                                Toast.makeText(getApplicationContext(), baslik + " Silerken bir hata oluştu", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }


                    @Override
                    public void onFailure(Call<dataTur> call, Throwable t) {
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), "internet bağlantınızı kontrol ediniz!!!", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
        builder.setNegativeButton("Hayır", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), "Tür silme işlemi iptal edildi", Toast.LENGTH_SHORT).show();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();

    }

    private void editClick() {

        Intent intent = new Intent(getApplicationContext(), data_add.class);
        intent.putExtra("gelis", "edit");
        intent.putExtra("guncelleId", gelenId);

        startActivity(intent);
    }

    private void volumeClick() {
        if (sesDurumu == false) {
            sesDurumu = true;
            volume.setImageResource(R.drawable.ic_volume_up_black_24dp);

            textToSpeech.speak(txtBaslik.getText() + " " + txtDetail.getText(), TextToSpeech.QUEUE_FLUSH, null);
        } else {
            sesDurumu = false;
            onPause();
            volume.setImageResource(R.drawable.ic_volume_off_black_24dp);

        }
    }

    private void txtBackClick() {
        Intent intent = new Intent(getApplicationContext(), Koleksiyonlarim.class);
        startActivity(intent);
    }

    private void variableDesc() {
        txtBack = (TextView) findViewById(R.id.txtBack);
        txtBaslik = (TextView) findViewById(R.id.txtBaslik);
        txtDetail = (TextView) findViewById(R.id.txtDetail);
        image = (ImageView) findViewById(R.id.image);
        volume = (ImageView) findViewById(R.id.volume);
        edit = (ImageView) findViewById(R.id.edit);
        delete = (ImageView) findViewById(R.id.delete);

        intent = getIntent();

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Yükleniyor...");
    }

    public void onPause() {
        super.onPause();
        if (textToSpeech != null) {
            textToSpeech.stop();

        }
    }

    @Override
    public void onBackPressed() {
        if ("kesfet".equalsIgnoreCase(kesfetDetay)) {

            intent = new Intent(getApplicationContext(), KesfetActivity.class);
        } else {
            intent = new Intent(getApplicationContext(), Koleksiyonlarim.class);

        }
    }

    //ekranı transpan yapar
    public void transparanEkran() {
        if (Build.VERSION.SDK_INT >= 19) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        } else {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    public String tarih(String s) {

        String[] separated = s.split(" ");
        String[] gecici = separated[0].split("-");
        int ayInt = Integer.parseInt(gecici[1]);
        String yil = "";
        String gun = "";
        String ay = "";
        String tarih;
        gun = gecici[2];
        yil = gecici[0];

        switch (ayInt) {
            case 1:
                ay = "Ocak";
                break;
            case 2:
                ay = "Şubat";
                break;
            case 3:
                ay = "Mart";
                break;
            case 4:
                ay = "Nisan";
                break;
            case 5:
                ay = "Mayıs";
                break;
            case 6:
                ay = "Haziran";
                break;
            case 7:
                ay = "Temmuz";
                break;
            case 8:
                ay = "Ağustos";
                break;
            case 9:
                ay = "Eylül";
                break;
            case 10:
                ay = "Ekim";
                break;
            case 11:
                ay = "Kasım";
                break;
            case 12:
                ay = "Aralık";
                break;
            default:
                tarih = "Hatalı Değer";
        }
        tarih = gun + " " + ay + " " + yil;
        return tarih;
    }
}

