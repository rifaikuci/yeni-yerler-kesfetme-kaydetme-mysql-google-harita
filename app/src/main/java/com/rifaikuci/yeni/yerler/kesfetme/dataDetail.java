package com.rifaikuci.yeni.yerler.kesfetme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Locale;

public class dataDetail extends AppCompatActivity {

    TextView txtBack, txtBaslik,txtDetail;
    ImageView image,volume;
    boolean sesDurumu=false;
    TextToSpeech textToSpeech;

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

        volume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                volumeClick();
            }
        });

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
}

