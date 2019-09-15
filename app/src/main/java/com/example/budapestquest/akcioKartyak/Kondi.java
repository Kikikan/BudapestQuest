package com.example.budapestquest.akcioKartyak;

import com.example.budapestquest.Karakterek.Karakter;

public class Kondi
{
    public static Karakter harciEdzes(Karakter en)
    {
        int belepo = 100;

        en.FT -= belepo;

        //TODO KÉRDÉS (optimalizálok): Ha 40 pontja van akkor melyikhez tartozon?
        if(en.DaP < 40)
        {
            en.DaP += 3;
        }
        else
        {
            en.DaP += 2;
        }

        return en;
    }

    public static Karakter kardioEdzes (Karakter en)
    {
        //TODO KÉRDÉS (optimalizálok): 1 egység legyen az ára?
        int belepo = 100;

        en.FT -= belepo;


        if(en.DeP < 40)
        {
            en.DeP += 6;
        }
        else
        {
            en.DeP += 4;
        }

        return en;
    }

    public static Karakter vegyesEdzes (Karakter en)
    {
        int belepo = 180;

        en.FT -= belepo;


        if(en.DeP < 40)
        {
            en.DeP += 6;
        }
        else
        {
            en.DeP += 4;
        }

        if(en.DaP < 40)
        {
            en.DaP += 3;
        }
        else
        {
            en.DaP += 2;
        }

        return en;
    }
}
