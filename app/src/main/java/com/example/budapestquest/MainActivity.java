package com.example.budapestquest;

import android.os.Bundle;

import com.example.budapestquest.Karakterek.Karakter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.budapestquest.ui.main.SectionsPagerAdapter;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    public static GameController gameController = new GameController();
    RadioGroup radioGroup;
    public RadioButton radioButton;
    Karakter selKar = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        radioGroup = findViewById(R.id.kasztgroup);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        FloatingActionButton fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    public void CreateButton(View v) {
        EditText nameText = findViewById(R.id.nevEditText);
        String name = nameText.getText().toString();
        radioGroup = findViewById(R.id.kasztgroup);
        int radioId = radioGroup.getCheckedRadioButtonId();
        gameController.CreateChar(name, radioId);
    }
}