package com.example.budapestquest.ActionCards;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View;

import com.example.budapestquest.GameController;
import com.example.budapestquest.Karakterek.Karakter;
import com.example.budapestquest.QRManager;
import com.example.budapestquest.R;
import com.google.zxing.WriterException;

import java.util.Random;

public class HarcAct extends AppCompatActivity {

    private TextView nyertes;
    private TextView vesztes;
    private ImageView qrkod;

    Karakter enemy;
    boolean enkezd;
    Random rand;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_harc);

        nyertes = findViewById(R.id.nyertesIndikator);
        vesztes = findViewById(R.id.vesztesIndikator);
        qrkod = findViewById(R.id.qrView2);

        if((enemy = Karakter.Deserialize(getIntent().getStringExtra("ENEMY"))) == null) {
            Toast.makeText(getApplicationContext(), "Hiba a karakter beolvasásnál.", Toast.LENGTH_LONG).show();
            this.finish();
        }
        enkezd = getIntent().getBooleanExtra("ENKEZD", false);
        rand = new Random(GameController.En.RandFactor ^ enemy.RandFactor);

        Bitmap bitmap = QRManager.TextToImageEncode(QRManager.QR_HARC2, GameController.En.Serialize());
        qrkod.setImageBitmap(bitmap);

        /*if(enkezd) {
            Bitmap bitmap = QRManager.TextToImageEncode(QRManager.QR_HARC2, GameController.En.Serialize());
            qrkod.setImageBitmap(bitmap);
        }else
            qrkod.setVisibility(View.INVISIBLE);

         */

    }

    public void startFight(View v){
        Toast.makeText(getApplicationContext(), "FIGHT START. Nyert: " + Fight(enemy, enkezd) + " Random: " + (GameController.En.RandFactor ^ enemy.RandFactor), Toast.LENGTH_LONG).show();
    }

    public void SortKiir(String str){
        return;
    }

    /*
     * Leszimulál egy kört.
     * Visszaadja, hogy a támadó mekkora sebzést vitt be a védekezőnek
     * */
    public int SimulateKor(Karakter tamado, Karakter vedekezo, Random rand, int kor){
        SortKiir(kor + ". kör, " + tamado.Name + " támad:");

        if (rand.nextDouble() < vedekezo.DO) {
            SortKiir(">>>>>>" + vedekezo.Name + " dodge-olt.");
        } else {
            if (rand.nextDouble() < tamado.CR) {
                SortKiir(">>>> " + tamado.Name + " kritelt");
                return (int) (((100 - vedekezo.DeP) / 100) * ((100 + tamado.DMG) / 100) * 2 * tamado.DMG);
            } else {
                return (int) (((100 - vedekezo.DeP) / 100) * ((100 + tamado.DMG) / 100) * tamado.DMG);
            }
        }
        return 0; 
    }

    // Visszaadja, hogy győztünk-e
    public boolean Fight(Karakter enemy, boolean enkezdek){
        Karakter en = GameController.En;

        // TÖRÖLD KI HA SIKERÜLT QR KÓDDAL MEGNYITNI AZ ACTIVITYT
        boolean debug = true;
        boolean debug_vesztett = true;
        //IDÁIG


        double HPen = en.HP, HPenemy = enemy.HP;
        int kor = 1;


        en.RandFactor = new Random().nextInt();

        // TÖRÖLD KI HA SIKERÜLT QR KÓDDAL MEGNYITNI AZ ACTIVITYT
        if(debug){
            if(debug_vesztett){
                vesztes.setText("Vesztettél!");
            }
            else{
                nyertes.setText("Nyertél!");
            }
        }
        //IDÁIG

        if(enkezdek)
            if((HPenemy -= SimulateKor(en, enemy, rand, kor++)) < 0){
                nyertes.setText("Nyertél!");
                return true;
            }
        while (true){
            if((HPen    -= SimulateKor(enemy, en, rand, kor++)) < 0){
                vesztes.setText("Vesztettél!");
                return false;
            }
            if((HPenemy -= SimulateKor(en, enemy, rand, kor++)) < 0){
                nyertes.setText("Nyertél!");
                return true;
            }
        }

    }
}
