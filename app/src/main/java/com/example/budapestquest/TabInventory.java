package com.example.budapestquest;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.budapestquest.Targyak.Targy;

public class TabInventory extends Fragment {

    private View item0Row;
    private View item1Row;
    private View item2Row;
    private View item3Row;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tabinventory, container, false);

        item0Row = v.findViewById(R.id.item0);
        item1Row = v.findViewById(R.id.item1);
        item2Row = v.findViewById(R.id.item2);
        item3Row = v.findViewById(R.id.item3);

        Update();

        return v;
    }

    public static void MakeStat(TextView stat, String s, double v){
        if(v == 0){
            stat.setVisibility(View.GONE);
            return;
        }
        stat.setVisibility(View.VISIBLE);
        stat.setText(s + (v > 0 ? "+" : "")+v);
        stat.setTextColor(v > 0 ? 0xff5abf36 : 0xffbf3636);
    }

    public static void UpdateInventoryRow(View row, Targy targy){
        TextView itemname = row.findViewById(R.id.itemname);
        TextView itemdesc = row.findViewById(R.id.itemdesc);
        LinearLayout statok = row.findViewById(R.id.itemstatok);

        //TODO: ez megint csak egy ugly hack
        if(targy == null){
            itemname.setText(" - ");
            itemdesc.setVisibility(View.GONE);
            statok.setVisibility(View.GONE);
            return;
        }
        itemdesc.setVisibility(View.VISIBLE);
        statok.setVisibility(View.VISIBLE);

        itemname.setText(targy.modifier.Name + " " + targy.item.Name);
        itemname.setTextColor(targy.modifier.Color);
        itemdesc.setText(targy.item.Desc);

        MakeStat((TextView) statok.findViewById(R.id.itemstatHP), "HP: ", targy.SumHP());
        MakeStat((TextView) statok.findViewById(R.id.itemstatDMG), "DMG: ", targy.SumDMG());
        MakeStat((TextView) statok.findViewById(R.id.itemstatDaP), "DaP: ", targy.SumDaP());
        MakeStat((TextView) statok.findViewById(R.id.itemstatDeP), "DeP: ", targy.SumDeP());
    }

    /*
     *   Frissíti a tabon lévő itemeket.
     * */
    public void Update(){
        UpdateInventoryRow(item0Row, GameController.En.Felszereles[Targy.FELSO_ID]);
        UpdateInventoryRow(item1Row, GameController.En.Felszereles[Targy.NADRAG_ID]);
        UpdateInventoryRow(item2Row, GameController.En.Felszereles[Targy.CIPO_ID]);
        UpdateInventoryRow(item3Row, GameController.En.Felszereles[Targy.FEGYVER_ID]);
    }

}
