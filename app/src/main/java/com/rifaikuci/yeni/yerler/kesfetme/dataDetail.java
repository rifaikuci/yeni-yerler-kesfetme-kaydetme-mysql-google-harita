package com.rifaikuci.yeni.yerler.kesfetme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.Locale;

public class dataDetail extends AppCompatActivity {

    TextView txtBack, txtBaslik,txtDetail;
    ImageView image,volume,edit,delete;
    boolean sesDurumu=false;
    TextToSpeech textToSpeech;
    Intent intent;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_detail);
        transparanEkran();
        variableDesc();

        txtBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { txtBackClick(); }});



        textToSpeech=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status!=TextToSpeech.ERROR)
                {
                    textToSpeech.setLanguage(new Locale("tr-TR"));
                }
            }
        });


        String gelenId=intent.getStringExtra("tur");


        try {

            String baslik = MapsActivity.data.get(Integer.parseInt(gelenId)).getTurAd();
            String resim= MapsActivity.data.get(Integer.parseInt(gelenId)).getTurResim();
            String detay= MapsActivity.data.get(Integer.parseInt(gelenId)).getTurDetay();

            String tarihDuzenli=  tarih(MapsActivity.data.get(Integer.parseInt(gelenId)).getTurKayitTarih());
            txtBaslik.setText(baslik);
            Picasso.get().load(resim).into(image);
            txtDetail.setText("Kayıt Tarihi : " + tarihDuzenli + " \n"+ detay);
        }catch (Exception e ){
            System.out.println(e.toString());
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
        Toast.makeText(getApplicationContext(),"Silme işlemleri yapılacak",Toast.LENGTH_LONG).show();
    }

    private void editClick() {

        Toast.makeText(getApplicationContext(),"Düzenleme işlemleri yapılacak",Toast.LENGTH_LONG).show();
    }

    private void volumeClick() {
        if(sesDurumu==false)
        {
            sesDurumu=true;
            volume.setImageResource(R.drawable.ic_volume_up_black_24dp);

            textToSpeech.speak(txtBaslik.getText()+" "+txtDetail.getText(),TextToSpeech.QUEUE_FLUSH,null);

        }
        else
        {
            sesDurumu=false;
            onPause();
            volume.setImageResource(R.drawable.ic_volume_off_black_24dp);

        }
    }

    private void txtBackClick() {
        Intent intent = new Intent(getApplicationContext(),MapsActivity.class);
        startActivity(intent);
    }

    private void variableDesc() {
        txtBack    = (TextView) findViewById(R.id.txtBack);
        txtBaslik  = (TextView) findViewById(R.id.txtBaslik);
        txtDetail  = (TextView) findViewById(R.id.txtDetail);
        image      = (ImageView) findViewById(R.id.image);
        volume     = (ImageView) findViewById(R.id.volume);
        edit       = (ImageView) findViewById(R.id.edit);
        delete     = (ImageView) findViewById(R.id.delete);

        intent  = getIntent();
    }
    public  void  onPause() {
        super.onPause();
        if (textToSpeech != null) {
            textToSpeech.stop();

        }
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

    public String tarih(String s){

        String[] separated = s.split(" ");
        String[] gecici = separated[0].split("-");
        int ayInt =Integer.parseInt( gecici[1]);
        String yil= "";
        String gun= "";
        String ay ="";
                String tarih;
        gun =gecici[2];
        yil =gecici[0];



        switch (ayInt){
            case 1: ay = "Ocak"; break;
            case 2: ay = "Şubat"; break;
            case 3: ay = "Mart"; break;
            case 4: ay = "Nisan"; break;
            case 5: ay = "Mayıs"; break;
            case 6: ay = "Haziran"; break;
            case 7: ay = "Temmuz"; break;
            case 8: ay = "Ağustos"; break;
            case 9: ay = "Eylül"; break;
            case 10: ay = "Ekim"; break;
            case 11: ay = "Kasım"; break;
            case 12: ay = "Aralık"; break;
            default: tarih = "Hatalı Değer";
        }
       tarih =gun +" "+ay+ " "+yil ;
        return tarih;
    }
}

