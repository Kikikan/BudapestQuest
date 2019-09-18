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
import com.example.budapestquest.Targyak.Targy;
import com.example.budapestquest.akcioKartyak.HuzottKartyak;
import com.example.budapestquest.akcioKartyak.Kaszino;
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
//-----------------------------------------
//EDZÉS

    //TODO frontend-el összekötni és ott tudjon választani melyiket szeretne
    public void edzes()
    {
        int valaszt = 0;

        switch(valaszt)
        {
            case 0: //harciEdzés
                En.harciEdzes();
                break;
            case 1: //kardioEdzés
                En.kardioEdzes();
                break;
        }
    }

//-------------------------------------------------
//KASZINÓ

    //TODO valaszt és osszegnek megkell adni az erteket frontendről
    public void kaszino()
    {
        int valaszt = 0;

        int osszeg = 0;

        switch (valaszt)
        {
            case 0:
                kaszinoPoker(osszeg);
                break;
            case 1:
                kaszinoRulett(osszeg);
                break;
            case 2:
                kaszinoBlackj(osszeg);
                break;
        }
    }

    public void kaszinoPoker (int osszeg)
    {
        En = Kaszino.poker(En, osszeg);
    }

    public void kaszinoRulett (int osszeg)
    {
        En = Kaszino.rulett(En, osszeg);
    }

    public void kaszinoBlackj (int osszeg)
    {
        En = Kaszino.blackJack(En, osszeg);
    }

//-------------------------------------------------

//-----------------------------------------------
//AUTOMATA

    //TODO darabjegynek frontendről kapja meg az adatott
    public void jegyVasarlas()
    {
        int darabjegy = 0;

        En.jegyvasarlas(darabjegy);
    }

//-------------------------------------------------

//-------------------------------------------------
//LEPES

    public void lepes (String data)
    {
        boolean valaszt = data.charAt(1)=='1';

        if(!En.lepes(valaszt))
        {
            //kimarad a körből
        }
    }


//--------------------------------------------------


//--------------------------------------------------
//kartyahuzas
    public void kartyahuzas(String data)
    {
        switch (data)
        {
            case "0":
                int nyert = HuzottKartyak.talalVagyVeszitPenzt();
                En.FT += nyert;
                //TODO kiírathatja mennyit nyert
                break;
            case "1":
                int veszit = HuzottKartyak.talalVagyVeszitPenzt();
                En.FT -= veszit;
                //TODO kiírathatja mennyit veszített
                break;
            case "2":
                Targy talat = HuzottKartyak.talaltTargy();
                //TODO frontend, hogy vállaszon melyik kell neki
                itamCsere(talat);
                break;

        }
    }

//--------------------------------------------------

//--------------------------------------------------
//munka

    //TODO db-t megadni frontendről
    public void munka()
    {
        int db = 0;

        En.munka(db);
    }

//--------------------------------------------------

    //TODO összehozni, hogy tudjon választani a felhasználó, hogy akar cserélni
    public void itamCsere (Targy targy)
    {
        boolean akarcserelni = true;

        if(akarcserelni)
        {
            En.targyCsere(targy);
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
            case '4':
                Toast.makeText(v, "AUTOMATA", Toast.LENGTH_LONG).show();
                jegyVasarlas();
                break;
            case '5':
                Toast.makeText(v, "KASZINÓ", Toast.LENGTH_LONG).show();
                kaszino();
                break;
            case '6':
                Toast.makeText(v, "LEPES", Toast.LENGTH_LONG).show();
                lepes(data);
                break;
            case '7':
                Toast.makeText(v, "KARTYAHUZAS", Toast.LENGTH_LONG).show();
                kartyahuzas(data);
                break;
            case '8':
                Toast.makeText(v, "MUNKA", Toast.LENGTH_LONG).show();
                munka();
                break;


            default:
                throw new Exception("Ismeretlen QR kód utasítás.");
        }
    }

}
