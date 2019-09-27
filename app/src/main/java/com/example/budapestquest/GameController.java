package com.example.budapestquest;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.example.budapestquest.ActionCards.BoltAct;
import com.example.budapestquest.ActionCards.HarcAct;
import com.example.budapestquest.ActionCards.KaszinoAct;
import com.example.budapestquest.ActionCards.LepesAct;
import com.example.budapestquest.ActionCards.MunkaAct;
import com.example.budapestquest.ActionCards.AutomataAct;
import com.example.budapestquest.Karakterek.Karakter;
import com.example.budapestquest.Targyak.Targy;
import com.example.budapestquest.akcioKartyak.Kondi;

import java.util.Random;

public class GameController {
    public static final String Version = "1001";

    /*
        Ez mindenhogy debil volt, sry...
        Most legalább már fix, hogy 1 instance lesz belőle, elvégre minek több? Meg minek lenne MainActivityben? Meg minek lenne a class fele static?
        TODO: static class maybe?
    */
    private GameController() {}
    private static final GameController Instance = new GameController();
    public static GameController GetInstance(){
        return Instance;
    }

    public Karakter En = null;

    public Random rand = new Random();

    // MainActivity "tölti ki"
    public TabInventory tabInventory = null;
    public TabStats tabStats = null;
    public Context context = null;

    // A játékra + ránk vonatkozó változók
    public int vonaljegy = 0;
    public int kimaradas = 0;

    /*
     *   Megpróbál pénzt költeni, visszaadja sikerült-e. Ha sikerült akkor le is vonja.
     * */
    public boolean PenztKolt(int osszeg){
        if((En.FT  < osszeg) || (osszeg < 0)) return false;
        En.FT -= osszeg;
        return true;
    }

    /*
    *   Létrehozza a karaktert, egyfajta lateinit.
    * */
    //TODO: a kasztbónuszt lehetne a Karakternél hozzáadni maybe
    public void CreateKarakter(String name, int kasztId, int uniId) {
        En = new Karakter(name, kasztId, uniId, rand.nextInt());

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
            En.FT = 9001;
            vonaljegy = 10;
        }
    }

    /*
    *   QR kódot dolgozza fel.
    * */
    protected void HandleQR(char method, String version, String data) throws Exception{
        Intent intent;

        // Csúnya, de kell
        if(method != QRManager.QR_LEPES && kimaradas > 0){
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
                case QRManager.QR_KONDI: { // kondi (felhasználó választja ki mit akar edzeni/ data: 0 erőnléti, 1 kardio)
                    switch (data.charAt(0)) {
                        case '0':
                            Toast.makeText(context, "Erőnléti edzés", Toast.LENGTH_LONG).show();
                            Kondi.harciEdzes();
                            break;
                        case '1':
                            Toast.makeText(context, "Kardio edzés", Toast.LENGTH_LONG).show();
                            Kondi.kardioEdzes();
                            break;
                        default:
                            throw new Exception("Ismeretlen edzés típus.");
                    }
                }
                break;
                case QRManager.QR_KASZINO: { // kaszinó (felhasználó választja ki mit akar játszani)
                    intent = new Intent(context, KaszinoAct.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
                break;
                case QRManager.QR_LEPES: { // lepes(data tárolja: 1 használ jegyet, 0 nem használ jegyet)
                    if (data.equals(""))
                        throw new Exception("Nincs paraméter.");
                    int lepes = data.charAt(0) - '0';//TODO ?
                    if (lepes < 0 || lepes > 1)
                        throw new Exception("Ismeretlen lépés típus ( " + lepes + " ).");

                    // bliccelni csak akkor tudunk, ha van jegyünk
                    //TODO: esetleg nem itt nézni?
                    if (kimaradas > 0) {
                        kimaradas--;
                        Toast.makeText(context, "Kimaradás csökkentve!", Toast.LENGTH_LONG).show();
                    } else if (lepes == 0 && vonaljegy == 0)
                        Toast.makeText(context, "Nincs vonaljegyed, csak bliccelni tudsz.", Toast.LENGTH_LONG).show();
                    else {
                        intent = new Intent(context, LepesAct.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra("LEPES", lepes);
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

                    int amount = 0;
                    switch (akcio) {

                        case 0:
                            amount = PenzRandom(true);
                            En.FT += amount;
                            Toast.makeText(context, "Találtál " + amount + " forintot a földön! Nyílván van eszed és zsebre tetted.", Toast.LENGTH_LONG).show();
                            break;
                        case 1:
                            amount = PenzRandom(false);
                            En.FT -= amount;
                            Toast.makeText(context, "Elloptak tőled " + amount + " forintot! Többet kéne edzened...", Toast.LENGTH_LONG).show();
                            break;
                    }
                    /*intent = new Intent(context, AkcioAct.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("AKCIO", akcio);
                    context.startActivity(intent);*/
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

    private int PenzRandom(boolean nyer) {
        int nyerMin = 15;
        int nyerMax = 30;
        int vesztMin = 5;
        int vesztMax = 20;

        Random r = new Random();
        int amount = r.nextInt((nyer ? nyerMax - nyerMin : vesztMax - vesztMin)) + (nyer ? nyerMin : vesztMin) + 1;
        return amount;
    }
}
