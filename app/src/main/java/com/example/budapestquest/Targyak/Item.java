package com.example.budapestquest.Targyak;

public class Item extends Containerable{
    public final String Name;   // Kiírandó név
    public final String Desc;   // Kiírandó leírás

    // Statok amiket a játékos statjaihoz hozzáadunk
    public final int HP;
    public final double DMG;
    public final double DaP;
    public final double DeP;
    public final double CR;
    public final double DO;

    public Item(String _Name, String _Desc, double _Weight, int _HP, double _DMG, double _DaP, double _DeP, double _CR, double _DO){
        Name = _Name;
        Desc = _Desc;
        Weight = _Weight;   // Mekorra súllyal generálódjon random. (általános itemeknek -> nagy, ritka -> kicsi)
        HP = _HP;
        DMG = _DMG;
        DaP = _DaP;
        DeP = _DeP;
        CR = _CR;
        DO = _DO;
    }
}
