package com.example.budapestquest.ActionCards;

import androidx.appcompat.app.AppCompatActivity;
import com.example.budapestquest.R;
import com.example.budapestquest.akcioKartyak.Tier1Shop;

import android.os.Bundle;

public class BoltAct extends AppCompatActivity {
Tier1Shop bolt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bolt = new Tier1Shop();
        setContentView(R.layout.activity_bolt);
    }
}
