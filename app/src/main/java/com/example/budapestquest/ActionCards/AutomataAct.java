package com.example.budapestquest.ActionCards;

import androidx.appcompat.app.AppCompatActivity;

import com.example.budapestquest.GameController;
import com.example.budapestquest.MainActivity;
import com.example.budapestquest.R;

import android.os.Bundle;
import android.text.Editable;
import android.widget.EditText;

public class AutomataAct extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_automata);
    }

    public int getDB(){
        return Integer.parseInt(((EditText)findViewById(R.id.AutomataDB)).getText().toString());
    }

    public void ButtonAutomataBuy () {
        GameController.En.jegyvasarlas(getDB());
        MainActivity.gameController.Update();
        this.finish();
    }
}
