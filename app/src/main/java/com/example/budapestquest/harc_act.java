package com.example.budapestquest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.budapestquest.GameController;
import com.example.budapestquest.Karakterek.Karakter;
import com.example.budapestquest.R;

import java.util.Random;

public class harc_act extends AppCompatActivity {

    TextView nyertes = (TextView)findViewById(R.id.nyertesIndikator);
    TextView vesztes = (TextView)findViewById(R.id.vesztesIndikator);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_harc_act);
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

        boolean debug = true;
        boolean debug_vesztett = true;


        double HPen = en.HP, HPenemy = enemy.HP;
        int kor = 1;

        Random rand = new Random(en.RandFactor ^ enemy.RandFactor);
        en.RandFactor = new Random().nextInt();

        if(debug){
            if(debug_vesztett){
                vesztes.setText("Vesztettél!");
            }
            else{
                nyertes.setText("Nyertél!");
            }
        }

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