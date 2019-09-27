package com.example.budapestquest.ActionCards;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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
    }

    public int getDB() {
        return Integer.parseInt(((EditText)findViewById(R.id.korEditText)).getText().toString());
    }

    public void ButtonMunka(View v) {
        GameController gc = GameController.GetInstance();

        int db = getDB();

        gc.kimaradas += db;
        if (gc.En.UNI == Karakter.BME_ID)
            gc.kimaradas--;

        gc.En.FT += munkaber * db;
    }
}
