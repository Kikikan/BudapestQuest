package com.example.budapestquest.Karakterek;

import com.example.budapestquest.GameController;

import java.io.IOException;

public class LocalKarakter extends Karakter {
    public int vonaljegy = 0;
    public int kimaradas = 0;

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
        if((XP < mennyiseg) || (mennyiseg < 0)) return false;
        XP -= mennyiseg;
        return true;
    }

    /*

    public void levelUPHP(View v)
    {
        if(GameController.En.XP >= 1)
        {
            GameController.En.HP += 1*100;
            GameController.En.XP -= 1;
        }
        else
        {
            //NINCS ELÉG XP-éd
        }
    }

    public void levelUPDMG()
    {
        if(GameController.En.XP >= 1)
        {
            GameController.En.DMG += 1*10;
            GameController.En.XP -= 1;
        }
        else
        {
            //NINCS ELÉG XP-éd
        }
    }

    public void levelUPDaP()
    {
        if(GameController.En.XP >= 1)
        {
            GameController.En.DaP += 1;
            GameController.En.XP -= 1;
        }
        else
        {
            //NINCS ELÉG XP-éd
        }
    }

    public void levelUPDeP()
    {
        if(GameController.En.XP >= 1)
        {
            GameController.En.DeP += 1;
            GameController.En.XP -= 1;
        }
        else
        {
            //NINCS ELÉG XP-éd
        }
    }

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
    }
    */
}
