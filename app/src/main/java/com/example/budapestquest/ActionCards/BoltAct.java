package com.example.budapestquest.ActionCards;

import androidx.appcompat.app.AppCompatActivity;
import com.example.budapestquest.R;

import android.os.Bundle;

public class BoltAct extends AppCompatActivity {

    int Tier;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bolt);

        Tier = getIntent().getIntExtra("TIER", 0);

    }
}
