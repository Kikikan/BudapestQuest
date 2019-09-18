package com.example.budapestquest.akcioKartyak;

import com.example.budapestquest.Karakterek.Karakter;

public class Kondi
{
    public static Karakter harciEdzes(Karakter en)
    {
        int belepo = 150;

        en.FT -= belepo;

        en.DaP += 2;

        return en;
    }

    public static Karakter kardioEdzes (Karakter en)
    {
        int belepo = 150;

        en.FT -= belepo;

        en.DeP += 2;

        return en;
    }

}
