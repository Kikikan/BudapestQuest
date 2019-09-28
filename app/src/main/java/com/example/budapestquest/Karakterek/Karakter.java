package com.example.budapestquest.Karakterek;

import android.util.Base64;

import com.example.budapestquest.Targyak.Targy;

import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ByteArrayInputStream;
import java.util.Random;

public class Karakter extends KarakterStats{
    public static final int ELTE_ID = 0;
    public static final int BME_ID = 1;
    public static final int CORVINUS_ID = 2;
    public static final int TE_ID = 3;

    public static final int BUDA_ID = 0;
    public static final int PEST_ID = 1;

    //TODO: enum
    public static String[] EgyetemNevek = new String[]{
            "ELTE",
            "BME",
            "CORVINUS",
            "TE"
    };

    public static String EgyetemIDToString(int id){
        if(id < 0 || id > EgyetemNevek.length - 1)
            throw new IllegalArgumentException("Ismeretlen egyetem.");
        return EgyetemNevek[id];
    }

    public String EgyetemToString(){
        return EgyetemIDToString(UNI);
    }

    public static String[] KasztNevek = new String[]{
            "Buda",
            "Pest"
    };

    public static String KasztIDToString(int id){
        if(id < 0 || id > KasztNevek.length - 1)
            throw new IllegalArgumentException("Ismeretlen kaszt.");
        return KasztNevek[id];
    }

    public String KasztToString(){
        return KasztIDToString(KASZT);
    }

    public final String   Name;
    public final int      UNI;
    public final int      KASZT;

    public Karakter(String _Name, int _UNI, int _KASZT, int _SEED){
        Name = _Name;
        UNI = _UNI;
        KASZT = _KASZT;
        RandFactor = _SEED;
    }

    public int      FT = 100;
    public double   XP = 0;

    public int      RandFactor = 0;
    public Targy[]  Felszereles = new Targy[4];

    //TODO: Az itemek statjait mikor és hogy adjuk hozzá a karakterünkéhez? Erre szükség van 1) harcnál 2) stat nézetben 3) esetleg boltban
    public KarakterStats SumStats(){
        KarakterStats s = SumItemStats();

        s.HP  += HP;
        s.DMG += DMG;
        s.DaP += DaP;
        s.DeP += DeP;
        s.CR = Math.max(Math.min(CR, 1), 0.0);
        s.DO = Math.max(Math.min(DO, 1), 0.0);

        // Megadjuk a statok korlátjait
        //TODO: szebben
        s.HP = Math.max(s.HP, 100.0);
        s.DMG = Math.max(s.DMG, 0.0);
        s.DaP = Math.max(Math.min(s.DaP, 5000.0), 0.0);
        s.DeP = Math.max(Math.min(s.DeP, 5000.0), 0.0);

        return s;
    }

    public KarakterStats SumItemStats(){
        KarakterStats s = new KarakterStats();

        for(int i = 0; i < 4; i++){
            if(Felszereles[i] == null) continue;
            s.HP  += Felszereles[i].SumHP();
            s.DMG += Felszereles[i].SumDMG();
            s.DaP += Felszereles[i].SumDaP();
            s.DeP += Felszereles[i].SumDeP();
        }

        return s;
    }

    /*
    *   A beolvasott, serializált adatot konvertálja át egy karakter objektummá.
    * */
    public static Karakter Deserialize(String data){
        try {
            ByteArrayInputStream byteStream = new ByteArrayInputStream(Base64.decode(data.getBytes(), 0));
            ObjectInputStream objStream = new ObjectInputStream(byteStream);

            String Name;
            int UNI, KASZT, SEED;

            // -Meta-
            Name = objStream.readUTF();
            UNI = objStream.readInt();
            KASZT = objStream.readInt();
            SEED = objStream.readInt();

            Karakter k = new Karakter(Name, UNI, KASZT, SEED);

            // -Stats-
            k.FT = objStream.readInt();
            k.XP = objStream.readDouble();

            k.HP = objStream.readDouble();
            k.DMG = objStream.readDouble();
            k.DaP = objStream.readDouble();
            k.DeP = objStream.readDouble();
            k.CR = objStream.readDouble();
            k.DO = objStream.readDouble();

            // -Items-
            for (int i = 0; i < k.Felszereles.length; i++) {
                int slot, tier, itemid, modifier;
                if ((slot = objStream.readInt()) == -1) {
                    objStream.skipBytes(3);
                    continue;
                }
                tier = objStream.readInt();
                itemid = objStream.readInt();
                modifier = objStream.readInt();
                k.Felszereles[i] = new Targy(slot, tier, itemid, modifier);
            }

            return k;
        }catch (Exception e){
            return null;
        }
    }

    /*
    *   A karakter objektumot konvertálja át Stringbe, hogy késöbb tárolni / küldeni tudjuk.
    * */
    public String Serialize() {
        try {
            ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
            ObjectOutputStream objStream = new ObjectOutputStream(byteStream);

            // -Meta-
            objStream.writeUTF(Name);
            objStream.writeInt(UNI);
            objStream.writeInt(KASZT);
            objStream.writeInt(RandFactor);

            // -Stats-

            //Ezek azért mennek át, mert esetlegesen késöbb lehetne őket használni csatában?
            objStream.writeInt(FT);
            objStream.writeDouble(XP);

            objStream.writeDouble(HP);
            objStream.writeDouble(DMG);
            objStream.writeDouble(DaP);
            objStream.writeDouble(DeP);
            objStream.writeDouble(CR);
            objStream.writeDouble(DO);

            // -Items-
            for(int i = 0; i < Felszereles.length; i++){
                if(Felszereles[i] == null) {
                    objStream.writeInt(-1);
                    objStream.writeInt(-1);
                    objStream.writeInt(-1);
                    objStream.writeInt(-1);
                }else{
                    objStream.writeInt(Felszereles[i].Slot);
                    objStream.writeInt(Felszereles[i].Tier);
                    objStream.writeInt(Felszereles[i].ItemID);
                    objStream.writeInt(Felszereles[i].ModifierID);
                }
            }

            objStream.flush();
            String ret = Base64.encodeToString(byteStream.toByteArray(), 0);
            return ret;
        }catch (Exception e){
            return null;
        }
    }

    //Azt csinálja, hogy a meglévő tárgyat kicseréli arra a tárgyra amit váltózóként megadunk neki
    //I hope értehtő vagyok :D
    //Mindenkinek szép kódolást :D
    /*
    public void targyCsere (Targy targy)
    {
        for(int i = 0; i < Felszereles.length; i++)
        {
            if(Felszereles[i].Slot == targy.Slot)
            {
                Felszereles[i] = targy;
                break;
            }
        }
    }

    public void arenaBajnok()
    {
        XP += 5;
        FT += 100;
    }
    */
}
