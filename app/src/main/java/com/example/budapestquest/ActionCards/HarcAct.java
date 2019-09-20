package com.example.budapestquest.ActionCards;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View;

import com.example.budapestquest.GameController;
import com.example.budapestquest.Karakterek.Karakter;
import com.example.budapestquest.Karakterek.Stats;
import com.example.budapestquest.MainActivity;
import com.example.budapestquest.QRManager;
import com.example.budapestquest.R;
import com.example.budapestquest.Targyak.Targy;
import com.google.zxing.WriterException;

import java.util.Random;

public class HarcAct extends AppCompatActivity {

    private TextView log;
    private ImageView qrkod;
    private Button btn;

    Karakter enemy;
    boolean enkezd;
    Random rand;

    Stats stat_en, stat_enemy;

    boolean lement = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_harc);

        log = findViewById(R.id.log);
        qrkod = findViewById(R.id.qrView2);
        btn = findViewById(R.id.start);

        if((enemy = Karakter.Deserialize(getIntent().getStringExtra("ENEMY"))) == null) {
            Toast.makeText(getApplicationContext(), "Hiba a karakter beolvasásnál.", Toast.LENGTH_LONG).show();
            this.finish();
        }
        enkezd = getIntent().getBooleanExtra("ENKEZD", false);
        rand = new Random(GameController.En.RandFactor ^ enemy.RandFactor);

        stat_en = GameController.En.SumStats();
        stat_enemy = enemy.SumStats();

        Bitmap bitmap = QRManager.TextToImageEncode(QRManager.QR_HARC2, GameController.En.Serialize());
        qrkod.setImageBitmap(bitmap);

        /*if(enkezd) {
            Bitmap bitmap = QRManager.TextToImageEncode(QRManager.QR_HARC2, GameController.En.Serialize());
            qrkod.setImageBitmap(bitmap);
        }else
            qrkod.setVisibility(View.INVISIBLE);

         */

    }

    public void startFight(View v){
        if(lement){
            MainActivity.gameController.Update();
            finish();
        }
        Fight();
        btn.setText("Kilépés");
    }

    public void SortKiir(String str){
        log.setText(log.getText() + str + "\n");
    }

    /*
     * Leszimulál egy kört.
     * Visszaadja, hogy a támadó mekkora sebzést vitt be a védekezőnek
     * */
    public int SimulateKor(Stats tamado, Stats vedekezo, int kor){
        SortKiir(kor + ". kör, " + tamado.Name + " támad:");

        if (rand.nextDouble() < vedekezo.DO) {
            SortKiir(">>>>>>" + vedekezo.Name + " dodge-olt.");
        } else {
            if (rand.nextDouble() < tamado.CR) {
                SortKiir(">>>> " + tamado.Name + " kritelt");
                return (int) (((100 - vedekezo.DeP) / 100) * ((100 + tamado.DaP) / 100) * 2 * tamado.DMG);
            } else {
                return (int) (((100 - vedekezo.DeP) / 100) * ((100 + tamado.DaP) / 100) * tamado.DMG);
            }
        }
        return 0; 
    }

    // Visszaadja, hogy győztünk-e
    public boolean Fight() {
        lement = true;

        Stats stat_en = GameController.En.SumStats(), stat_enemy = enemy.SumStats();
        int kor = 1;

        GameController.En.RandFactor = new Random().nextInt();

        if(enkezd)
            if((stat_enemy.HP -= SimulateKor()) <= 0) return true;
        while(true){
            if((stat_en.HP -= SimulateKor()) <= 0) return false;
            if((stat_enemy.HP -= SimulateKor()) <= 0) return true;
        }
    }
}
