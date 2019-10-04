package com.example.budapestquest.ActionCards;

import androidx.appcompat.app.AppCompatActivity;

import com.example.budapestquest.GameController;
import com.example.budapestquest.R;
import com.example.budapestquest.TabInventory;
import com.example.budapestquest.Targyak.Targy;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class AkcioAct extends AppCompatActivity {

    public static final int penzPluszMin = 15, penzPluszMax = 30;
    public static final String[] penzPlusz = new String[]{
            "A földön találtál egy pénztárcát, melyben fura módon iratok nem, csak egy köteg pénz lapult, melyet gyorsan zsebre is vágtál:\n"
    };

    public static final int penzMinuszMin = 5, penzMinuszMax = 20;
    public static final String[] penzMinusz = new String[]{
            "Betévedtél a város egyik hírhedten rossz nyegyedébe, és kiraboltak. Ennyit vittek el tőled:\n"
    };

    public static final String[] penzMinusz0 = new String[]{
            "Beléd kötött a família a Blahán. Sajnos nem volt nálad pénz, náluk meg terminál, így a testi épségeddel fizettél. Menj a kórházba.\n"
    };

    public static final String[] itemPlusz = new String[]{
            "Egy aluljáróban bóklászva hirtelen valami csillogásra lettél figyelmes a földön. Közelebb mentél, és döbbenve láttad, hogy találtál egy: ",
            "Az utcán sétálva egyszer csak egy katonai konvoj süvített el, és az egyik teherautó platójáról valami pont a lábad elé esett: "
    };

    private Targy targy;
    private boolean itemtalalat = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_akcio);

        TextView msg = findViewById(R.id.mitortent);
        Button RendbenButton = findViewById(R.id.RendbenButton);
        Button MegsemButton = findViewById(R.id.MegsemButton);

        // Esélyek kiszámítása
        int o1 = getIntent().getIntExtra("ODDS_PENZ+", 0);
        int o2 = o1 + getIntent().getIntExtra("ODDS_PENZ-", 0);
        int o3 = o2 + getIntent().getIntExtra("ODDS_TARGY+", 0);
        int rnd = GameController.rand.nextInt(o3);

        if(rnd < o1) {
            int nyert = GameController.rand.nextInt(penzPluszMax - penzPluszMin) + penzPluszMin;
            msg.setText(penzPlusz[GameController.rand.nextInt(penzPlusz.length)] + nyert + " Ft");
            GameController.En.FT += nyert;
        }else if(rnd < o2) {
            if (GameController.En.FT > 0) {
                int veszit = Math.min(GameController.rand.nextInt(penzMinuszMax - penzMinuszMin) + penzMinuszMin, GameController.En.FT);
                msg.setText(penzMinusz[GameController.rand.nextInt(penzMinusz.length)] + veszit + " Ft");
                GameController.En.FT -= veszit;
            }
            else
            {
                msg.setText(penzMinusz0[GameController.rand.nextInt(penzMinusz0.length)]);
            }

        }else {
            itemtalalat = true;
            msg.setText(itemPlusz[GameController.rand.nextInt(itemPlusz.length)]);
            targy = Targy.Generate(GameController.rand.nextInt(4), GameController.rand.nextInt(3));

            // Ugly hack, TODO: szebbre
            RendbenButton.setText("Lecserélem");

            View newitem = findViewById(R.id.newitem);
            newitem.setVisibility(View.VISIBLE);
            TabInventory.UpdateInventoryRow(newitem, targy);
            findViewById(R.id.regiitem).setVisibility(View.VISIBLE);

            View olditem = findViewById(R.id.olditem);
            olditem.setVisibility(View.VISIBLE);
            TabInventory.UpdateInventoryRow(olditem, GameController.En.Felszereles[targy.Slot]);

            MegsemButton.setVisibility(View.VISIBLE);
        }
    }

    public void ButtonRendben(View v){
        if(itemtalalat)
            GameController.En.Felszereles[targy.Slot] = targy;
        GameController.UpdateStats();
        finish();
    }

    public void ButtonMegsem(View v){
        GameController.tabStats.Update();
        finish();
    }
}
