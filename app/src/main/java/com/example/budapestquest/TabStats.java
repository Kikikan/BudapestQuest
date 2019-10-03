package com.example.budapestquest;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.budapestquest.Karakterek.KarakterStats;

public class TabStats extends Fragment implements View.OnClickListener {
    private TextView nameText;
    private ImageView kep;

    private TextView jegyText;
    private TextView ftText;
    private TextView xpText;
    private TextView xpModText;

    private TextView hpText;
    private TextView dmgText;
    private TextView dapText;
    private TextView depText;
    private TextView crText;
    private TextView doText;

    private TextView hpModText;
    private TextView dmgModText;
    private TextView dapModText;
    private TextView depModText;

    private ImageView qrView;

    private Button buttonLvlHP;
    private Button buttonLvlDMG;
    private Button buttonLvlDaP;
    private Button buttonLvlDeP;

    private View kimaradasView;

    //TODO: ez így nagyon undorító, kéne keresni valami szebb megoldást
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tabstats, container, false);

        nameText = v.findViewById(R.id.nevText);
        kep = v.findViewById(R.id.profpict);

        jegyText = v.findViewById(R.id.statJegy_Base);
        ftText = v.findViewById(R.id.statPenz_Base);
        xpText = v.findViewById(R.id.statXP_Base);
        xpModText = v.findViewById(R.id.statXP_Modif);

        hpText = v.findViewById(R.id.statHP_Base);
        hpModText = v.findViewById(R.id.statHP_Modif);

        dmgText = v.findViewById(R.id.statDMG_Base);
        dmgModText = v.findViewById(R.id.statDMG_Modif);

        dapText = v.findViewById(R.id.statDaP_Base);
        dapModText = v.findViewById(R.id.statDaP_Modif);

        depText = v.findViewById(R.id.statDeP_Base);
        depModText = v.findViewById(R.id.statDeP_Modif);

        crText = v.findViewById(R.id.statCR_Base);
        doText = v.findViewById(R.id.statDO_Base);

        qrView = v.findViewById(R.id.qrView);

        buttonLvlHP = v.findViewById(R.id.HPUP);
        buttonLvlDMG = v.findViewById(R.id.DMGUP);
        buttonLvlDaP = v.findViewById(R.id.DaPUP);
        buttonLvlDeP = v.findViewById(R.id.DePUP);

        buttonLvlHP.setOnClickListener(this);
        buttonLvlDMG.setOnClickListener(this);
        buttonLvlDaP.setOnClickListener(this);
        buttonLvlDeP.setOnClickListener(this);

        kimaradasView = v.findViewById(R.id.statKimaradas);

        // A név garantáltan nem változik miután elkészült a karakterünk. (Kivéve persze ha új karaktert csinálunk)
        UpdateMeta();
        Update();

        return v;
    }

    /*
    *   Kérdezzük meg, biztosan szeretné-e fejleszteni a kiválasztott statok, mivel nagyon kicsik a gombok.
    * */
    @Override
    public void onClick(View v){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setCancelable(false);
        builder.setTitle("Fejlődés");
        builder.setNegativeButton("Nem", null);
        switch (v.getId()){
            case R.id.HPUP:
                builder.setMessage("Biztosan szeretnéd fejleszteni a HP-dat?");
                builder.setPositiveButton("Igen", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(GameController.En.DoLVLUP_HP())
                            Update();
                    }
                });
                break;
            case R.id.DMGUP:
                builder.setMessage("Biztosan szeretnéd fejleszteni a DMG-det?");
                builder.setPositiveButton("Igen", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(GameController.En.DoLVLUP_DMG())
                            Update();
                    }
                });break;
            case R.id.DaPUP:
                builder.setMessage("Biztosan szeretnéd fejleszteni a DaP-odat?");
                builder.setPositiveButton("Igen", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(GameController.En.DoLVLUP_DaP())
                            Update();
                    }
                });break;
            case R.id.DePUP:
                builder.setMessage("Biztosan szeretnéd fejleszteni a DeP-edet?");
                builder.setPositiveButton("Igen", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(GameController.En.DoLVLUP_DeP())
                            Update();
                    }
                });break;
            default: return;
        }
        builder.show();
    }

    /*
    *   Csak a nevet, kasztot, unit és a képet frissíti
    * */
    public void UpdateMeta(){
        nameText.setText(GameController.En.Name + "\n(" + GameController.En.KasztToString() + ", " + GameController.En.EgyetemToString() + ")");

        int resid;
        switch (GameController.En.Name){
            case "creeper": resid = R.drawable.creeper; break;
            case "pepe": resid = R.drawable.pepe; break;
            case "patrik": resid = R.drawable.patrik; break;
            default: resid = R.drawable.face; break;
        }
        kep.setImageResource(resid);
    }

    protected void SetStat(TextView first, TextView mod, double vf, double vm){
        first.setText(String.valueOf(vf));
        if(vm != 0) {
            mod.setText(" (" + (vm > 0 ? "+" : "") + vm + ")");
            mod.setTextColor(vm > 0 ? Color.GREEN : Color.RED);
        }
        else
            mod.setText("");
    }

    protected void ToggleButton(Button btn, boolean state){
        btn.setClickable(state);
        btn.setVisibility(state ? View.VISIBLE : View.GONE);
    }

    /*
    *   Frissíti a tabon lévő statokat.
    * */
    public void Update() {
        KarakterStats ks = GameController.En.SumItemStats();

        jegyText.setText(GameController.En.vonaljegy + " db");
        ftText.setText(GameController.En.FT + " Ft");
        xpText.setText(String.valueOf(GameController.En.XP));
        xpModText.setText("(elkölthető: " + GameController.En.elkolthetoXP + ")");

        SetStat(hpText, hpModText, GameController.En.HP, ks.HP);
        SetStat(dmgText, dmgModText, GameController.En.DMG, ks.DMG);
        SetStat(dapText, dapModText, GameController.En.DaP, ks.DaP);
        SetStat(depText, depModText, GameController.En.DeP, ks.DeP);

        crText.setText(String.valueOf(GameController.En.CR));
        doText.setText(String.valueOf(GameController.En.DO));

        // Csak akkor látszódjanak, ha van elég XP-nk a leveluphoz.
        boolean lvlupbuttons = GameController.En.elkolthetoXP >= 1;
        ToggleButton(buttonLvlHP, lvlupbuttons);
        ToggleButton(buttonLvlDMG, lvlupbuttons);
        ToggleButton(buttonLvlDaP, lvlupbuttons);
        ToggleButton(buttonLvlDeP, lvlupbuttons);

        // Ha van kimaradásunk, akkor azt írjuk ki a QR kód rajzolása helyett.
        if(GameController.En.kimaradas > 0) {
            kimaradasView.setVisibility(View.VISIBLE);
            qrView.setVisibility(View.GONE);
            ((TextView)kimaradasView.findViewById(R.id.statKimaradas_Base)).setText(String.valueOf(GameController.En.kimaradas));
        }else {
            kimaradasView.setVisibility(View.GONE);
            qrView.setVisibility(View.VISIBLE);
            Bitmap bitmap = QRManager.TextToImageEncode(QRManager.QR_HARC1, GameController.En.Serialize());
            qrView.setImageBitmap(bitmap);
        }
    }
}
