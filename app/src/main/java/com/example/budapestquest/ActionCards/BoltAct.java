package com.example.budapestquest.ActionCards;

import androidx.appcompat.app.AppCompatActivity;

import com.example.budapestquest.GameController;
import com.example.budapestquest.R;
import com.example.budapestquest.Targyak.Targy;

import android.os.Bundle;

public class BoltAct extends AppCompatActivity {

    int Tier;
    static final int capacity = 4;
    Targy[] targyak = new Targy[capacity];

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bolt);

        Tier = getIntent().getIntExtra("TIER", 0);
        for (int j = 0; j < capacity; j++) {
            targyak[j] = Targy.Generate(j, 1);
        }
    }

    public void vasarlas(int index) {
        double actualPrice = targyak[index].item.Price * targyak[index].modifier.PriceWeight;
        if (GameController.En.FT >= actualPrice) {
            GameController.En.FT -= actualPrice;
            GameController.En.Felszereles[index] = targyak[index];
        }


    }
}







