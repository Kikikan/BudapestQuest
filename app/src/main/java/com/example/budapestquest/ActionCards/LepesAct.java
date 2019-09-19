package com.example.budapestquest.ActionCards;

import androidx.appcompat.app.AppCompatActivity;

import com.example.budapestquest.GameController;
import com.example.budapestquest.R;

import android.os.Bundle;
import android.widget.Toast;

public class LepesAct extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lepes);

        switch (getIntent().getIntExtra("LEPES", 0)){
            case 0:
                Toast.makeText(getApplicationContext(), "Bliccelés.", Toast.LENGTH_LONG).show();
                GameController.En.lepes(false);
                break;
            case 1:
                Toast.makeText(getApplicationContext(), "Lyukasztás.", Toast.LENGTH_LONG).show();
                GameController.En.lepes(true);
                break;
        }
    }
}
