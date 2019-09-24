package com.example.budapestquest.ActionCards;

import androidx.appcompat.app.AppCompatActivity;

import com.example.budapestquest.GameController;
import com.example.budapestquest.Karakterek.Karakter;
import com.example.budapestquest.MainActivity;
import com.example.budapestquest.R;
import com.example.budapestquest.Targyak.Targy;

import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Toast;

public class BoltAct extends AppCompatActivity {

    int Tier;
    static final int capacity = 4;
    Targy[] targyak = new Targy[capacity];

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bolt);

        Tier = getIntent().getIntExtra("TIER", 0);

        // Itemek generálása
        for (int j = 0; j < capacity; j++) {
            targyak[j] = Targy.Generate(j, Tier);
        }
    }

    public void ButtonVasarlas(View v) {
        RadioGroup slotGroup = findViewById(R.id.unigroup); //TODO: radiogroup
        int slotId = slotGroup.indexOfChild(slotGroup.findViewById(slotGroup.getCheckedRadioButtonId()));

        int actualPrice = (int) (targyak[slotId].item.Price * targyak[slotId].modifier.PriceWeight * (GameController.En.UNI == Karakter.CORVINUS_ID ? 0.8 : 1.0));
        if (GameController.En.PenztKolt(actualPrice)) {
            GameController.En.Felszereles[slotId] = targyak[slotId];
            MainActivity.gameController.Update();
            finish();
        }else
            Toast.makeText(getApplicationContext(), "Nincs elég pénzed erre az itemre.", Toast.LENGTH_LONG).show();


    }
}







