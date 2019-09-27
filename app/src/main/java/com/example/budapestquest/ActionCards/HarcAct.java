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
import com.example.budapestquest.Karakterek.KarakterStats;
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

    private Karakter enemy;
    private boolean enkezd;
    private Random rand;

    private KarakterStats stat_en, stat_enemy;

    private boolean lement = false;

    private GameController gc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_harc);

        gc = GameController.GetInstance();

        log = findViewById(R.id.log);
        qrkod = findViewById(R.id.qrView2);
        btn = findViewById(R.id.start);

        if((enemy = Karakter.Deserialize(getIntent().getStringExtra("ENEMY"))) == null) {
            Toast.makeText(getApplicationContext(), "Hiba a karakter beolvasásnál.", Toast.LENGTH_LONG).show();
            this.finish();
        }
        enkezd = getIntent().getBooleanExtra("ENKEZD", false);
        rand = new Random(gc.En.RandFactor ^ enemy.RandFactor);

        stat_en = gc.En.SumStats();
        stat_enemy = enemy.SumStats();

        if(enkezd) {
            Bitmap bitmap = QRManager.TextToImageEncode(QRManager.QR_HARC2, gc.En.Serialize());
            qrkod.setImageBitmap(bitmap);
        }else
            qrkod.setVisibility(View.INVISIBLE);

    }

    public void startFight(View v){
        if(lement){
            gc.tabStats.Update();
            finish();
        }else {
            if(Fight()) {
                Toast.makeText(getApplicationContext(), "NYERTÉL!", Toast.LENGTH_LONG).show();
                gc.En.FT += 30;
            }else{
                Toast.makeText(getApplicationContext(), "VESZTETTÉL!", Toast.LENGTH_LONG).show();
            }
            btn.setText("Kilépés");
        }
    }

    //TODO: SZÉP LOG

    public void SortKiir(String str){
        log.setText(log.getText() + str + "\n");
    }

    /*
     * Leszimulál egy kört.
     * Visszaadja, hogy a támadó mekkora sebzést vitt be a védekezőnek
     * */
    public int SimulateKor(KarakterStats tamado, KarakterStats vedekezo){
        if (rand.nextDouble() < vedekezo.DO) {
            SortKiir(">>>>>> Dodge.");
        } else {
            if (rand.nextDouble() < tamado.CR) {
                SortKiir(">>>> Kritikus találat!");
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
        gc.En.RandFactor = new Random().nextInt();

        int kor = 1;

        if(enkezd) {
            SortKiir((kor++) + ". kör, " + gc.En.Name + " támad:");
            if ((stat_enemy.HP -= SimulateKor(gc.En, enemy)) <= 0) return true;
        }
        while(true){
            SortKiir((kor++) + ". kör, " + enemy.Name + " támad:");
            if((stat_en.HP -= SimulateKor(enemy, gc.En)) <= 0) return false;
            SortKiir((kor++) + ". kör, " + gc.En.Name + " támad:");
            if((stat_enemy.HP -= SimulateKor(gc.En, enemy)) <= 0) return true;
        }
    }
}
