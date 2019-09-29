package com.example.budapestquest.ActionCards;

import androidx.appcompat.app.AppCompatActivity;

import com.example.budapestquest.GameController;
import com.example.budapestquest.Karakterek.Karakter;
import com.example.budapestquest.R;
import com.example.budapestquest.TabInventory;
import com.example.budapestquest.Targyak.Targy;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

public class BoltAct extends AppCompatActivity {

    static final int capacity = 4;

    private int Tier;
    private int Valasztott = -1;

    private Targy[] targyak = new Targy[capacity];
    private int[] arak = new int[capacity];

    private TextView lecserel;
    private View regiitem;

    protected TextView MakeStat(String s, double v){
        TextView stat = new TextView(this);
        stat.setText(s + (v > 0 ? "+" : "")+v);
        stat.setTextColor(v > 0 ? Color.GREEN : Color.RED);
        return stat;
    }

    //TODO: kijelezni, hogy most milyen itemjeink vannak + statjaink

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bolt);

        ((TextView)findViewById(R.id.statom)).setText("Pénzem: "+ GameController.En.FT + " Ft");

        lecserel = findViewById(R.id.lecserel);
        regiitem = findViewById(R.id.regiitem);

        Tier = getIntent().getIntExtra("TIER", 0);
        ((TextView) findViewById(R.id.boltTierText)).setText("Tier " + (Tier + 1) + " Bolt");

        // Itemek generálása
        TableLayout tl = findViewById(R.id.boltTable);

        for (int i = 0; i < capacity; i++) {
            Targy t = Targy.Generate(i, Tier);
            int ar = (int) (t.item.Price * t.modifier.PriceWeight * (GameController.En.UNI == Karakter.CORVINUS_ID ? 0.8 : 1.0));

            targyak[i] = t;
            arak[i] = ar;

            View itemrow = LayoutInflater.from(this).inflate(R.layout.activity_bolt_item, null, false);

            TextView itemname = itemrow.findViewById(R.id.itemname);
            itemname.setText(t.modifier.Name + " " + t.item.Name);

            // Modifier adja a színét
            itemname.setTextColor(t.modifier.Color);

            ((TextView) itemrow.findViewById(R.id.itemdesc)).setText(t.item.Desc);
            ((TextView) itemrow.findViewById(R.id.itemar)).setText(ar + " Ft");

            // Statok kiírása
            LinearLayout statok = itemrow.findViewById(R.id.itemstatok);
            double k;
            if ((k = t.SumHP()) != 0)
                statok.addView(MakeStat("HP: ", k));
            if ((k = t.SumDMG()) != 0)
                statok.addView(MakeStat("DMG: ", k));
            if ((k = t.SumDaP()) != 0)
                statok.addView(MakeStat("DaP: ", k));
            if ((k = t.SumDeP()) != 0)
                statok.addView(MakeStat("DeP: ", k));

            itemrow.setTag(i);
            tl.addView(itemrow);
        }
    }

    //TODO: szebb select
    private View elozo = null;
    public void ButtonSelect(View v){
        if(elozo != null) {
            elozo.setBackgroundColor(0x00000000);
        }
        if(Valasztott != -1 && elozo == v) {
            Valasztott = -1;
            lecserel.setVisibility(View.GONE);
            regiitem.setVisibility(View.GONE);
        }else{
            v.setBackgroundColor(0xffb3b3b3);
            Valasztott = (int) v.getTag();
            Targy t = GameController.En.Felszereles[targyak[Valasztott].Slot];
            if(t != null) {
                lecserel.setVisibility(View.VISIBLE);
                regiitem.setVisibility(View.VISIBLE);
                TabInventory.UpdateInventoryRow(regiitem, t);
            }else{
                lecserel.setVisibility(View.GONE);
                regiitem.setVisibility(View.GONE);
            }
        }
        elozo = v;
    }

    public void ButtonVasarlas(View v) {
        if(Valasztott < 0){
            Toast.makeText(getApplicationContext(), "Nem választottál semmit.", Toast.LENGTH_SHORT).show();
            return;
        }

        Targy t = targyak[Valasztott];
        int actualPrice = arak[Valasztott];

        if (GameController.En.PenztKolt(actualPrice)) {
            GameController.En.Felszereles[t.Slot] = t;
            GameController.UpdateStats();
            finish();
        }else
            Toast.makeText(getApplicationContext(), "Nincs elég pénzed erre az itemre.", Toast.LENGTH_SHORT).show();

    }
}







