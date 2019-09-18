package com.example.budapestquest.akcioKartyak;

import com.example.budapestquest.GameController;
import com.example.budapestquest.Karakterek.Karakter;

import java.util.Random;

public class Kaszino {
    private static final int pokerszint = 50;
    private static final int pokerwin = 45;
    public static final double pokerodds = 0.45;

    private static final int ruletszint = 30;
    private static final int ruletwin = 25;
    public static final double ruletodds = 0.25;

    private static final int blackszint = 40;
    private static final int blackwin = 35;
    public static final double blackodds = 0.35;

    public static final Random rand = new Random();

    public static void poker(int osszeg) {
        if(isNyer(pokerszint)) {
            GameController.En.XP += pokerwin;
            GameController.En.FT += (int) (osszeg * pokerodds);
        }
        else {
            GameController.En.FT -= osszeg;
        }
    }

    public static void rulett(int osszeg) {
        if(isNyer(ruletszint)) {
            GameController.En.XP += ruletwin;
            GameController.En.FT += (int) (osszeg * ruletodds);
        }
        else {
            GameController.En.FT -= osszeg;
        }
    }

    public static void blackJack(int osszeg) {
        if(isNyer(blackszint)) {
            GameController.En.XP += blackwin;
            GameController.En.FT += (int) (osszeg * blackodds);
        }
        else {
            GameController.En.FT -= osszeg;
        }
    }

    private static boolean isNyer(int meddig) {
        return rand.nextInt(100+1) > meddig;
    }
}
