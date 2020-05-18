package com.rifaikuci.yeni.yerler.kesfetme.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ebanx.swipebtn.OnStateChangeListener;
import com.ebanx.swipebtn.SwipeButton;
import com.rifaikuci.yeni.yerler.kesfetme.R;
import com.rifaikuci.yeni.yerler.kesfetme.login_islemleri.SessionManager;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class Ana_ekran extends AppCompatActivity {

    SwipeButton swipe_koleksiyonum, swipe_kesfet, swipe_cikis_yap, swipe_uygulamayi_kapat;
    TextView bilgiAdsoyad;
    ImageView profile_image;
    Intent intent;
    int id = 0;
    String adsoyad, resim;

    SessionManager sessionManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ana_ekran);
        transparanEkran();
        variableDesc();

        sessionManager.checkLogin();
        HashMap<String, String> user = sessionManager.getUserDetail();


        try {
            adsoyad = user.get(sessionManager.NAME);
            resim = user.get(sessionManager.RESIM);
            id = Integer.parseInt(user.get(sessionManager.ID));

            bilgiAdsoyad.setText(adsoyad);
            Picasso.get().load(resim).into(profile_image);
        } catch (Exception e) {
            e.printStackTrace();
        }


        swipe_koleksiyonum.setOnStateChangeListener(new OnStateChangeListener() {
            @Override
            public void onStateChange(boolean active) {
                koleksiyonumClick();

            }
        });
        swipe_kesfet.setOnStateChangeListener(new OnStateChangeListener() {
            @Override
            public void onStateChange(boolean active) {
                kesfetClick();

            }
        });

        swipe_cikis_yap.setOnStateChangeListener(new OnStateChangeListener() {
            @Override
            public void onStateChange(boolean active) {
                cikis_yapClick();
            }
        });

        swipe_uygulamayi_kapat.setOnStateChangeListener(new OnStateChangeListener() {
            @Override
            public void onStateChange(boolean active) {
                kapatClick();
            }
        });

    }

    private void kapatClick() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Uyarı")
                .setMessage("Uygulamayı kapatmak ister misiniz?")
                .setPositiveButton("Evet", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                        System.exit(0);
                        Toast.makeText(getApplicationContext(), "Uygulamayı kapatılıyo...", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Hayır", null)
                .show();
    }

    private void cikis_yapClick() {
        Toast.makeText(getApplicationContext(), "Çıkış yapılıyor", Toast.LENGTH_SHORT).show();
        sessionManager.logout();

    }

    private void kesfetClick() {
        Toast.makeText(getApplicationContext(), "Kesfet", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getApplicationContext(), KesfetActivity.class);
        startActivity(intent);
    }

    private void koleksiyonumClick() {
        Intent intent = new Intent(getApplicationContext(), Koleksiyonlarim.class);
        intent.putExtra("id", id);
        startActivity(intent);
    }

    private void variableDesc() {

        swipe_koleksiyonum = (SwipeButton) findViewById(R.id.swipe_koleksiyonum);
        swipe_kesfet = (SwipeButton) findViewById(R.id.swipe_kesfet);
        swipe_cikis_yap = (SwipeButton) findViewById(R.id.swipe_cikis_yap);
        swipe_uygulamayi_kapat = (SwipeButton) findViewById(R.id.swipe_uygulamayi_kapat);

        bilgiAdsoyad = (TextView) findViewById(R.id.bilgiAdsoyad);

        profile_image = (ImageView) findViewById(R.id.profile_image);

        intent = getIntent();

        sessionManager = new SessionManager(this);
    }

    public void transparanEkran() {
        if (Build.VERSION.SDK_INT >= 19) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        } else {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

}
