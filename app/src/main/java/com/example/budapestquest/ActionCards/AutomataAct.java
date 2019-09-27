package com.example.budapestquest.ActionCards;

import androidx.appcompat.app.AppCompatActivity;

import com.example.budapestquest.GameController;
import com.example.budapestquest.MainActivity;
import com.example.budapestquest.R;

import android.os.Bundle;
import android.text.Editable;
import android.widget.EditText;
import android.view.View;
import android.widget.Toast;

public class AutomataAct extends AppCompatActivity {

    public static final int vonaljegyar = 50;
    private GameController gc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_automata);

        gc = GameController.GetInstance();
    }

    public int GetDB(){
        return Integer.parseInt(((EditText)findViewById(R.id.AutomataDB)).getText().toString());
    }

    public void ButtonAutomataBuy (View v) {
        int darab = GetDB(), osszeg = darab * vonaljegyar;
        if(gc.PenztKolt(osszeg)) {
            gc.vonaljegy += darab;
            gc.tabStats.Update();
            finish();
        }else
            Toast.makeText(getApplicationContext(), "Nincs elég pénzed ennyi jegyre.", Toast.LENGTH_LONG).show();
    }
}
