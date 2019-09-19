package com.example.budapestquest.Karakterek;

import android.util.Base64;

import com.example.budapestquest.Targyak.Targy;

import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ByteArrayInputStream;
import java.util.Random;

public class Karakter {
    public static final int ELTE_ID = 0;
    public static final int BME_ID = 1;
    public static final int CORVINUS_ID = 2;
    public static final int TE_ID = 3;

    public static String[] EgyetemNevek = new String[]{
            "ELTE",
            "BME",
            "CORVINUS",
            "TE"
    };

    public static String EgyetemIDToString(int id){
        if(id < 0 || id > 3)
            throw new IllegalArgumentException("Ismeretlen egyetem.");
        return EgyetemNevek[id];
    }

    public String   Name = "";
    public int      UNI = 0;
    public double   XP = 0;
    public int      FT = 100;
    public int      Vonaljegy = 0;
    public int      korbolKimaradas = 0;

    // Statok
    public double   HP = 100;
    public double   DMG = 10;
    public double   DaP = 0;
    public double   DeP = 0;
    public double   CR = 0.05;
    public double   DO = 0.05;

    public int      RandFactor = new Random().nextInt();

    public Targy[] Felszereles = new Targy[4];

    //TODO: Az itemek statjait mikor és hogy adjuk hozzá a karakterünkéhez? Erre szükség van 1) harcnál 2) stat nézetben 3) esetleg boltban

    /*
    *   A beolvasott, serializált adatot konvertálja át egy karakter objektummá.
    * */
    public static Karakter Deserialize(String data){
        try {
            ByteArrayInputStream byteStream = new ByteArrayInputStream(Base64.decode(data.getBytes(), 0));
            ObjectInputStream objStream = new ObjectInputStream(byteStream);

            Karakter k = new Karakter();

            k.Name = objStream.readUTF();
            k.RandFactor= objStream.readInt();
            k.UNI = objStream.readInt();

            //k.XP = objStream.readDouble();
            //k.FT = objStream.readInt();
            //k.Vonaljegy = objStream.readInt();

            k.HP = objStream.readDouble();
            k.DMG = objStream.readDouble();
            k.DaP = objStream.readDouble();
            k.DeP = objStream.readDouble();
            k.CR = objStream.readDouble();
            k.DO = objStream.readDouble();

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

            objStream.writeUTF(Name);
            objStream.writeInt(RandFactor);
            objStream.writeInt(UNI);

            //objStream.writeDouble(XP);
            //objStream.writeInt(FT);
            //objStream.writeInt(Vonaljegy);

            objStream.writeDouble(HP);
            objStream.writeDouble(DMG);
            objStream.writeDouble(DaP);
            objStream.writeDouble(DeP);
            objStream.writeDouble(CR);
            objStream.writeDouble(DO);

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

    public void jegyvasarlas (int db)
    {
        int vonaljegyara = 50;
        FT -= vonaljegyara * db;
        Vonaljegy += db;
    }

    public boolean lepes(boolean vonaljegyHasznalata)
    {
        if(korbolKimaradas != 0)
        {
            korbolKimaradas--;

            return false;
        }

        if(vonaljegyHasznalata)
        {
            Vonaljegy--;
        }
        else
        {
            int random = new Random().nextInt(100+1);

            if(random <= 25)
            {
                if(FT >= 60)
                {
                    FT -= 60;
                }
                else
                {
                    korbolKimaradas -= 1;
                    return false;
                }

            }
        }

        return true;
    }

    public void  harciEdzes ()
    {
        int belepo = 160;

        FT -= belepo;

        DMG += 2;
        DaP += 2;
        CR += 2;
    }

    public void kardioEdzes ()
    {
        int belepo = 160;

        FT -= belepo;
        HP += 2;
        DeP += 2;
        DO += 2;
    }

    public void munka (int db)
    {
        korbolKimaradas += db;

        FT += 15 * db;
    }

    //Azt csinálja, hogy a meglévő tárgyat kicseréli arra a tárgyra amit váltózóként megadunk neki
    //I hope értehtő vagyok :D
    //Mindenkinek szép kódolást :D
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
}
