package com.example.budapestquest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import java.util.Random;

public class kaszino_act extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kaszino_act);
    }

    private static final int pokerszint = 50;
    public static final double pokerodds = 0.45;

    private static final int ruletszint = 30;
    public static final double ruletodds = 0.25;

    private static final int blackszint = 40;
    public static final double blackodds = 0.35;

    public static final Random rand = new Random();

    public static void poker(int osszeg) {
        if(isNyer(pokerszint)) {
            GameController.En.FT += (int) (osszeg * pokerodds);
            //Toast.makeText(getApplicationContext(), "Nyert", 2);
        }
        else {
            GameController.En.FT -= osszeg;
            //Toast.makeText(getApplicationContext(), "Vesztett", 2);
        }
    }

    public static void rulett(int osszeg) {
        if(isNyer(ruletszint)) {
            GameController.En.FT += (int) (osszeg * ruletodds);
            //Toast.makeText(getApplicationContext(), "Nyert", 2);
        }
        else {
            GameController.En.FT -= osszeg;
            //Toast.makeText(getApplicationContext(), "Vesztett", 2);
        }
    }

    public static void blackJack(int osszeg) {
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
