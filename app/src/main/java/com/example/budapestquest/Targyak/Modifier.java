package com.example.budapestquest.Targyak;

public class Modifier extends Containerable{
    public final String Name;   // Kiírandó név
    public final double PriceWeight;

    public final int    Color;

    // Statok amik a tárgy statjaihoz adódnak hozzá, amik aztán a játékos statjaihoz
    public final double HP;
    public final double DMG;
    public final double DaP;
    public final double DeP;

    public Modifier(String _Name, double _Weight, double _PriceWeight, int _Color, double _HP, double _DMG, double _DaP, double _DeP){
        Name = _Name;
        Weight = _Weight;               // Mekorra súllyal generálódjon random. (általános itemeknek -> nagy, ritka -> kicsi)
        PriceWeight = _PriceWeight;     // A boltban milyen drága legyen egy tárgy (olcsó -> kicsi, drága -> nagy)
        Color = _Color;
        HP = _HP;
        DMG = _DMG;
        DaP = _DaP;
        DeP = _DeP;
    }
}
