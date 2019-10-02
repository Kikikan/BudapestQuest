package com.example.budapestquest.Karakterek;

import com.example.budapestquest.GameController;

import java.io.IOException;
import android.view.View;

public class LocalKarakter extends Karakter {
    public int vonaljegy = 0;
    public int kimaradas = 0;

    public double elkoltotXP = 0;

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
    *   Megpróbál szintet léptetni az adott statból, visszaadja sikerült-e. Ha sikerült, akkor le is vonja az XP-t.
    * */
    public boolean XPtKolt(int mennyiseg){
        if(((XP-elkoltotXP) < mennyiseg) || (mennyiseg < 0)) return false;
        elkoltotXP += mennyiseg;
        return true;

    }



    public void ButtonlvlUPHP(View v)
    {
        if(XPtKolt(1))
        {
            HP += 1*100;
        }
        else
        {
            //NINCS ELÉG XP-éd
        }
    }

    public void ButtonlvlUPDMG(View v)
    {
        if(XPtKolt(1))
        {
            DMG += 1*10;
        }
        else
        {
            //NINCS ELÉG XP-éd
        }
    }

    public void ButtonlvlUPDaP(View v)
    {
        if(XPtKolt(1))
        {
            DaP += 1;
        }
        else
        {
            //NINCS ELÉG XP-éd
        }
    }

    public void ButtonlvlUPDeP(View v) {
        if(XPtKolt(1)) {
            DeP += 1;
        }
        else {
            //NINCS ELÉG XP-éd
        }
    }

    /*
    public void levelUPCR()
    {
        if(GameController.En.XP >= 1)
        {
            GameController.En.CR += 1;
            GameController.En.XP -= 1;
        }
        else
        {
            //NINCS ELÉG XP-éd
        }
    }

    public void levelUPDO()
    {
        if(GameController.En.XP >= 1)
        {
            GameController.En.DO += 1;
            GameController.En.XP -= 1;
        }
        else
        {
            //NINCS ELÉG XP-éd
        }
    }*/

}
