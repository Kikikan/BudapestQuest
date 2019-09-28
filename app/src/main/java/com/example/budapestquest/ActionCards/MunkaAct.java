package com.example.budapestquest.ActionCards;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.budapestquest.GameController;
import com.example.budapestquest.Karakterek.Karakter;
import com.example.budapestquest.R;

public class MunkaAct extends AppCompatActivity {

    public static final int munkaber = 15;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_munka);

        ((TextView)findViewById(R.id.munkaber)).setText("Bérezés: " + munkaber + "/kör");
        ((TextView)findViewById(R.id.statom)).setText("Pénzem: "+ GameController.En.FT + " Ft");
    }

    public void ButtonMunka(View v) {
        try {
            int darab = Integer.parseInt(((EditText) findViewById(R.id.korEditText)).getText().toString());
            if(darab <= 0) {
                Toast.makeText(getApplicationContext(), "Minimum 1 napott kell dolgoznod. Ha nem akarsz dolgozni, akkor lépj vissza a vissza gombbal.", Toast.LENGTH_LONG).show();
                return;
            }
            GameController.En.kimaradas += darab;
            if (GameController.En.UNI == Karakter.BME_ID)
                GameController.En.kimaradas--;
            GameController.En.FT += munkaber * darab;
            GameController.tabStats.Update();
            finish();
        }catch (Exception e){
            Toast.makeText(getApplicationContext(), "Pozitív egész számot írj be.", Toast.LENGTH_LONG).show();
        }
    }
}
