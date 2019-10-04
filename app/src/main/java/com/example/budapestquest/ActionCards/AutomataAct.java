package com.example.budapestquest.ActionCards;

import androidx.appcompat.app.AppCompatActivity;

import com.example.budapestquest.GameController;
import com.example.budapestquest.R;

import android.os.Bundle;
import android.widget.EditText;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class AutomataAct extends AppCompatActivity {

    public static final int vonaljegyar = 50;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_automata);

        ((TextView)findViewById(R.id.jegyar)).setText("Jegy ár: " + vonaljegyar + "/db");
        ((TextView)findViewById(R.id.statom)).setText("Jegyeim: " + GameController.En.vonaljegy + " db\nPénzem: "+ GameController.En.FT + " Ft");
    }

    public void ButtonAutomataBuy(View v){
        try {
            int darab = Integer.parseInt(((EditText) findViewById(R.id.AutomataDB)).getText().toString());
            if(darab <= 0) {
                Toast.makeText(getApplicationContext(), "Minimum 1 darab jegyet kell venned. Ha nem akarsz venni, akkor lépj vissza a vissza gombbal.", Toast.LENGTH_LONG).show();
                return;
            }
            if (GameController.En.SpendMoney(darab * vonaljegyar)) {
                GameController.En.vonaljegy += darab;
                GameController.tabStats.Update();
                finish();
            } else
                Toast.makeText(getApplicationContext(), "Nincs elég pénzed ennyi jegyre.", Toast.LENGTH_LONG).show();
        }catch (Exception e){
            Toast.makeText(getApplicationContext(), "Pozitív egész számot írj be.", Toast.LENGTH_LONG).show();
        }
    }
}
