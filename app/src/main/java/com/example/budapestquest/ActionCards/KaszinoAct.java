package com.example.budapestquest.ActionCards;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;
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

    public static final Random rand = new Random();

    private GameController gc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kaszino);

        gc = GameController.GetInstance();
    }

    public int GetOsszeg(){
        return Integer.parseInt(((EditText)findViewById(R.id.osszeg)).getText().toString());
    }

    public void Game(int szint, double odds){
        int osszeg = GetOsszeg();
        if(gc.PenztKolt(osszeg) || isNyer(szint)) {
            int nyer = (int) (osszeg * odds);
            gc.En.FT += nyer;
            Toast.makeText(getApplicationContext(), "Nyertél " + nyer + " FT-t!", Toast.LENGTH_LONG).show();
            gc.tabStats.Update();
            finish();
        }
        else
            Toast.makeText(getApplicationContext(), "Nincs elég pénzed.", Toast.LENGTH_LONG).show();
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

    private static boolean isNyer(int meddig) {
        return rand.nextInt(100+1) > meddig;
    }
}
