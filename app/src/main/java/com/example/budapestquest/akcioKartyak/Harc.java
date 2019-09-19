package com.example.budapestquest.akcioKartyak;

import com.example.budapestquest.GameController;
import com.example.budapestquest.Karakterek.Karakter;

import java.util.Random;

public class Harc {


    //TODO: Kiírni a harc logot
    public static void SortKiir(String str){
        return;
    }

    /*
     * Leszimulál egy kört.
     * Visszaadja, hogy a támadó mekkora sebzést vitt be a védekezőnek
     * */
    public static int SimulateKor(Karakter tamado, Karakter vedekezo, Random rand, int kor){
        SortKiir(kor + ". kör, " + tamado.Name + " támad:");

        if (rand.nextDouble() < vedekezo.DO) {
            SortKiir(">>>>>>" + vedekezo.Name + " dodge-olt.");
        } else {
            if (rand.nextDouble() < tamado.CR) {
                SortKiir(">>>> " + tamado.Name + " kritelt");
                return (int) (((100 - vedekezo.DeP) / 100) * ((100 + tamado.DMG) / 100) * 2 * tamado.DMG);
            } else {
                return (int) (((100 - vedekezo.DeP) / 100) * ((100 + tamado.DMG) / 100) * tamado.DMG);
            }
        }
        return 0;
    }

    // Visszaadja, hogy győztünk-e
    public static boolean Fight(Karakter enemy, boolean enkezdek){
        Karakter en = GameController.En;

        double HPen = en.HP, HPenemy = enemy.HP;
        int kor = 1;

        // Közös random
        Random rand = new Random(en.RandFactor ^ enemy.RandFactor);
        en.RandFactor = new Random().nextInt();

        if(enkezdek)
            if((HPenemy -= SimulateKor(en, enemy, rand, kor++)) < 0) return true;
        while (true){
            if((HPen    -= SimulateKor(enemy, en, rand, kor++)) < 0) return false;
            if((HPenemy -= SimulateKor(en, enemy, rand, kor++)) < 0) return true;
        }
    }
}
