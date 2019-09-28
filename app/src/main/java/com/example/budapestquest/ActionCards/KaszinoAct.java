package com.example.budapestquest.ActionCards;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.budapestquest.GameController;
import com.example.budapestquest.MainActivity;
import com.example.budapestquest.R;

import java.util.Random;

public class KaszinoAct extends AppCompatActivity {
    private static final int pokerszint = 50;
    public static final double pokerodds = 0.45;

    private static final int ruletszint = 30;
    public static final double ruletodds = 0.25;

    private static final int blackszint = 40;
    public static final double blackodds = 0.35;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kaszino);

        ((TextView)findViewById(R.id.statom)).setText("Pénzem: "+ GameController.En.FT + " Ft");
    }

    //TODO: az algo hibás volt, pénzt vesztettél. Most "javítva van", nem vesztesz pénzt, de a súlyokat stb kérem hogy valaki állítsa be.

    public void Game(int szint, double odds){
        try {
            int osszeg = Integer.parseInt(((EditText) findViewById(R.id.osszeg)).getText().toString());
            if(osszeg <= 0) {
                Toast.makeText(getApplicationContext(), "Minimum 1 forintot fel kell tenned. Ha nem akarsz játszani, akkor lépj vissza a vissza gombbal.", Toast.LENGTH_LONG).show();
                return;
            }
            if(GameController.En.PenztKolt(osszeg) || isNyer(odds)) {
                int nyer = (osszeg * 2);
                GameController.En.FT += nyer;
                Toast.makeText(getApplicationContext(), "Nyertél " + nyer + " FT-t!", Toast.LENGTH_LONG).show();
                GameController.tabStats.Update();
                finish();
            }
            else
                Toast.makeText(getApplicationContext(), "Nincs elég pénzed.", Toast.LENGTH_LONG).show();
        }catch (Exception e){
            Toast.makeText(getApplicationContext(), "Pozitív egész számot írj be.", Toast.LENGTH_LONG).show();
        }
    }

    public void ButtonPoker(View v) {
        Game(pokerszint, pokerodds);
    }

    public void ButtonRulett(View v) {
        Game(ruletszint, ruletodds);
    }

    public void ButtonJack(View v) {
        Game(blackszint, blackodds);
    }

    private static boolean isNyer(double odds) {
        return GameController.rand.nextDouble() < odds;
    }
}
