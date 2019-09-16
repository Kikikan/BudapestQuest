package com.example.budapestquest.ui.main;
import com.example.budapestquest.GameController;
import com.example.budapestquest.Karakterek.Karakter;

public class FightController {
    public Karakter En = null;

    public Karakter fight (Karakter ellenfel){
        Karakter gyoztes = GameController.En;
        double actHP = GameController.En.HP;
        double eActHP = ellenfel.HP;

        int kor = 1;
        Karakter tamado = En;
        Karakter vedekezo = ellenfel;
        while (actHP > 0 && eActHP > 0) {
            if (kor % 2 == 0) {
                tamado = ellenfel;
                vedekezo = tamado;
            }
            System.out.println(kor + ". kör, " + tamado.Name + " támad:");

            double random1 = Math.random()*100.0;
            double random2 = Math.random()*100.0;

            if (random1 < vedekezo.CR) {
                System.out.println(">>>>>>" + vedekezo.Name + " dodge-olt.");
            } else {
                if (random2 < tamado.CR) {
                    System.out.println(">>>> " + tamado.Name + " kritelt");
                    eActHP -= (((100 - vedekezo.DeP) / 100) * ((100 + tamado.DMG) / 100) * 2 * tamado.DMG);
                } else {
                    eActHP -= (((100 - vedekezo.DeP) / 100) * ((100 + tamado.DMG) / 100) * tamado.DMG);
                }

            }
            // System.out.println(támadó.acthp+" - "+védekező.acthp);
            kor++;
        }
        if (actHP <= 0) {
            gyoztes = ellenfel;
        }

        return gyoztes;
    }

}
