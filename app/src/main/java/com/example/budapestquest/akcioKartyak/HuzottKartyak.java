package com.example.budapestquest.akcioKartyak;

import com.example.budapestquest.Karakterek.Karakter;
import com.example.budapestquest.Targyak.Targy;

import java.util.Random;

public class HuzottKartyak
{

    public static int talalVagyVeszitPenzt ()
    {
        return new Random().nextInt(45+15);
    }

    public static Targy talaltTargy ()
    {
        //TODO zsolt random tárgy generáló kódja

        return null;
    }


}