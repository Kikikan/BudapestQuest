package com.example.budapestquest.ActionCards;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.budapestquest.GameController;
import com.example.budapestquest.R;

public class MunkaAct extends AppCompatActivity {

    EditText kor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_munka);
        kor = findViewById(R.id.korEditText);
        Button button = findViewById(R.id.munkaButton);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                ButtonMunka();
            }
        });

    }

    public int getDB(){
        return Integer.parseInt(kor.getText().toString());
    }

    public void ButtonMunka()
    {
        GameController.En.munka(getDB());
    }
}
