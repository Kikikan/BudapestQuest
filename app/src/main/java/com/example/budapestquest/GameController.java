package com.example.budapestquest;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.budapestquest.Karakterek.Buda;
import com.example.budapestquest.Karakterek.Karakter;
import com.example.budapestquest.Karakterek.Pest;
import com.google.zxing.WriterException;

import java.util.ArrayList;
import java.util.List;

public class GameController {

    public static List<Integer> KasztRadioButtonIdList = new ArrayList<>();
    public static List<Integer> ClassRadioButtonIdList = new ArrayList<>();

    public static final int ELTE_ID = 1;
    public static final int BME_ID = 2;
    public static final int CORVINUS_ID = 3;
    public static final int TF_ID = 4;

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

    protected void CreateChar(String name, int kasztId, int uniId) {
        String uni = "";
        switch (kasztId) {
            case 0:
                En = new Buda();
                break;
            case 1:
                En = new Pest();
                break;
                default:
                    En = new Karakter();
                    break;
        }
        switch(uniId) {
            case GameController.ELTE_ID:
                uni = "ELTE";
                break;
            case GameController.BME_ID:
                uni = "BME";
                break;
            case GameController.CORVINUS_ID:
                uni = "Corvinus";
                break;
            case GameController.TF_ID:
                uni = "TF";
                break;
        }
        nameText.setText(name + " (" + uni + ")");
        En.Name = name;
        En.UNI = uniId;
        Update();
    }
}
