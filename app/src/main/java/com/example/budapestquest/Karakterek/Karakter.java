package com.example.budapestquest.Karakterek;

import com.example.budapestquest.Targyak.Targy;

public class Karakter {

    public String Name = "";
    public int UNI = 0;
    public double XP = 0;
    public int FT = 100;
    public double HP = 100;
    public double DMG = 10;
    public double DaP = 0;
    public double DeP = 0;
    public double CR = 0.05;
    public double DO = 0.05;
    double actHP;

    public Targy[] Felszereles = new Targy[4];  // 0 - fej, 1 - mellkas, 2 - láb, 3 - fegyver

    public String GenerateCode() {
        return Name + "|" + HP + "|" + DMG + "|" + DaP + "|" + DeP + "|" + CR + "|" + DO; //Todo: Felszerelés továbbítása
    }
}
