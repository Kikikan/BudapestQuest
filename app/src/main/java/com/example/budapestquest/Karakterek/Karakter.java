package com.example.budapestquest.Karakterek;

import android.util.Base64;

import com.example.budapestquest.Targyak.Targy;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ByteArrayInputStream;

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

    public int FT = 100;
    public int XP = 0;

    public int      RandFactor = 0;
    public Targy[]  Felszereles = new Targy[4];

    /*
    *   Visszaad egy KarakterStats objectet, ami tartalmazza a karakter, illetve a cuccok statjait összeadva.
    * */
    //TODO: Az itemek statjait mikor és hogy adjuk hozzá a karakterünkéhez? Erre szükség van 1) harcnál 2) stat nézetben 3) esetleg boltban
    public KarakterStats SumStats(){
        KarakterStats s = SumItemStats();

        s.HP  += HP;
        s.DMG += DMG;
        s.DaP += DaP;
        s.DeP += DeP;
        s.CR = Math.max(Math.min(CR, 100.0), 0.0);
        s.DO = Math.max(Math.min(DO, 100.0), 0.0);

        // Megadjuk a statok korlátjait
        //TODO: szebben
        s.HP = Math.max(s.HP, 1000.0);
        s.DMG = Math.max(s.DMG, 0.0);
        s.DaP = Math.max(s.DaP, 0.0);
        s.DeP = Math.max(s.DeP, 0.0);

        return s;
    }

    /*
    *   Visszaad egy KarakterStats objectet, ami csak az itemek statjat tartalmazza összeadva.
    * */
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
    public Karakter(String serializaltData) throws IOException {
        ByteArrayInputStream byteStream = new ByteArrayInputStream(Base64.decode(serializaltData.getBytes(), 0));
        ObjectInputStream objStream = new ObjectInputStream(byteStream);

        // -Meta-
        Name = objStream.readUTF();
        UNI = objStream.readInt();
        KASZT = objStream.readInt();
        RandFactor = objStream.readInt();

        // -Stats-
        FT = objStream.readInt();
        XP = objStream.readInt();

        HP = objStream.readDouble();
        DMG = objStream.readDouble();
        DaP = objStream.readDouble();
        DeP = objStream.readDouble();
        CR = objStream.readDouble();
        DO = objStream.readDouble();

        // -Items-
        for (int i = 0; i < Felszereles.length; i++) {
            int slot, tier, itemid, modifier;
            if ((slot = objStream.readInt()) == -1) {
                objStream.skipBytes(3 * Integer.BYTES);
                continue;
            }
            tier = objStream.readInt();
            itemid = objStream.readInt();
            modifier = objStream.readInt();
            Felszereles[i] = new Targy(slot, tier, itemid, modifier);
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
            objStream.writeInt(XP);

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
}
