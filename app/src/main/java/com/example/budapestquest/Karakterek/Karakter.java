package com.example.budapestquest.Karakterek;

import android.util.Base64;

import com.example.budapestquest.Targyak.Targy;

import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ByteArrayInputStream;

public class Karakter {
    public static final int ELTE_ID = 0;
    public static final int BME_ID = 1;
    public static final int CORVINUS_ID = 2;
    public static final int TE_ID = 3;

    public static String EgyetemIDToString(int id){
        switch (id){
            case ELTE_ID: return "ELTE";
            case BME_ID: return "BME";
            case CORVINUS_ID: return "CORVINUS";
            case TE_ID: return "TE";
            default: return "";
        }
    }

    public String   Name = "";
    public int      UNI = 0;
    public double   XP = 0;
    public int      FT = 100;
    public int      HP = 100;
    public int      Vonaljegy = 0;
    public double   DMG = 10;
    public double   DaP = 0;
    public double   DeP = 0;
    public double   CR = 0.05;
    public double   DO = 0.05;

    public double   actHP = 100.0f;

    public Targy[] Felszereles = new Targy[4];

    public int hashCache = 0;

    /*
    *   A beolvasott, serializált adatot konvertálja át egy karakter objektummá.
    * */
    public static Karakter Deserialize(String data){
        try {
            ByteArrayInputStream byteStream = new ByteArrayInputStream(Base64.decode(data.getBytes(), 0));
            ObjectInputStream objStream = new ObjectInputStream(byteStream);

            Karakter k = new Karakter();

            k.Name = objStream.readUTF();
            k.UNI = objStream.readInt();
            k.XP = objStream.readDouble();
            k.FT = objStream.readInt();
            k.HP = objStream.readInt();
            k.Vonaljegy = objStream.readInt();
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
            objStream.writeInt(UNI);
            objStream.writeDouble(XP);
            objStream.writeInt(FT);
            objStream.writeInt(HP);
            objStream.writeInt(Vonaljegy);
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
            hashCache = ret.hashCode();
            return ret;
        }catch (Exception e){
            return null;
        }
    }
}
