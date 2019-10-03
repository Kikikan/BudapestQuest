package com.example.budapestquest.Karakterek;

import com.example.budapestquest.GameController;

import java.io.IOException;

public class LocalKarakter extends Karakter {
    public int vonaljegy = 0;
    public int kimaradas = 0;
    public int elkolthetoXP = 0;

    public LocalKarakter(String _Name, int _UNI, int _KASZT){
        super(_Name, _UNI, _KASZT, GameController.rand.nextInt());
    }

    //TODO: ez így eléggé össze lett gányolva általam (Zsolt)
    public LocalKarakter(String serializaltData) throws IOException {
        super(serializaltData);
    }

    /*
     *   Megpróbál pénzt költeni, visszaadja sikerült-e. Ha sikerült akkor le is vonja.
     * */
    public boolean PenztKolt(int osszeg){
        if((FT  < osszeg) || (osszeg < 0)) return false;
        FT -= osszeg;
        return true;
    }

    /*
    *   Valami szofisztikált LVLUP rendszert alapoz meg a késöbbiekre tekintettel léve. Egyszerőbb lesz majd bevezetni a szinteket, és hogy pl szintenként járjon majd "LVLUP jog"
     * */
    public void GiveXP(int xp){
        XP += xp;
        elkolthetoXP += xp;
    }

    /*
    *   Megpróbál szintet léptetni az adott statból, visszaadja sikerült-e. Ha sikerült, akkor le is vonja az XP-t.
    * */
    public boolean SpendXP(int mennyiseg){
        if((elkolthetoXP < mennyiseg) || (mennyiseg < 0)) return false;
        elkolthetoXP -= mennyiseg;
        return true;
    }

    public boolean DoLVLUP_HP() {
        boolean success;
        if(success = SpendXP(1)) {
            HP += 1*100;
        }return success;
    }

    public boolean DoLVLUP_DMG() {
        boolean success;
        if(success = SpendXP(1)) {
            DMG += 1*10;
        }return success;
    }

    public boolean DoLVLUP_DaP() {
        boolean success;
        if(success = SpendXP(1)) {
            DaP += 1;
        }return success;
    }

    public boolean DoLVLUP_DeP() {
        boolean success;
        if(success = SpendXP(1)) {
            DeP += 1;
        }return success;
    }
}
