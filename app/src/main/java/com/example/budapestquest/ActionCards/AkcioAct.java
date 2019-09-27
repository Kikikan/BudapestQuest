package com.example.budapestquest.ActionCards;

import androidx.appcompat.app.AppCompatActivity;

import com.example.budapestquest.GameController;
import com.example.budapestquest.R;
import com.example.budapestquest.akcioKartyak.HuzottKartyak;

import android.os.Bundle;
import android.widget.Toast;

public class AkcioAct extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_akcio);

        GameController gc = GameController.GetInstance();

        switch (getIntent().getIntExtra("AKCIO", 0)){
            case 0:
                Toast.makeText(getApplicationContext(), "Pénz+", Toast.LENGTH_LONG).show();
                int nyert = HuzottKartyak.talalVagyVeszitPenzt();
                gc.En.FT += nyert;
                break;
            case 1:
                Toast.makeText(getApplicationContext(), "Pénz-", Toast.LENGTH_LONG).show();
                int veszit = HuzottKartyak.talalVagyVeszitPenzt();
                if((gc.En.FT -= veszit) < 0)
                    gc.En.FT = 0;
                break;
            case 2: //KELL EZ? MERT SOKAT KELLENE VELE KÓDOLNI
                Toast.makeText(getApplicationContext(), "Targy+", Toast.LENGTH_LONG).show();
                break;
            case 3:
                Toast.makeText(getApplicationContext(), "Arena Bajnok", Toast.LENGTH_LONG).show();
                //gc.En.arenaBajnok();
                break;
        }
    }
}
