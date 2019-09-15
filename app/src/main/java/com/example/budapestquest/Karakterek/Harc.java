package com.example.budapestquest.Karakterek;

public class Harc {

    //TODO: Az actHP-t szerintem függvényen belül kéne tárolni, mivel csak itt használatos.

    public Karakter fight(Karakter a,Karakter b){
        Karakter gyoztes = a;

        int kor = 1;
        Karakter tamado = a;
        Karakter vedekezo = b;
        while (a.actHP > 0 && b.actHP > 0) {
            if (kor % 2 == 0) {
                tamado = b;
                vedekezo = a;
            }
            System.out.println(kor + ". kör, " + tamado.Name + " támad:");

            double random1 = Math.random();
            double random2 = Math.random();

            if (Math.round(random1 * 100.0) < vedekezo.CR) {
                System.out.println(">>>>>>" + vedekezo.Name + " dodge-olt.");
            } else {
                if (Math.round(random2 * 100.0) < tamado.CR) {
                    System.out.println(">>>> " + tamado.Name + " kritelt");
                    vedekezo.actHP -= (int) (((100 - vedekezo.DeP) / 100) * ((100 + tamado.DMG) / 100) * 2 * tamado.DMG);
                } else {
                    vedekezo.actHP -= (int) (((100 - vedekezo.DeP) / 100) * ((100 + tamado.DMG) / 100) * tamado.DMG);
                }

            }
            // System.out.println(támadó.acthp+" - "+védekező.acthp);
            kor++;
        }
        if (a.actHP <= 0) {
            gyoztes = b;
        }

        return gyoztes;
    }
}
