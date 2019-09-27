package com.example.budapestquest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.example.budapestquest.Targyak.Targy;
import com.example.budapestquest.barcode.BarcodeCaptureActivity;
import com.example.budapestquest.ui.main.SectionsPagerAdapter;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {
    public static final int INTENT_QR_READER_CODE = 100;
    public static final int INTENT_CREATE_CHAR = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Készíttessünk karaktert.
        Intent intent = new Intent(getApplicationContext(), CreateCharAct.class);
        startActivityForResult(intent, INTENT_CREATE_CHAR);
    }

    public void onQrRead(View v){
        Intent intent = new Intent(getApplicationContext(), BarcodeCaptureActivity.class);
        startActivityForResult(intent, INTENT_QR_READER_CODE);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        switch (requestCode){
            case INTENT_QR_READER_CODE:{
                if (resultCode != CommonStatusCodes.SUCCESS || intent == null) break;
                try{
                    Barcode barcode = intent.getParcelableExtra(BarcodeCaptureActivity.BarcodeObject);
                    if ((barcode.rawValue.length() < 13) || !barcode.rawValue.substring(0,8).equals(QRManager.QRCONST))
                        throw new Exception("Hibás QR formátum.");

                    // Parse
                    String version = barcode.rawValue.substring(8,12);
                    char method = barcode.rawValue.charAt(12);
                    String data = barcode.rawValue.substring(13);

                    // GameControllerben feldolgozzuk
                    GameController.GetInstance().HandleQR(method, version, data);
                }catch (Exception e){
                    Toast.makeText(getApplicationContext(), "Hiba: " + e.toString(), Toast.LENGTH_LONG).show();
                }
            }break;
            case INTENT_CREATE_CHAR:{
                if (resultCode != CommonStatusCodes.SUCCESS) break;
                try{
                    // Tabok, itt állítjuk őket föl, mivel innentől van karakterünk.
                    SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
                    ViewPager viewPager = findViewById(R.id.view_pager);
                    viewPager.setAdapter(sectionsPagerAdapter);

                    TabLayout tabs = findViewById(R.id.tabs);
                    tabs.setupWithViewPager(viewPager);

                    // Odaadjuk neki az appcontextet
                    // TODO: kell/szebb megoldás?
                    GameController.GetInstance().context = getApplicationContext();
                }catch (Exception e){
                    Toast.makeText(getApplicationContext(), "Hiba a karakter készítésnél: " + e.toString(), Toast.LENGTH_LONG).show();
                }
            }break;
            default: super.onActivityResult(requestCode, resultCode, intent); break;
        }
    }

}