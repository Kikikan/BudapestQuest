package com.example.budapestquest;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.budapestquest.Karakterek.Buda;
import com.example.budapestquest.Karakterek.Karakter;
import com.example.budapestquest.Karakterek.Pest;
import com.example.budapestquest.akcioKartyak.Kondi;
import com.google.zxing.WriterException;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class GameController {
    public static final String Version = "1000";

    public static Karakter En = null;

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
        try {
            Bitmap bitmap = QRManager.TextToImageEncode('0',En.Serialize());
            qrView.setImageBitmap(bitmap);
        }
        catch (WriterException e) {
            e.printStackTrace();
        }
    }

    public static Karakter Load(File fp, String fn) {
        try {
            File file = new File(fp, fn);
            BufferedReader br = new BufferedReader(new FileReader(file));
            char[] data = new char[(int) file.length()];
            br.read(data);
            return Karakter.Deserialize(new String(data));
        }catch (Exception e){
            return null;
        }
    }

    public boolean Save(File fp, String fn){
        try{
            BufferedWriter bw = new BufferedWriter(new FileWriter(new File(fp, fn).getAbsoluteFile()));
            bw.write(En.Serialize());
            bw.flush();
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public void CreateChar(String name, int kasztId, int uniId) {
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
        nameText.setText(name + " (" + Karakter.EgyetemIDToString(uniId) + ")");
        En.Name = name;
        En.UNI = uniId;
        Update();
    }

    //Bí adta hozzá:
    public void edzes()
    {
        //TODO frontend-el összekötni és ott tudjon választani melyiket szeretne
        int valaszt = 0;

        switch(valaszt)
        {
            case 0: //harciEdzés
                En = Kondi.harciEdzes(En);
                break;
            case 1: //kardioEdzés
                En = Kondi.kardioEdzes(En);
                break;
            case 2: //vegyesEdzes
                En = Kondi.vegyesEdzes(En);
                break;
        }
    }

    // Context csak a Toast miatt jön, totál ideiglenes
    protected void HandleQR(char method, String version, String data, Context v) throws Exception{
        switch (method){
            // Aréna
            case '0':
                if (!version.equals(Version))
                    throw new Exception("Különböző játékverzió. ( beolvasott: "+version+" != mienk: "+Version+" )");

                Karakter enemy = Karakter.Deserialize(data);
                Toast.makeText(v, "Beolvasott karakter:" + enemy.Name + " ("+ Karakter.EgyetemIDToString(enemy.UNI) +")", Toast.LENGTH_LONG).show();

                break;
            case '1':
                break;

            // Akciókártyák
            //TODO: Panelek megnyitása
            case '2':
                Toast.makeText(v, "BOLT", Toast.LENGTH_LONG).show();
                break;
            case '3':
                Toast.makeText(v, "KONDI", Toast.LENGTH_LONG).show();
                edzes();
                break;
            default:
                throw new Exception("Ismeretlen QR kód utasítás.");
        }
    }
}
