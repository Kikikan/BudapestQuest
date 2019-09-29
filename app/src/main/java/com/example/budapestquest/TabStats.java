package com.example.budapestquest;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.budapestquest.Karakterek.KarakterStats;

public class TabStats extends Fragment {
    private TextView nameText;
    private ImageView kep;

    private TextView jegyText;
    private TextView ftText;
    private TextView xpText;

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

        kimaradasView = v.findViewById(R.id.statKimaradas);

        // A név garantáltan nem változik miután elkészült a karakterünk. (Kivéve persze ha új karaktert csinálunk)
        UpdateMeta();
        Update();

        return v;
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

    /*
    *   Frissíti a tabon lévő statokat.
    * */
    public void Update() {
        KarakterStats ks = GameController.En.SumItemStats();

        jegyText.setText(GameController.En.vonaljegy + " db");
        ftText.setText(GameController.En.FT + " Ft");
        xpText.setText(String.valueOf(GameController.En.XP));

        SetStat(hpText, hpModText, GameController.En.HP, ks.HP);
        SetStat(dmgText, dmgModText, GameController.En.DMG, ks.DMG);
        SetStat(dapText, dapModText, GameController.En.DaP, ks.DaP);
        SetStat(depText, depModText, GameController.En.DeP, ks.DeP);

        crText.setText(String.valueOf(GameController.En.CR));
        doText.setText(String.valueOf(GameController.En.DO));

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
