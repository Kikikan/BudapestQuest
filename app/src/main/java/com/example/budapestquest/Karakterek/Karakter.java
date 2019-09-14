package com.example.budapestquest.Karakterek;

import android.util.Base64;

import com.example.budapestquest.Targyak.Targy;

import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ByteArrayInputStream;

public class Karakter implements java.io.Serializable {
    public String   Name = "";

    //TODO: A UNI lehetne egy ENUM és/vagy lehetne egy függvény ami a UNI értékét Stringgé konvertálja, ugyanis pl a harcnál is kiírhatnánk az ellenfél egyetemét.
    public int      UNI = 0;
    public double   XP = 0;
    public int      FT = 100;
    public double   HP = 100;
    public double   DMG = 10;
    public double   DaP = 0;
    public double   DeP = 0;
    public double   CR = 0.05;
    public double   DO = 0.05;

    public Targy    Fej = null;
    public Targy    Mellkas = null;
    public Targy    Lab = null;
    public Targy    Fegyver = null;

    // transient = nem fog serializálódni
    transient int hashCache = 0;

    /*
    *   A QRkódból beolvasott, serializált adatot konvertálja át egy karakter objektummá.
    * */
    public static Karakter Deserialize(String data){
        try{
            ByteArrayInputStream byteStream = new ByteArrayInputStream(Base64.decode(data.getBytes(),0));
            ObjectInputStream objStream = new ObjectInputStream(byteStream);
            return (Karakter) objStream.readObject();
        }catch (Exception e){
            return null;
        }
    }

    /*
    *   Java-ban van beépített serialize, így egyszerűbben átkerülnek a 'custom' tárgyak.
    *   De ha mondjuk a tárgyaknak fix statjaik lennének, akkor hivatkozhatnánk rájuk egy ID-val is.
    * */
    public String Serialize() {
        try {
            ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
            ObjectOutputStream objStream = new ObjectOutputStream(byteStream);
            objStream.writeObject(this);
            objStream.flush();
            String ret = Base64.encodeToString(byteStream.toByteArray(), 0);
            hashCache = ret.hashCode();
            return ret;
        }catch (Exception e){
            return null;
        }
    }
}
