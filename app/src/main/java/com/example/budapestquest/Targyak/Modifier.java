package com.example.budapestquest.Targyak;

public class Modifier extends Containerable{
    public final String Name;   // Kiírandó név

    // Statok amik a tárgy statjaihoz adódnak hozzá, amik aztán a játékos statjaihoz
    public final int HP;
    public final double DMG;
    public final double DaP;
    public final double DeP;
    public final double CR;
    public final double DO;

    public Modifier(String _Name, double _Weight, int _HP, double _DMG, double _DaP, double _DeP, double _CR, double _DO){
        Name = _Name;
        Weight = _Weight;   // Mekorra súllyal generálódjon random. (általános itemeknek -> nagy, ritka -> kicsi)
        HP = _HP;
        DMG = _DMG;
        DaP = _DaP;
        DeP = _DeP;
        CR = _CR;
        DO = _DO;
    }
}
