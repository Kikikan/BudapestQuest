package com.example.budapestquest;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.example.budapestquest.Karakterek.Buda;
import com.example.budapestquest.Karakterek.Karakter;
import com.example.budapestquest.Karakterek.Pest;
import com.example.budapestquest.akcioKartyak.HuzottKartyak;
import com.example.budapestquest.akcioKartyak.Kaszino;
import com.example.budapestquest.Targyak.Targy;
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
        Bitmap bitmap = QRManager.TextToImageEncode(QRManager.QR_HARC1, En.Serialize());
        qrView.setImageBitmap(bitmap);
    }

    //TODO: mentés / betöltés
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
                Kaszino.poker(osszeg);
                break;
            case 1:
                Kaszino.rulett(osszeg);
                break;
            case 2:
                Kaszino.blackJack(osszeg);
                break;
        }
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
        //itt vizsgálja, hogy használjon jegyet (1) vagy ne (0)
        boolean jegyHasznalat = data.charAt(1)=='1';

        if(!En.lepes(jegyHasznalat))
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
            case "0"://penz+
                int nyert = HuzottKartyak.talalVagyVeszitPenzt();
                En.FT += nyert;
                //TODO kiírathatja mennyit nyert
                break;
            case "1"://penz-
                int veszit = HuzottKartyak.talalVagyVeszitPenzt();
                En.FT -= veszit;
                //TODO kiírathatja mennyit veszített
                break;
            case "2"://targy+
                Targy talat = HuzottKartyak.talaltTargy();
                //TODO frontend, hogy vállaszon melyik kell neki
                itamCsere(talat);
                break;
                //azért nincsen targy-, mert túl nagy veszteség
            case "3"://aréna győzelem
                En.arenaBajnok();
                break;
        }
    }

//--------------------------------------------------

//--------------------------------------------------
//munka

    //TODO db-t megadni frontendről
    //lehet munkát válalni és a db jelezi, hány kört akarsz kimaradni
    public void munka()
    {
        int db = 0;

        En.munka(db);
    }

//--------------------------------------------------

    //TODO összehozni, hogy tudjon választani a felhasználó, hogy akar cserélni
    //azért van, hogy ha boltba is kell tudjuk használni, hogy a felhasználónak egy felületet kell lene
    //feldoni, hogy akarod ezt az itemet (true) vagy inkább megtartod (false) amid van
    //valtozoban akarcserelni eltárolni ideiglenes)
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
        Intent intent;
        switch (method){
            // Aréna
            case QRManager.QR_HARC1:
            case QRManager.QR_HARC2:
                if (!version.equals(Version))
                    throw new Exception("Különböző játékverzió. ( beolvasott: "+version+" != mienk: "+Version+" )");

                /*Karakter enemy = Karakter.Deserialize(data);
                if(enemy == null)
                    throw new Exception("Hiba a karakter beolvasásánál.");*/

                //Toast.makeText(v, "Beolvasott karakter:" + enemy.Name + " ("+ Karakter.EgyetemIDToString(enemy.UNI) +") Kezd: " + (method == QRManager.QR_HARC1 ? "én" : "ő"), Toast.LENGTH_LONG).show();
                //Fight(this, enemy, method == QRManager.QR_HARC1);

                intent = new Intent(v, HarcAct.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("ENKEZD", method == QRManager.QR_HARC1);
                intent.putExtra("ENEMY", data);
                v.startActivity(intent);
                break;

            // Akciókártyák
            //TODO: Panelek megnyitása
            case QRManager.QR_BOLT://bolt (felhasználó választja ki mit vásárol)
                intent = new Intent(v, KaszinoAct.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                v.startActivity(intent);
                break;
            case QRManager.QR_KONDI://kondi (felhasználó választja ki mit akar edzeni/ data: 0 erőnléti, 1 kondi)
                intent = new Intent(v, KaszinoAct.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                v.startActivity(intent);
                break;
            case QRManager.QR_KASZINO://kaszinó (felhasználó választja ki mit akar játszani)
                intent = new Intent(v, KaszinoAct.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                v.startActivity(intent);
                break;
            case QRManager.QR_LEPES://lepes(data tárolja: 1 használ jegyet, 0 nem használ jegyet)
                intent = new Intent(v, KaszinoAct.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                v.startActivity(intent);
                break;
            case QRManager.QR_AKCIOK://akciókártya húzása(data tárolja: 0 penz+, 1 penz-, 2 targy+) (azért nincs targy- mert tul nagy hátrány)
                intent = new Intent(v, KaszinoAct.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                v.startActivity(intent);
                break;
            case QRManager.QR_MUNKA://munka (majd a felhasználó választja ki mennyit akar dolgozni)
                intent = new Intent(v, KaszinoAct.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                v.startActivity(intent);
                munka();
                break;
            default:
                throw new Exception("Ismeretlen QR kód utasítás.");
        }
        Update();
    }
}
