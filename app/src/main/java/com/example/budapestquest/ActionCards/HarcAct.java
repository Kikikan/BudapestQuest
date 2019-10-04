package com.example.budapestquest.ActionCards;

import androidx.appcompat.app.AppCompatActivity;

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
import com.example.budapestquest.QRManager;
import com.example.budapestquest.R;

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
    private String logString = "";

    //TODO: az xml-ben elég fura dolgok vannak még

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_harc);

        log = findViewById(R.id.log);
        qrkod = findViewById(R.id.qrView2);
        btn = findViewById(R.id.start);

        try {
            enemy = new Karakter(getIntent().getStringExtra("ENEMY"));
        }catch (Exception e){
            Toast.makeText(getApplicationContext(), "Hiba a karakter beolvasásnál: " + e.toString(), Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        enkezd = getIntent().getBooleanExtra("ENKEZD", false);
        rand = new Random(GameController.En.RandFactor ^ enemy.RandFactor);

        stat_en = GameController.En.SumStats();
        stat_enemy = enemy.SumStats();

        if(enkezd) {
            Bitmap bitmap = QRManager.TextToImageEncode(QRManager.QR_HARC2, GameController.En.Serialize());
            qrkod.setImageBitmap(bitmap);
        }else
            qrkod.setVisibility(View.GONE);

    }

    // Ne lehessen visszalépni, ezzel megúszva a csatát
    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    public void startFight(View v){
        if(lement){
            GameController.UpdateStats();
            finish();
        }else {
            SortKiir(""+GameController.En.Name + " VS " + enemy.Name + "\nKEZD: " + (enkezd ? GameController.En.Name : enemy.Name) + "\n");
            boolean nyertem = Fight();
            if(nyertem) {
                Toast.makeText(getApplicationContext(), "NYERTÉL!", Toast.LENGTH_LONG).show();
                GameController.En.FT += 30;
                GameController.En.GiveXP(1);
            }else{
                Toast.makeText(getApplicationContext(), "VESZTETTÉL!", Toast.LENGTH_LONG).show();
                GameController.En.kimaradas = 1;
            }
            SortKiir("A nyertes: " + (nyertem ? GameController.En.Name : enemy.Name));
            qrkod.setVisibility(View.GONE);
            log.setText(logString);
            btn.setText("Kilépés");
        }
    }

    //TODO: SZÉP LOG

    public void SortKiir(String str){ logString += str + "\n";
    }

    /*
     * Leszimulál egy kört.
     * Visszaadja, hogy a támadó mekkora sebzést vitt be a védekezőnek.
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

    /*
    *   Visszaadja, hogy győztünk-e.
    * */
    public boolean Fight() {
        lement = true;
        GameController.En.RandFactor = new Random().nextInt();

        int kor = 1;
        int bevitSebzes;

        if(enkezd) {
            SortKiir((kor++) + ". kör, TE támadsz.");

            bevitSebzes = SimulateKor(GameController.En, enemy);
            stat_enemy.HP -= bevitSebzes;

            SortKiir("  " + enemy.Name + "-nak/nek ennyit sebeztél: "+bevitSebzes+ " (HP: "+stat_enemy.HP+")");

            if(stat_enemy.HP <= 0) return true;
        }
        while(true){
            SortKiir((kor++) + ". kör, " + enemy.Name + " támad.");

            bevitSebzes = SimulateKor(enemy, GameController.En);
            stat_en.HP -= bevitSebzes;

            SortKiir("  ennyit sebezett Neked: "+bevitSebzes+ " (HP: "+stat_en.HP+")");

            if(stat_en.HP <= 0) return false;

            // ----

            SortKiir((kor++) + ". kör, TE támadsz.");

            bevitSebzes = SimulateKor(GameController.En, enemy);
            stat_enemy.HP -= bevitSebzes;

            SortKiir("  " + enemy.Name + "-nak/nek ennyit sebeztél: "+bevitSebzes+ " (HP: "+stat_enemy.HP+")");

            if(stat_enemy.HP <= 0) return true;
        }
    }
}
