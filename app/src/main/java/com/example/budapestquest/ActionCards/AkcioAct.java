package com.example.budapestquest.ActionCards;

import androidx.appcompat.app.AppCompatActivity;
import com.example.budapestquest.R;

import android.os.Bundle;
import android.widget.Toast;

public class AkcioAct extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_akcio);

        switch (getIntent().getIntExtra("AKCIO", 0)){
            case 0:
                Toast.makeText(getApplicationContext(), "Pénz+", Toast.LENGTH_LONG).show();
                break;
            case 1:
                Toast.makeText(getApplicationContext(), "Pénz-", Toast.LENGTH_LONG).show();
                break;
            case 2:
                Toast.makeText(getApplicationContext(), "Targy+", Toast.LENGTH_LONG).show();
                break;
        }
    }
}
