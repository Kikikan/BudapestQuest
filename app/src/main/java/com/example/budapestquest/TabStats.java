package com.example.budapestquest;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.budapestquest.Karakterek.Karakter;
import com.example.budapestquest.Karakterek.KarakterStats;

public class TabStats extends Fragment {
    private TextView nameText;

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
    private TextView crModText;
    private TextView doModText;

    private ImageView qrView;
    private ImageView kep;

    private GameController gc;
    //private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tabstats, container, false);
        //view = v;
        gc = GameController.GetInstance();

        nameText = v.findViewById(R.id.nevText);

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
        kep = v.findViewById(R.id.profpict);

        try {
            // A név garantált nem változik miután elkészült a karakterünk.
            nameText.setText(gc.En.Name + " (" + Karakter.EgyetemIDToString(gc.En.UNI) + ")");

            int resid;
            switch (gc.En.Name){
                case "creeper": resid = R.drawable.creeper; break;
                case "pepe": resid = R.drawable.pepe; break;
                case "patrik": resid = R.drawable.patrik; break;
                default: resid = R.drawable.face; break;
            }
            kep.setImageResource(resid);

        }catch (Exception e){
            Toast.makeText(v.getContext(), "Hiba: " + e.toString(), Toast.LENGTH_LONG).show();
        }
        Update();

        return v;
    }

    public void Update() {
        Karakter en = gc.En;
        KarakterStats ks = en.SumItemStats();

        jegyText.setText(   gc.vonaljegy + " db");
        ftText.setText(     en.FT + " Ft");
        xpText.setText(     String.valueOf(en.XP));

        hpText.setText(    String.valueOf(en.HP));
        if(ks.HP != 0) {
            hpModText.setText(" (" + (ks.HP > 0 ? "+" : "") + ks.HP + ")");
            hpModText.setTextColor(ks.HP > 0 ? Color.GREEN : Color.RED);
        }else hpModText.setText("");

        dmgText.setText(    String.valueOf(en.DMG));
        if(ks.DMG != 0) {
            dmgModText.setText(" (" + (ks.DMG > 0 ? "+" : "") + ks.DMG + ")");
            dmgModText.setTextColor(ks.DMG > 0 ? Color.GREEN : Color.RED);
        }else dmgModText.setText("");

        dapText.setText(    String.valueOf(en.DaP));
        if(ks.DaP != 0) {
            dapModText.setText(" (" + (ks.DaP > 0 ? "+" : "") + ks.DaP + ")");
            dapModText.setTextColor(ks.DaP > 0 ? Color.GREEN : Color.RED);
        }else dapModText.setText("");

        depText.setText(    String.valueOf(en.DeP));
        if(ks.DeP != 0) {
            depModText.setText(" (" + (ks.DeP > 0 ? "+" : "") + ks.DeP + ")");
            depModText.setTextColor(ks.DeP > 0 ? Color.GREEN : Color.RED);
        }else depModText.setText("");

        crText.setText(     String.valueOf(en.CR));
        doText.setText(     String.valueOf(en.DO));

        Bitmap bitmap = QRManager.TextToImageEncode(QRManager.QR_HARC1, en.Serialize());
        qrView.setImageBitmap(bitmap);
    }

    // Zsolt kérdi: Ez miért itt van?
    /*

    public void levelUPHP(View v)
    {
        if(GameController.En.XP >= 1)
        {
            GameController.En.HP += 1*100;
            GameController.En.XP -= 1;
        }
        else
        {
            //NINCS ELÉG XP-éd
        }
    }

    public void levelUPDMG()
    {
        if(GameController.En.XP >= 1)
        {
            GameController.En.DMG += 1*10;
            GameController.En.XP -= 1;
        }
        else
        {
            //NINCS ELÉG XP-éd
        }
    }

    public void levelUPDaP()
    {
        if(GameController.En.XP >= 1)
        {
            GameController.En.DaP += 1;
            GameController.En.XP -= 1;
        }
        else
        {
            //NINCS ELÉG XP-éd
        }
    }

    public void levelUPDeP()
    {
        if(GameController.En.XP >= 1)
        {
            GameController.En.DeP += 1;
            GameController.En.XP -= 1;
        }
        else
        {
            //NINCS ELÉG XP-éd
        }
    }

    public void levelUPCR()
    {
        if(GameController.En.XP >= 1)
        {
            GameController.En.CR += 1;
            GameController.En.XP -= 1;
        }
        else
        {
            //NINCS ELÉG XP-éd
        }
    }

    public void levelUPDO()
    {
        if(GameController.En.XP >= 1)
        {
            GameController.En.DO += 1;
            GameController.En.XP -= 1;
        }
        else
        {
            //NINCS ELÉG XP-éd
        }
    }
    */
}
