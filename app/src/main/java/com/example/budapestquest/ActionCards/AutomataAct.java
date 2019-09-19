package com.example.budapestquest.ActionCards;

import androidx.appcompat.app.AppCompatActivity;

import com.example.budapestquest.GameController;
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
        //TODO azt a edittextből kikérni az adatot ami benne van
        EditText edit = null;//(EditText) findViewById(R.id.);
        Editable editable = edit.getText();
        String allTheText = editable.toString();
        return Integer.parseInt(allTheText);
    }

    public void ButtonAutomataBuy ()
    {
        GameController.En.jegyvasarlas(getDB());
    }
}
