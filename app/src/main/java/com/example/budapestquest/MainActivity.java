package com.example.budapestquest;

import android.content.Intent;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;

import com.example.budapestquest.Targyak.Targy;
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
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.budapestquest.ui.main.SectionsPagerAdapter;

public class MainActivity extends AppCompatActivity {
    public static GameController gameController = new GameController();

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

        // Ha "ébredünk", töltsük be magunkat a cacheből, már ha ott vagyunk
        if(savedInstanceState != null){
            gameController.En = GameController.Load(getApplicationContext().getCacheDir(), "player.dat");
        }
    }

    // Ha az Android megölné az applikációnkat erőforráshiány miatt, itt definiálhatjuk, mit mentsen el
    //TODO: Ezt ellenőrizni, és kicsit kicsinosítani
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        gameController.Save(getApplicationContext().getCacheDir(), "player.dat");
    }

    //TODO: Csak akkor lehessen továbbmenni a többi panelre, ha ez sikerrel járt.
    public void CreateButton(View v) {
        try {
            String name = ((EditText) findViewById(R.id.nevEditText)).getText().toString();

            RadioGroup kasztGroup = findViewById(R.id.kasztgroup);
            int kasztId = kasztGroup.indexOfChild(kasztGroup.findViewById(kasztGroup.getCheckedRadioButtonId()));

            RadioGroup uniGroup = findViewById(R.id.unigroup);
            int uniId = uniGroup.indexOfChild(uniGroup.findViewById(uniGroup.getCheckedRadioButtonId()));

            gameController.CreateChar(name, kasztId, uniId);
        }catch(Exception e){
            Toast.makeText(getApplicationContext(), "Hiba: " + e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    // Ideiglenes teszt gomb az Item generálásra
    public void RandItemGen(View v){
        Targy t = Targy.Generate(Targy.FEJ_ID, Targy.TIER_1);
        Toast.makeText(getApplicationContext(), "Tárgy: " + t.modifier.Name + " " + t.item.Name + " (Tier: "+(t.Tier+1)+")", Toast.LENGTH_SHORT).show();
    }

    // QR Kód feldolgozás csak jelenleg
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == QR_READER_CODE) {
            if (resultCode == CommonStatusCodes.SUCCESS) {
                if (intent != null) {
                    try {
                        Barcode barcode = intent.getParcelableExtra(BarcodeCaptureActivity.BarcodeObject);
                        if (barcode.rawValue.length() < 6)
                            throw new Exception("Hibás QR formátum.");

                        // Parse
                        String version = barcode.rawValue.substring(0,4);
                        char method = barcode.rawValue.charAt(4);
                        String data = barcode.rawValue.substring(5);

                        // GameControllerben feldolgozzuk
                        gameController.HandleQR(method, version, data, getApplicationContext());
                    }catch(Exception e){
                        Toast.makeText(getApplicationContext(), "Hiba: " + e.toString(), Toast.LENGTH_LONG).show();
                    }
                }
            }
        } else
            super.onActivityResult(requestCode, resultCode, intent);
    }

}