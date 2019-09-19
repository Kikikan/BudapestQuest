package com.example.budapestquest.akcioKartyak;

import com.example.budapestquest.GameController;
import com.example.budapestquest.Karakterek.Karakter;

//SZAR
//Karakter-ben benne van
public class Kondi{
    private static final int belepo_harci = 160;
    private static final int belepo_kardio = 160;

    public void harciEdzes() {
        GameController.En.FT -= belepo_harci;

        GameController.En.DMG += 2*10;
        GameController.En.DaP += 2;
        GameController.En.CR += 2;
    }

    public void kardioEdzes() {
        GameController.En.FT -= belepo_kardio;

        GameController.En.HP += 2*100;
        GameController.En.DeP += 2;
        GameController.En.DO += 2;
    }
}
