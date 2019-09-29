package com.example.budapestquest;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.budapestquest.barcode.BarcodeCaptureActivity;
import com.example.budapestquest.ui.main.SectionsPagerAdapter;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.material.tabs.TabLayout;

import java.io.BufferedReader;

public class MainActivity extends AppCompatActivity {
    public static final int INTENT_QR_READER_CODE = 100;
    public static final int INTENT_CREATE_CHAR = 101;

    public static ViewPager viewPager;
    public static TabLayout tabLayout;

    private static SectionsPagerAdapter sectionsPagerAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = findViewById(R.id.view_pager);
        tabLayout = findViewById(R.id.tabs);

        // Legyen menünk
        setSupportActionBar((Toolbar) findViewById(R.id.main_toolbar));
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        // Odaadjuk neki az appcontextet
        // TODO: kell/szebb megoldás?
        GameController.context = getApplicationContext();

        // Készíttessünk karaktert (vagy töltsük be)
        //TODO: Talán kéne egy külön activity ami indul és kiválasztja, hogy karaktert készítünk vagy betöltünk
        try {
            if (GameController.LoadGame()) {
                Toast.makeText(this, "Előző játékállás betöltve.", Toast.LENGTH_LONG).show();
                initTabs();
            } else {
                CallCreateKarakter();
            }
        }catch (Exception e) {
            Toast.makeText(this, "Hiba: " + e.toString(), Toast.LENGTH_LONG).show();
        }
    }

    /*
    *   Elindítja a karakterkészítési folyamatot.
    * */
    public void CallCreateKarakter(){
        Intent intent = new Intent(getApplicationContext(), CreateCharAct.class);
        startActivityForResult(intent, INTENT_CREATE_CHAR);
    }

    public void onQrRead(View v){
        Intent intent = new Intent(getApplicationContext(), BarcodeCaptureActivity.class);
        startActivityForResult(intent, INTENT_QR_READER_CODE);
    }

    /*
    *   Felállítja a tabokat ha még nincsenek, frissíti a statokat ha már vannak.
    * */
    protected void initTabs(){
        if(sectionsPagerAdapter == null) {
            sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
            viewPager.setAdapter(sectionsPagerAdapter);
            tabLayout.setupWithViewPager(viewPager);
        }else{
            GameController.tabStats.UpdateMeta();
            GameController.UpdateStats();
        }
    }

    /*
    *   Menü init + handle.
    * */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.action_delete:{
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Új karakter");
                builder.setMessage("Ezzel el fog veszni a jelenlegi állásod. Biztos vagy benne, hogy új karaktert készítesz?");
                builder.setCancelable(false);
                builder.setPositiveButton("Igen", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        CallCreateKarakter();
                    }
                });
                builder.setNegativeButton("Nem", null);
                builder.show();
                return true;
            }
            case R.id.action_save: {
                if (GameController.SaveGame())
                    Toast.makeText(this, "Játékállás elmentve.", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(this, "Hiba mentés közben.", Toast.LENGTH_LONG).show();
                return true;
            }
            case R.id.action_about: {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("About");
                builder.setMessage("© 5vös gang 2019");
                builder.setCancelable(false);
                builder.setPositiveButton("Ok", null);
                builder.show();
                return true;
            }
            case R.id.action_exit: {
                GameController.SaveGame();
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    /*
     *   Fusson tovább a háttérben. TODO: Mentés miatt ezt talán kivehetjük?
     * */
    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    /*
    *   Automata mentés. (a onRestoreInstanceState-t felesleges overrideolni, onCreate-ben úgyis megpróbáljuk betölteni a játékállást.)
    * */
    @Override
    public void onSaveInstanceState(Bundle bundle){
        GameController.SaveGame();
        super.onSaveInstanceState(bundle);
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
                    GameController.HandleQR(method, version, data);
                }catch (Exception e){
                    Toast.makeText(this, "Hiba: " + e.toString(), Toast.LENGTH_LONG).show();
                }
            }break;
            case INTENT_CREATE_CHAR:{
                if (resultCode != CommonStatusCodes.SUCCESS) break;
                try{
                    initTabs();
                }catch (Exception e){
                    Toast.makeText(this, "Hiba a karakter készítésnél: " + e.toString(), Toast.LENGTH_LONG).show();
                }
            }break;
            default: super.onActivityResult(requestCode, resultCode, intent); break;
        }
    }

}