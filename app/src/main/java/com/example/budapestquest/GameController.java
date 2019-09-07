package com.example.budapestquest;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.budapestquest.Karakterek.Eros;
import com.example.budapestquest.Karakterek.Karakter;
import com.example.budapestquest.Karakterek.Kitarto;
import com.example.budapestquest.Karakterek.Mozgekony;
import com.google.zxing.WriterException;

public class GameController {

    private static Karakter En;
    private TextView nameText;
    private TextView hpText;
    private TextView ftText;
    private TextView xpText;
    private TextView dmgText;
    private TextView dapText;
    private TextView depText;
    private TextView crText;
    private TextView doText;
    private ImageView qrView;
    private View view;

    protected void Initialize(View v) {
        nameText = v.findViewById(R.id.nevText);
        hpText = v.findViewById(R.id.hpText);
        ftText = v.findViewById(R.id.ftText);
        xpText = v.findViewById(R.id.xpText);
        dmgText = v.findViewById(R.id.dmgText);
        dapText = v.findViewById(R.id.dapText);
        depText = v.findViewById(R.id.depText);
        crText = v.findViewById(R.id.crText);
        doText = v.findViewById(R.id.doText);
        qrView = v.findViewById(R.id.qrView);
        view = v;
    }

    protected void Update() {
        hpText.setText("HP: " + En.HP);
        ftText.setText("Pénz: " + En.FT + " Ft");
        xpText.setText("XP: " + En.XP);
        dmgText.setText("Sebzés: " + En.DMG);
        dapText.setText("Támadási Pont: " + En.DaP);
        depText.setText("Védekezési Pont: " + En.DeP);
        crText.setText("Kritikus Sebzés Esélye: " + En.CR + "%");
        doText.setText("Kivédés Esélye: " + En.DO + "%");
        QRManager qr = new QRManager();
        try {
            Bitmap bitmap = qr.TextToImageEncode(En.GenerateCode(), view);

            qrView.setImageBitmap(bitmap);
        }
        catch (WriterException e) {
            e.printStackTrace();
        }
    }

    protected void CreateChar(String name, int id) {
        switch (id) {
            case 2131230818:
                En = new Eros();
                break;
            case 2131230819:
                En = new Kitarto();
                break;
            case 2131230820:
                En = new Mozgekony();
                break;
                default:
                    En = new Karakter();
                    break;
        }
        nameText.setText(name);
        En.Name = name;
        Update();
    }
}
