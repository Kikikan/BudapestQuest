package com.example.budapestquest.ActionCards;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;

import com.example.budapestquest.GameController;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kaszino);
    }

    public int GetOsszeg(){
        return Integer.parseInt(((EditText)findViewById(R.id.osszeg)).getText().toString());
    }

    public void ButtonPoker(View v) {
        int osszeg = GetOsszeg();
        if(isNyer(pokerszint)) {
            GameController.En.FT += (int) (osszeg * pokerodds);
            //Toast.makeText(getApplicationContext(), "Nyert", 2);
        }
        else {
            GameController.En.FT -= osszeg;
            //Toast.makeText(getApplicationContext(), "Vesztett", 2);
        }
    }

    public void ButtonRulett(View v) {
        int osszeg = GetOsszeg();
        if(isNyer(ruletszint)) {
            GameController.En.FT += (int) (osszeg * ruletodds);
            //Toast.makeText(getApplicationContext(), "Nyert", 2);
        }
        else {
            GameController.En.FT -= osszeg;
            //Toast.makeText(getApplicationContext(), "Vesztett", 2);
        }
    }

    public void ButtonJack(View v) {
        int osszeg = GetOsszeg();
        if(isNyer(blackszint)) {
            GameController.En.FT += (int) (osszeg * blackodds);
            //Toast.makeText(getApplicationContext(), "Nyert", 2);
        }
        else {
            GameController.En.FT -= osszeg;
            //Toast.makeText(getApplicationContext(), "Vesztett", 2);
        }
    }

    private static boolean isNyer(int meddig) {
        return rand.nextInt(100+1) > meddig;
    }
}
