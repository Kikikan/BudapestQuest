package com.example.budapestquest.akcioKartyak;

import com.example.budapestquest.GameController;
import com.example.budapestquest.Karakterek.Karakter;

//SZAR
//Karakter-ben benne van
public class Kondi{
    private static final int belepo_harci = 100;
    private static final int belepo_kardio = 100;
    private static final int belepo_vegyes = 100;

    public void harciEdzes() {
        GameController.En.FT -= belepo_harci;

        //TODO KÉRDÉS (optimalizálok): Ha 40 pontja van akkor melyikhez tartozon?
        if(GameController.En.DaP < 40) {
            GameController.En.DaP += 3;
        }
        else {
            GameController.En.DaP += 2;
        }
    }

    public void kardioEdzes() {
        //TODO KÉRDÉS (optimalizálok): 1 egység legyen az ára?
        GameController.En.FT -= belepo_kardio;

        if(GameController.En.DeP < 40) {
            GameController.En.DeP += 6;
        }
        else {
            GameController.En.DeP += 4;
        }
    }

    public void vegyesEdzes() {
        GameController.En.FT -= belepo_vegyes;

        if(GameController.En.DeP < 40) {
            GameController.En.DeP += 6;
        }
        else {
            GameController.En.DeP += 4;
        }

        if(GameController.En.DaP < 40) {
            GameController.En.DaP += 3;
        }
        else {
            GameController.En.DaP += 2;
        }
    }
}
