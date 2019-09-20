package com.example.budapestquest.Targyak;

public class Item extends Containerable{
    public final String Name;   // Kiírandó név
    public final String Desc;   // Kiírandó leírás

    public final int Price;

    // Statok amiket a játékos statjaihoz hozzáadunk
    public final double HP;
    public final double DMG;
    public final double DaP;
    public final double DeP;

    public Item(String _Name, String _Desc, double _Weight, int _Price, double _HP, double _DMG, double _DaP, double _DeP){
        Name = _Name;
        Desc = _Desc;
        Weight = _Weight;           // Mekorra súllyal generálódjon random. (általános itemeknek -> nagy, ritka -> kicsi)
        Price = _Price;             // A boltban milyen drága legyen egy tárgy.
        HP = _HP;
        DMG = _DMG;
        DaP = _DaP;
        DeP = _DeP;
    }
}
