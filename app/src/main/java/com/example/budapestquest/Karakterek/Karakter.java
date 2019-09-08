package com.example.budapestquest.Karakterek;

import com.example.budapestquest.Targyak.Targy;

public class Karakter {

    public String Name = "";
    public int LVL = 0;
    public float XP = 0.0f;    // Ákos dokumentumából kiszedett statok
    public int FT = 1000;
    public float HP = 100.0f;
    public float DMG = 10.0f;
    public float DaP = 0.0f;
    public float DeP = 0.0f;
    public float CR = 0.0f;
    public float DO = 0.0f;

    public Targy[] Felszereles = new Targy[4];  // 0 - fej, 1 - mellkas, 2 - láb, 3 - fegyver

    public String GenerateCode() {
        return Name + "|" + LVL + "|" + HP + "|" + DMG + "|" + DaP + "|" + DeP + "|" + CR + "|" + DO; //Todo: Felszerelés továbbítása
    }
}
