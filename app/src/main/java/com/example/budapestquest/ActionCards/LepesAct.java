package com.example.budapestquest.ActionCards;

import androidx.appcompat.app.AppCompatActivity;

import com.example.budapestquest.GameController;
import com.example.budapestquest.Karakterek.Karakter;
import com.example.budapestquest.R;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class LepesAct extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lepes);

        TextView blicc;
        blicc = findViewById(R.id.bliccsiker);
        switch (getIntent().getIntExtra("LEPES", 0)){
            case 0:
                Toast.makeText(getApplicationContext(), "Bliccelés.", Toast.LENGTH_LONG).show();
                if(GameController.En.lepes(false)) {
                    if (GameController.En.elkaptakBlicceles()) {
                        //SIKERES BLITZ
                        blicc.setText("Sikerült! Nincs olyan ellenőr aki elbánhatna veled!;)");
                    } else {
                        //SIKERTELEN BLITZ
                        blicc.setText("Megpróbáltál elmenekülni, de a rendőrök elkaptak! ");
                    }
                }
                break;
            case 1:
                Toast.makeText(getApplicationContext(), "Lyukasztás.", Toast.LENGTH_LONG).show();
                GameController.En.lepes(true);
                break;
        }
    }
}
