package com.example.budapestquest;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.Intent;
import android.widget.Toast;

import com.example.budapestquest.ActionCards.AkcioAct;
import com.example.budapestquest.ActionCards.BoltAct;
import com.example.budapestquest.ActionCards.HarcAct;
import com.example.budapestquest.ActionCards.KaszinoAct;
import com.example.budapestquest.ActionCards.LepesAct;
import com.example.budapestquest.ActionCards.MunkaAct;
import com.example.budapestquest.ActionCards.AutomataAct;
import com.example.budapestquest.Karakterek.Buda;
import com.example.budapestquest.Karakterek.Karakter;
import com.example.budapestquest.Karakterek.Pest;
import com.example.budapestquest.akcioKartyak.HuzottKartyak;
import com.example.budapestquest.Targyak.Targy;
import com.example.budapestquest.akcioKartyak.Kondi;

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

    private ImageView kep;

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

        kep = v.findViewById(R.id.profpict);
    }

    public void Update() {
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
        if(GameController.En.Name.toLowerCase().equals("creeper"))
            kep.setImageResource(R.drawable.creeper);
        else if(GameController.En.Name.toLowerCase().equals("pepe"))
            kep.setImageResource(R.drawable.pepe);
        else
            kep.setImageResource(R.drawable.face);
        Update();
    }

    //Bí adta hozzá:

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
                //itamCsere(talat);
                break;
                //azért nincsen targy-, mert túl nagy veszteség
            case "3"://aréna győzelem
                En.arenaBajnok();
                break;
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

                intent = new Intent(v, HarcAct.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("ENKEZD", method == QRManager.QR_HARC1);
                intent.putExtra("ENEMY", data);
                v.startActivity(intent);
                break;

            // Akciókártyák
            //TODO: Panelek megnyitása
            case QRManager.QR_BOLT: // bolt (felhasználó választja ki mit vásárol)
                if(data.equals(""))
                    throw new Exception("Nincs paraméter.");
                int tier = data.charAt(0) - '0';//TODO ?
                if(tier < 0 || tier > 2)
                    throw new Exception("Ismeretlen bolt típus ( " + tier + " ).");

                intent = new Intent(v, BoltAct.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("TIER", tier);
                v.startActivity(intent);
                break;
            case QRManager.QR_AUTOMATA: // AUTOMATA JEGYET VESZ
                intent = new Intent(v, AutomataAct.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                v.startActivity(intent);
                break;
            case QRManager.QR_KONDI: // kondi (felhasználó választja ki mit akar edzeni/ data: 0 erőnléti, 1 kardio)
                switch (data.charAt(0)){
                    case '0':
                        Toast.makeText(v, "Erőnléti edzés", Toast.LENGTH_LONG).show();
                        Kondi.harciEdzes();
                        break;
                    case '1':
                        Toast.makeText(v, "Kardio edzés", Toast.LENGTH_LONG).show();
                        Kondi.kardioEdzes();
                        break;
                    default:
                        throw new Exception("Ismeretlen edzés típus.");
                }
                break;
            case QRManager.QR_KASZINO: // kaszinó (felhasználó választja ki mit akar játszani)
                intent = new Intent(v, KaszinoAct.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                v.startActivity(intent);
                break;
            case QRManager.QR_LEPES: // lepes(data tárolja: 1 használ jegyet, 0 nem használ jegyet)
                if(data.equals(""))
                    throw new Exception("Nincs paraméter.");
                int lepes = data.charAt(0) - '0';//TODO ?
                if(lepes < 0 || lepes > 1)
                    throw new Exception("Ismeretlen lépés típus ( " + lepes + " ).");

                intent = new Intent(v, LepesAct.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("LEPES", lepes);
                v.startActivity(intent);
                break;
            case QRManager.QR_AKCIOK: // akciókártya húzása(data tárolja: 0 penz+, 1 penz-, 2 targy+) (azért nincs targy- mert tul nagy hátrány)
                if(data.equals(""))
                    throw new Exception("Nincs paraméter.");
                int akcio = data.charAt(0) - '0';//TODO ?
                if(akcio < 0 || akcio > 2)
                    throw new Exception("Ismeretlen akció típus ( " + akcio + " ).");

                intent = new Intent(v, AkcioAct.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("AKCIO", akcio);
                v.startActivity(intent);
                break;
            case QRManager.QR_MUNKA: // munka (majd a felhasználó választja ki mennyit akar dolgozni)
                intent = new Intent(v, MunkaAct.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                v.startActivity(intent);
                break;
            default:
                throw new Exception("Ismeretlen QR kód utasítás.");
        }
    }
}
