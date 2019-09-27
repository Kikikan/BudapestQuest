package com.example.budapestquest.ActionCards;

import androidx.annotation.ColorInt;
import androidx.appcompat.app.AppCompatActivity;

import com.example.budapestquest.GameController;
import com.example.budapestquest.Karakterek.Karakter;
import com.example.budapestquest.MainActivity;
import com.example.budapestquest.R;
import com.example.budapestquest.Targyak.Targy;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class BoltAct extends AppCompatActivity {

    static final int capacity = 4;

    private int Tier;
    private int Valasztott = -1;
    private Targy[] targyak = new Targy[capacity];

    private GameController gc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bolt);

        gc = GameController.GetInstance();

        Tier = getIntent().getIntExtra("TIER", 0);

        try {
            TableLayout tl = findViewById(R.id.boltTable);

            for (int i = 0; i < capacity; i++) {
                Targy t = Targy.Generate(i, Tier);
                targyak[i] = t;

                View itemrow = LayoutInflater.from(this).inflate(R.layout.activity_bolt_item,null,false);

                TextView itemname =itemrow.findViewById(R.id.itemname);
                itemname.setText(t.modifier.Name + " " + t.item.Name);

                // Színes név az itemek ritkasága alapján, TODO: talán a modifier alapján színezni?
                double rarity = t.GetRarity();
                int color = 0xff476aa6;
                if(rarity < 0.01)
                    color = 0xffa8a620;
                else if(rarity < 0.1)
                    color = 0xff60b53c;
                itemname.setTextColor(color);

                ((TextView)itemrow.findViewById(R.id.itemdesc)).setText(t.item.Desc);
                ((TextView)itemrow.findViewById(R.id.itemar)).setText((int) (t.item.Price * t.modifier.PriceWeight * (gc.En.UNI == Karakter.CORVINUS_ID ? 0.8 : 1.0)) + " Ft");

                LinearLayout statok = itemrow.findViewById(R.id.itemstatok);
                double k = t.item.HP + t.modifier.HP;
                if(k != 0){
                    TextView stat = new TextView(this);
                    stat.setText("HP: " + (k > 0 ? "+" : "")+k);
                    stat.setTextColor(k > 0 ? Color.GREEN : Color.RED);
                    statok.addView(stat);
                }
                k = t.item.DMG + t.modifier.DMG;
                if(k != 0){
                    TextView stat = new TextView(this);
                    stat.setText("DMG: " + (k > 0 ? "+" : "")+k);
                    stat.setTextColor(k > 0 ? Color.GREEN : Color.RED);
                    statok.addView(stat);
                }
                k = t.item.DaP + t.modifier.DaP;
                if(k != 0){
                    TextView stat = new TextView(this);
                    stat.setText("DaP: " + (k > 0 ? "+" : "")+k);
                    stat.setTextColor(k > 0 ? Color.GREEN : Color.RED);
                    statok.addView(stat);
                }
                k = t.item.DeP + t.modifier.DeP;
                if(k != 0){
                    TextView stat = new TextView(this);
                    stat.setText("DeP: " + (k > 0 ? "+" : "")+k);
                    stat.setTextColor(k > 0 ? Color.GREEN : Color.RED);
                    statok.addView(stat);
                }

                itemrow.setTag(i);
                tl.addView(itemrow);
            }


            ((TextView) findViewById(R.id.boltTierText)).setText("Tier " + (Tier+1) + " Bolt");
        }catch (Exception e){
            Toast.makeText(getApplicationContext(), "Hiba: " + e.toString(), Toast.LENGTH_LONG).show();
        }
        // Itemek generálása



        /*
        for (int j = 0; j < capacity; j++) {
            targyak[j] = Targy.Generate(j, Tier);
        }*/
    }

    private View elozo = null;
    public void ButtonSelect(View v){
        if(elozo != null)
            elozo.setBackgroundColor(0xffffffff);
        Valasztott = (int)v.getTag();
        v.setBackgroundColor(0xffb3b3b3);
        elozo = v;
        //Toast.makeText(getApplicationContext(), "Választott: " + Valasztott, Toast.LENGTH_LONG).show();
    }

    public void ButtonVasarlas(View v) {
        if(Valasztott < 0){
            Toast.makeText(getApplicationContext(), "Nem választottál semmit.", Toast.LENGTH_LONG).show();
            return;
        }

        Targy t = targyak[Valasztott];
        int actualPrice = (int) (t.item.Price * t.modifier.PriceWeight * (gc.En.UNI == Karakter.CORVINUS_ID ? 0.8 : 1.0));
        if (gc.PenztKolt(actualPrice)) {
            gc.En.Felszereles[t.Slot] = t;
            gc.tabInventory.Update();
            gc.tabStats.Update();
            finish();
        }else
            Toast.makeText(getApplicationContext(), "Nincs elég pénzed erre az itemre.", Toast.LENGTH_LONG).show();

    }
}







