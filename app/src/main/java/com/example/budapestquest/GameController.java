package com.example.budapestquest;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

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

import java.util.Locale;
import java.util.Random;

public class GameController {
    public static final String Version = "1001";
    public static final String SaveLocation = "com.example.budapestquest.SaveGame";

    public static LocalKarakter En = null;

    // Lehetőleg mindenhol ezt a randomot használjuk, hogy pörgessük.
    public static Random rand = new Random();

    // MainActivity "tölti ki"
    public static TabStats tabStats = null;
    public static TabInventory tabInventory = null;
    public static MainActivity context = null;

    /*
    *   Frissíti az "Én" és az "Inventory" tabot is
    * */
    public static void UpdateStats(){
        tabStats.Update();
        tabInventory.Update();
    }

    /*
    *   Feldob egy ablakot a megadott címmel és üzenettel, melyre csak OK-ot lehet válaszolni.
    * */
    public static void ShowPopup(String title, String body){
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle(title);
            builder.setMessage(body);
            builder.setCancelable(false);
            builder.setPositiveButton("Ok", null);
            builder.show();
        }catch (Exception e){
            Toast.makeText(context, "Error: " + e.toString(), Toast.LENGTH_LONG).show();
        }
    }

    /*
    *   Elmenti a játékállást.
    * */
    public static boolean SaveGame(){
        try {
            SharedPreferences sp = context.getSharedPreferences(SaveLocation, Context.MODE_PRIVATE);
            SharedPreferences.Editor spe = sp.edit();
            spe.putString("karakter", En.Serialize());
            spe.putInt("vonaljegy", En.vonaljegy);
            spe.putInt("kimaradas", En.kimaradas);
            spe.putInt("elkothetoxp", En.elkolthetoXP);
            spe.putBoolean("winreward", En.winreward);
            spe.apply();
        }catch (Exception e){
            return false;
        }
        return true;
    }

    /*
    *   Betölti a játékállást.
    * */
    public static boolean LoadGame(){
        try {
            SharedPreferences sp = context.getSharedPreferences(SaveLocation, Context.MODE_PRIVATE);

            String k = sp.getString("karakter", "");
            int vj = sp.getInt("vonaljegy", -1);
            int km = sp.getInt("kimaradas", -1);
            int xp = sp.getInt("elkothetoxp", -1);
            boolean rw = sp.getBoolean("winreward", false);

            if (k.isEmpty() || vj == -1 || km == -1 || xp == -1) return false;

            En = new LocalKarakter(k);
            En.vonaljegy = vj;
            En.kimaradas = km;
            En.elkolthetoXP = xp;
            En.winreward = rw;
        }catch (Exception e){
            return false;
        }
        return true;
    }

    /*
    *   Létrehozza a karaktert, egyfajta lateinit.
    * */
    //TODO: a kasztbónuszt lehetne a Karakternél hozzáadni maybe
    public static void CreateKarakter(String name, int uniId, int kasztId) {
        En = new LocalKarakter(name, uniId, kasztId);

        //TODO: Kezdő statok
        En.HP = 1000;
        En.DMG = 100;
        En.DaP = 0;
        En.DeP = 0;
        En.CR = 0.05;
        En.DO = 0.05;

        //TODO: Kaszt bónuszok
        switch (kasztId) {
            case Karakter.BUDA_ID: En.DeP += 3; break;
            case Karakter.PEST_ID: En.DaP += 3; break;
        }

        // Csalás
        //TODO: végleges verzióból kivenni
        if(name.startsWith("5vos")){
            En.FT = 2000;
            En.vonaljegy = 3;
            En.GiveXP(5);
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
                // Aréna / Harc (data: serializált enemy)
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
                case QRManager.QR_BOLT: { // Bolt (data: 0 tier1, 1 tier2, 2 tier3) (felhasználó választja ki mit vásárol)
                    if (data.equals(""))
                        throw new Exception("Nincs paraméter.");
                    int tier = data.charAt(0) - '0';
                    if (tier < 0 || tier > 2)
                        throw new Exception("Ismeretlen bolt típus ( " + tier + " ).");

                    intent = new Intent(context, BoltAct.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("TIER", tier);
                    context.startActivity(intent);
                }
                break;
                case QRManager.QR_AUTOMATA: { // Automata
                    intent = new Intent(context, AutomataAct.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
                break;
                case QRManager.QR_KONDI: { // Kondi (felhasználó választja ki az edzéstípust)
                    intent = new Intent(context, KondiAct.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
                break;
                case QRManager.QR_KASZINO: { // Kaszinó (felhasználó választja ki mit akar játszani)
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
                case QRManager.QR_AKCIOK: { // Akciókártya Húzása (data: 0 penz+, 1 penz-, 2 targy+ RELATÍV ESÉLYEK) (azért nincs targy- mert tul nagy hátrány)
                    int o1, o2, o3;
                    if(data.length() == 0)
                        throw new Exception("Nincs paraméter.");
                    if(version.equals("1000") && data.charAt(0) == '3'){ // Kompatibilitás miatt, TODO: Ha újra nyomtatunk, akkor azthiszem felesleges
                        if(En.ArenaBajnok()) {
                            tabStats.Update();
                            ShowPopup("Aréna bajnoka", "Gratulálunk "+En.Name+"!\nAz Aréna új bajnokot avathatott személyedben! Jutalmul pedig ezeket kaptad:\n"+LocalKarakter.ArenabajnokRewardXP+" XP és "+LocalKarakter.ArenabajnokRewardPenz+" Ft");
                        }
                        else
                            Toast.makeText(context, "Már aréna bajnok vagy.", Toast.LENGTH_LONG).show();
                        return;
                    }else {
                        if (data.length() != 3)
                            throw new Exception("3 paraméter szükséges.");
                        o1 = data.charAt(0) - '0';
                        o2 = data.charAt(1) - '0';
                        o3 = data.charAt(2) - '0';

                        if (o1 < 0 || o2 < 0 || o3 < 0)
                            throw new Exception("Nem lehet negatív paraméter.");
                        if (o1 == 0 && o2 == 0 && o3 == 0)
                            throw new Exception("Nem lehet minden paraméter 0.");
                    }

                    intent = new Intent(context, AkcioAct.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("ODDS_PENZ+", o1);
                    intent.putExtra("ODDS_PENZ-", o2);
                    intent.putExtra("ODDS_TARGY+", o3);
                    context.startActivity(intent);
                }
                break;
                case QRManager.QR_MUNKA: { // Munka (majd a felhasználó választja ki mennyit akar dolgozni)
                    intent = new Intent(context, MunkaAct.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
                break;
                /*case QRManager.QR_ARENABAJNOK: {
                    if(En.ArenaBajnok()) {
                        tabStats.Update();
                        ShowPopup("Aréna bajnoka", "Gratulálunk "+En.Name+"!\nAz Aréna új bajnokot avathatott személyedben! Jutalmul pedig ezeket kaptad:\n"+LocalKarakter.ArenabajnokRewardXP+" XP és "+LocalKarakter.ArenabajnokRewardPenz+" Ft");
                    }
                    else
                        Toast.makeText(context, "Az Aréna bajnoki címének elnyeréséhez győznöd kell egy csatában.", Toast.LENGTH_LONG).show();
                }
                break;*/
                default:
                    throw new Exception("Ismeretlen QR kód utasítás.");
            }
        }
    }
}
