package com.example.budapestquest;

import android.content.Intent;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;

import com.example.budapestquest.Karakterek.Karakter;
import com.example.budapestquest.barcode.BarcodeCaptureActivity;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
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
    public static final int QR_READER_CODE = 100;
    public TextView QrResultText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        FloatingActionButton fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                QrResultText = findViewById(R.id.result);
                Intent intent = new Intent(getApplicationContext(), BarcodeCaptureActivity.class);
                startActivityForResult(intent, QR_READER_CODE);

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

    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == QR_READER_CODE) {
            if (resultCode == CommonStatusCodes.SUCCESS) {
                if (intent != null) {
                    Barcode barcode = intent.getParcelableExtra(BarcodeCaptureActivity.BarcodeObject);
                    QrResultText.setText(barcode.rawValue);
                }
            }
        } else
            super.onActivityResult(requestCode, resultCode, intent);
    }

}