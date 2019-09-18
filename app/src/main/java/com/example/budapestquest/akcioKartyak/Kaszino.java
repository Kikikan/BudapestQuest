package com.example.budapestquest.akcioKartyak;

import com.example.budapestquest.Karakterek.Karakter;

import java.util.Random;

public class Kaszino
{
    private static final int pokerszint = 50;
    private static final int pokerwin = 45;
    public static final double pokerodds = 0.45;

    private static final int ruletszint = 30;
    private static final int ruletwin = 25;
    public static final double ruletodds = 0.25;


    private static final int blackszint = 40;
    private static final int blackwin = 35;
    public static final double blackodds = 0.35;





    public static Karakter poker(Karakter en, int osszeg)
    {
        if(isNyer(en, pokerszint))
        {
            en.FT += (int) (osszeg * pokerodds);
        }
        else
        {
            en.FT -= osszeg;
        }

        return en;
    }

    public static Karakter rulett(Karakter en, int osszeg)
    {

        if(isNyer(en, ruletszint))
        {
            en.FT += (int) (osszeg * ruletodds);
        }
        else
        {
            en.FT -= osszeg;
        }

        return en;
    }

    public static Karakter blackJack(Karakter en, int osszeg)
    {
        if(isNyer(en, blackszint))
        {
            en.FT += (int) (osszeg * blackodds);
        }
        else
        {
            en.FT -= osszeg;
        }
        return en;
    }

    private static boolean isNyer(Karakter en, int meddig)
    {
        int random = new Random().nextInt(100+1);

        return random > meddig;
    }

}
