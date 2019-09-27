package com.example.budapestquest.ActionCards;

import androidx.appcompat.app.AppCompatActivity;

import com.example.budapestquest.GameController;
import com.example.budapestquest.Karakterek.Karakter;
import com.example.budapestquest.R;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class LepesAct extends AppCompatActivity {

    public static final int bliccbirsag = 60;

    private GameController gc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lepes);

        gc = GameController.GetInstance();

        TextView blicc;
        blicc = findViewById(R.id.bliccsiker);
        String outtext = "";

        switch (getIntent().getIntExtra("LEPES", 0)) {
            case 1: {
                gc.vonaljegy--;
                outtext = "Lyukasztottál, egyel kevesebb jegyed van.";
            }break;
            case 0: {
                if (gc.rand.nextDouble() < (gc.En.UNI == Karakter.ELTE_ID ? 12.5 : 25)) {
                    outtext = "Jött az ellenőr, és megbírságolt " + bliccbirsag + " FT-ra, " + (gc.PenztKolt(bliccbirsag) ? "melyet kifizettél." : "viszont nem volt nálad ennyi, tehát bevágtak böribe.");
                } else {
                    outtext = "Sikerült! Nincs olyan ellenőr aki elbánhatna veled!;)";
                }
            }
            break;
        }
        blicc.setText(outtext);
    }
}
