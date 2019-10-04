package com.example.budapestquest.ActionCards;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.budapestquest.GameController;
import com.example.budapestquest.R;

public class KondiAct extends AppCompatActivity {
    private static final int belepo_harci = 160;
    private static final int belepo_kardio = 160;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kondi);

        ((TextView)findViewById(R.id.statom)).setText("Pénzem: "+ GameController.En.FT + " Ft");

        // Ha nem tudunk edzeni, kapcsoljuk ki a gombokat
        Button HarciButton = findViewById(R.id.HarciButton);
        HarciButton.setText("Hardi edzés ( Belépő: " + belepo_harci + " Ft )");
        if(GameController.En.FT < belepo_harci) {
            HarciButton.getBackground().setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);
            HarciButton.setClickable(false);
        }

        Button KardioButton = findViewById(R.id.KardioButton);
        KardioButton.setText("Kardio edzés ( Belépő: " + belepo_kardio + " Ft )");
        if(GameController.En.FT < belepo_kardio) {
            KardioButton.getBackground().setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);
            KardioButton.setClickable(false);
        }
    }

    public void ButtonHarci(View v){
        if(GameController.En.SpendMoney(belepo_harci)) {
            GameController.En.DMG += 2 * 10;
            GameController.En.DaP += 2;
            GameController.En.CR += 2;
            GameController.UpdateStats();
            finish();
        }else
            Toast.makeText(getApplicationContext(), "Nincs elég pénzed harci edzésre.", Toast.LENGTH_LONG).show();
    }

    public void ButtonKardio(View v){
        if(GameController.En.SpendMoney(belepo_kardio)) {
            GameController.En.HP += 2 * 100;
            GameController.En.DeP += 2;
            GameController.En.DO += 2;
            GameController.UpdateStats();
            finish();
        }else
            Toast.makeText(getApplicationContext(), "Nincs elég pénzed kardio edzésre.", Toast.LENGTH_LONG).show();
    }
}
