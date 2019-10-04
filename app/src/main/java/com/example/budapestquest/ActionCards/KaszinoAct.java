package com.example.budapestquest.ActionCards;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.budapestquest.GameController;
import com.example.budapestquest.R;

public class KaszinoAct extends AppCompatActivity {
    private static final int pokerszint = 30;
    public static final double pokerodds = 0.45;

    private static final int ruletszint = 50;
    public static final double ruletodds = 0.25;

    private static final int blackszint = 40;
    public static final double blackodds = 0.35;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kaszino);

        ((TextView)findViewById(R.id.pokerodds)).setText("Esély a nyerésre: "+ pokerodds*100 + " %");
        ((TextView)findViewById(R.id.rulettodds)).setText("Esély a nyerésre: "+ ruletodds*100 + " %");
        ((TextView)findViewById(R.id.blackjackodds)).setText("Esély a nyerésre: "+ blackodds*100 + " %");
        ((TextView)findViewById(R.id.statom)).setText("Pénzem: "+ GameController.En.FT + " Ft");
    }

    //TODO: az algo hibás volt, pénzt vesztettél. Most "javítva van", nem vesztesz pénzt, de a súlyokat stb kérem hogy valaki állítsa be.

    public void Game(double szint, double odds){
        try {
            int osszeg = Integer.parseInt(((EditText) findViewById(R.id.osszeg)).getText().toString());
            if(osszeg <= 0) {
                Toast.makeText(getApplicationContext(), "Minimum 1 forintot fel kell tenned. Ha nem akarsz játszani, akkor lépj vissza a vissza gombbal.", Toast.LENGTH_LONG).show();
                return;
            }
            if(GameController.En.SpendMoney(osszeg)) {
                if(GameController.rand.nextDouble() < odds) {
                    int nyer = (int) Math.round((osszeg * (1.0+((double)szint /100))));
                    GameController.En.FT += nyer;
                    Toast.makeText(getApplicationContext(), "Nyertél " + nyer + " Ft-t!", Toast.LENGTH_LONG).show();
                }else
                    Toast.makeText(getApplicationContext(), "Vesztettél!", Toast.LENGTH_LONG).show();
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
}
