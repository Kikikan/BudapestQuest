package com.example.budapestquest.akcioKartyak;

import com.example.budapestquest.Karakterek.Karakter;

import java.util.Random;

public class Kaszino
{
    private static final int pokerszint = 50;
    private static final int pokerwin = 45;
    private static final double pokerodds = 0.45;

    private static final int ruletszint = 30;
    private static final int ruletwin = 25;
    private static final double ruletodds = 0.25;


    private static final int blackszint = 40;
    private static final int blackwin = 35;
    private static final double blackodds = 0.35;



   /* public static Karakter belepAKaszinoba(Karakter en)
    {

        for(int kor = 0; kor < 3; kor++)
        {
            int valaszt = 0;

            switch(valaszt)
            {
                case 0: //poker
                    poker(en);
                    break;

                case 1: //rulett
                    rulett(en);
                    break;

                case 2: //blackJack
                    blackJack(en);
                    break;
            }
        }

        return en;
    }*/

    private static Karakter poker(Karakter en, int osszeg)
    {
        if(isNyer(en, pokerszint))
        {
            en.XP += pokerwin;
            en.FT += (int) (osszeg * pokerodds);
        }
        else
        {
            en.FT -= osszeg;
        }

        return en;
    }

    private static Karakter rulett(Karakter en, int osszeg)
    {

        if(isNyer(en, ruletszint))
        {
            en.XP += ruletwin;
            en.FT += (int) (osszeg * ruletodds);
        }
        else
        {
            en.FT -= osszeg;
        }

        return en;
    }

    private static Karakter blackJack(Karakter en, int osszeg)
    {
        if(isNyer(en, blackszint))
        {
            en.XP += blackwin;
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
