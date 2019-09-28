package com.example.budapestquest;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.budapestquest.ActionCards.AkcioAct;
import com.example.budapestquest.ActionCards.BoltAct;
import com.example.budapestquest.ActionCards.HarcAct;
import com.example.budapestquest.ActionCards.KaszinoAct;
import com.example.budapestquest.ActionCards.KondiAct;
import com.example.budapestquest.ActionCards.LepesAct;
import com.example.budapestquest.ActionCards.MunkaAct;
import com.example.budapestquest.ActionCards.AutomataAct;
import com.example.budapestquest.Karakterek.Karakter;
import com.example.budapestquest.Karakterek.LocalKarakter;
import com.example.budapestquest.ui.main.SectionsPagerAdapter;

import java.util.Random;

public class GameController {
    public static final String Version = "1001";

    /*
        Ez mindenhogy debil volt, sry...
        Most legalább már fix, hogy 1 instance lesz belőle, elvégre minek több? Meg minek lenne MainActivityben? Meg minek lenne a class fele static?
    */

    public static LocalKarakter En = null;

    public static Random rand = new Random();

    // MainActivity "tölti ki"
    public static SectionsPagerAdapter sectionsPagerAdapter = null;
    public static TabStats tabStats = null;
    public static TabInventory tabInventory = null;
    public static Context context = null;

    public static void UpdateStats(){
        tabStats.Update();
        tabInventory.Update();
    }

    /*
    *   Létrehozza a karaktert, egyfajta lateinit.
    * */
    //TODO: a kasztbónuszt lehetne a Karakternél hozzáadni maybe
    public static void CreateKarakter(String name, int uniId, int kasztId) {
        En = new LocalKarakter(name, uniId, kasztId);

        En.HP = 100;
        En.DMG = 10;
        En.DaP = 0;
        En.DeP = 0;
        En.CR = 0.05;
        En.DO = 0.05;

        switch (kasztId) {
            case Karakter.BUDA_ID: En.DeP += 3; break;
            case Karakter.PEST_ID: En.DaP += 3; break;
        }

        // Csalás
        if(name.equals("Programozo69")){
            En.FT = 2000;
            En.vonaljegy = 3;
        }
    }

    /*
    *   QR kódot dolgozza fel.
    * */
    protected static void HandleQR(char method, String version, String data) throws Exception{
        Intent intent;

        // Csúnya, de kell
        if(method != QRManager.QR_LEPES && En.kimaradas > 0){
            Toast.makeText(context, "Nem léphetsz, mert még van körkimaradásod!", Toast.LENGTH_LONG).show();
        }
        else {
            switch (method) {
                // Aréna
                case QRManager.QR_HARC1:
                case QRManager.QR_HARC2: {
                    if (!version.equals(Version))
                        throw new Exception("Különböző játékverzió. ( beolvasott: " + version + " != mienk: " + Version + " )");

                    intent = new Intent(context, HarcAct.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("ENKEZD", method == QRManager.QR_HARC1);
                    intent.putExtra("ENEMY", data);
                    context.startActivity(intent);
                }
                break;

                // Akciókártyák
                case QRManager.QR_BOLT: { // bolt (felhasználó választja ki mit vásárol)
                    if (data.equals(""))
                        throw new Exception("Nincs paraméter.");
                    int tier = data.charAt(0) - '0';//TODO ?
                    if (tier < 0 || tier > 2)
                        throw new Exception("Ismeretlen bolt típus ( " + tier + " ).");

                    intent = new Intent(context, BoltAct.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("TIER", tier);
                    context.startActivity(intent);
                }
                break;
                case QRManager.QR_AUTOMATA: { // AUTOMATA JEGYET VESZ
                    intent = new Intent(context, AutomataAct.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
                break;
                case QRManager.QR_KONDI: { // Kondi
                    intent = new Intent(context, KondiAct.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
                break;
                case QRManager.QR_KASZINO: { // kaszinó (felhasználó választja ki mit akar játszani)
                    intent = new Intent(context, KaszinoAct.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
                break;
                case QRManager.QR_LEPES: { // Lépés
                    //TODO: esetleg nem itt nézni?
                    if (En.kimaradas > 0) {
                        En.kimaradas--;
                        Toast.makeText(context, "Kimaradás csökkentve!", Toast.LENGTH_LONG).show();
                        tabStats.Update();
                    } else {
                        intent = new Intent(context, LepesAct.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    }
                }
                break;
                case QRManager.QR_AKCIOK: { // akciókártya húzása(data tárolja: 0 penz+, 1 penz-, 2 targy+) (azért nincs targy- mert tul nagy hátrány)
                    if (data.equals(""))
                        throw new Exception("Nincs paraméter.");
                    int akcio = data.charAt(0) - '0';//TODO ?
                    if (akcio < 0 || akcio > 3)
                        throw new Exception("Ismeretlen akció típus ( " + akcio + " ).");

                    intent = new Intent(context, AkcioAct.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("AKCIO", akcio);
                    context.startActivity(intent);
                }
                break;
                case QRManager.QR_MUNKA: { // munka (majd a felhasználó választja ki mennyit akar dolgozni)
                    intent = new Intent(context, MunkaAct.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
                break;
                default:
                    throw new Exception("Ismeretlen QR kód utasítás.");
            }
        }
    }
}
