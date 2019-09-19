package com.example.budapestquest.Targyak;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class Targy {
    public static final int FEJ_ID = 0;
    public static final int MELLKAS_ID = 1;
    public static final int LAB_ID = 2;
    public static final int FEGYVER_ID = 3;

    public static final int TIER_1 = 0;
    public static final int TIER_2 = 1;
    public static final int TIER_3 = 2;

    public final int Slot;
    public final int Tier;
    public final int ItemID;
    public final int ModifierID;

    // Habár ezek itt redundánsak, az ID-ket célszerű tárolni a serializáció miatt, ezeket meg azért, hogy nem kelljen mindig tömbökben turkálni.
    public final Item item;
    public final Modifier modifier;

    public Targy(int _Slot, int _Tier, int _ItemID, int _ModifierID){
        if(_Slot < 0 || _Slot > 3)
            throw new IllegalArgumentException("Nem létező Slot.");
        if(_Tier < 0 || _Tier > 2)
            throw new IllegalArgumentException("Nem létező Tier.");

        Slot = _Slot;
        Tier = _Tier;

        ItemID = _ItemID;
        ModifierID = _ModifierID;

        if((item = GetItem(Slot, Tier, ItemID)) == null)
            throw new IllegalArgumentException("Nem létező Item.");
        if((modifier = GetModifier(Slot, ModifierID)) == null)
            throw new IllegalArgumentException("Nem létező Modifier.");
    }

    public static WeightedContainer<Item> GetItemContainer(int Slot, int Tier){
        if((Slot < 0 || Slot > 3) || (Tier< 0 || Tier > 2))
            throw new IllegalArgumentException("Nem létező Slot / Tier.");
        return ItemSchema[Slot][Tier];
    }

    public static WeightedContainer<Modifier> GetModifierContainer(int Slot){
        if(Slot < 0 || Slot > 3)
            throw new IllegalArgumentException("Nem létező Slot.");
        return Modifiers[(Slot / 3)];
    }

    public double GetRarity(){
        return GetItemRarity() * GetModifierRarity();
    }

    public double GetItemRarity(){
        WeightedContainer<Item> t = GetItemContainer(Slot, Tier);
        return t.Items[ItemID].Weight / t.CalculatedWeight;
    }

    public double GetModifierRarity(){
        WeightedContainer<Modifier> m = GetModifierContainer(Slot);
        return m.Items[ModifierID].Weight / m.CalculatedWeight;
    }

    public static Item GetItem(int Slot, int Tier, int ItemID){
        WeightedContainer<Item> c = GetItemContainer(Slot, Tier);
        if(c == null)
            throw new IllegalArgumentException("Nem létező Slot / Tier.");
        if(ItemID < 0 || ItemID > c.Items.length)
            throw new IllegalArgumentException("Nem létező Tárgy.");
        return c.Items[ItemID];
    }

    public static Modifier GetModifier(int Slot, int ModifierID){
        WeightedContainer<Modifier> m = GetModifierContainer(Slot);
        if(m == null)
            throw new IllegalArgumentException("Nem létező Modifier.");
        if(ModifierID < 0 || ModifierID > m.Items.length)
            throw new IllegalArgumentException("Nem létező Modifier.");
        return m.Items[ModifierID];
    }

    //TODO: Ezt itt alul mind helyrerakni + feltölteni contenttel
    //TODO: Esetleg valami adatbázisba kiszervezni az egészet, amit aztán lehet majd frissíteni?
    public static final WeightedContainer<Item>[][] ItemSchema = new WeightedContainer[][]{
            /* FEJ ITEMEK */
            new WeightedContainer[]{
                    new WeightedContainer<>(new Item[]{
                            new Item("Hálósapka", "Menőn néz ki.", 1.0f, 20, 0.0f, 0.0f,0.0f,0.0f,0.0f,0.0f),
                            new Item("Alufólia sapka", "Megvéd az UFÓktól.", 1.0f, 20, 0.0f, 0.0f,0.0f,0.0f,0.0f,0.0f),
                            new Item("Kalap", "Megvéd az UFÓktól.", 1.0f, 20, 0.0f, 0.0f,0.0f,0.0f,0.0f,0.0f),
                            new Item("Fejkendő", "Megvéd az UFÓktól.", 1.0f, 20, 0.0f, 0.0f,0.0f,0.0f,0.0f,0.0f),
                    }),
                    new WeightedContainer<>(new Item[]{
                            new Item("Hálósapka", "Menőn néz ki.", 1.0f, 20, 0.0f, 0.0f,0.0f,0.0f,0.0f,0.0f),
                            new Item("Alufólia sapka", "Megvéd az UFÓktól.", 1.0f, 20, 0.0f, 0.0f,0.0f,0.0f,0.0f,0.0f),
                    }),
                    new WeightedContainer<>(new Item[]{
                            new Item("Bukósisak", "Menőn néz ki.", 1.0f, 20, 0.0f, 0.0f,0.0f,0.0f,0.0f,0.0f),
                            new Item("Alufólia sapka", "Megvéd az UFÓktól.", 1.0f, 20, 0.0f, 0.0f,0.0f,0.0f,0.0f,0.0f),
                    })
            },
            /* MELLKAS ITEMEK */
            new WeightedContainer[]{
                    new WeightedContainer<>(new Item[]{
                            new Item("Hálósapka", "Menőn néz ki.", 1.0f, 20, 0.0f, 0.0f,0.0f,0.0f,0.0f,0.0f),
                            new Item("Alufólia sapka", "Megvéd az UFÓktól.", 1.0f, 20, 0.0f, 0.0f,0.0f,0.0f,0.0f,0.0f),
                    }),
                    new WeightedContainer<>(new Item[]{
                            new Item("Hálósapka", "Menőn néz ki.", 1.0f, 20, 0.0f, 0.0f,0.0f,0.0f,0.0f,0.0f),
                            new Item("Alufólia sapka", "Megvéd az UFÓktól.", 1.0f, 20, 0.0f, 0.0f,0.0f,0.0f,0.0f,0.0f),
                    }),
                    new WeightedContainer<>(new Item[]{
                            new Item("Hálósapka", "Menőn néz ki.", 1.0f, 20, 0.0f, 0.0f,0.0f,0.0f,0.0f,0.0f),
                            new Item("Alufólia sapka", "Megvéd az UFÓktól.", 1.0f, 20, 0.0f, 0.0f,0.0f,0.0f,0.0f,0.0f),
                    })
            },
            /* LÁB ITEMEK */
            new WeightedContainer[]{
                    new WeightedContainer<>(new Item[]{
                            new Item("Hálósapka", "Menőn néz ki.", 1.0f, 20, 0.0f, 0.0f,0.0f,0.0f,0.0f,0.0f),
                            new Item("Alufólia sapka", "Megvéd az UFÓktól.", 1.0f, 20, 0.0f, 0.0f,0.0f,0.0f,0.0f,0.0f),
                    }),
                    new WeightedContainer<>(new Item[]{
                            new Item("Hálósapka", "Menőn néz ki.", 1.0f, 20, 0.0f, 0.0f,0.0f,0.0f,0.0f,0.0f),
                            new Item("Alufólia sapka", "Megvéd az UFÓktól.", 1.0f, 20, 0.0f, 0.0f,0.0f,0.0f,0.0f,0.0f),
                    }),
                    new WeightedContainer<>(new Item[]{
                            new Item("Hálósapka", "Menőn néz ki.", 1.0f, 20, 0.0f, 0.0f,0.0f,0.0f,0.0f,0.0f),
                            new Item("Alufólia sapka", "Megvéd az UFÓktól.", 1.0f, 20, 0.0f, 0.0f,0.0f,0.0f,0.0f,0.0f),
                    })
            },
            /* FEGYVER ITEMEK */
            new WeightedContainer[]{
                    new WeightedContainer<>(new Item[]{
                            new Item("Bot", "Egy bot...", 1.0f, 20, 0.0f, 0.0f,0.0f,0.0f,0.0f,0.0f),
                            new Item("Esernyő", "Egy e-ser-NYŐ!", 1.0f, 20, 0.0f, 0.0f,0.0f,0.0f,0.0f,0.0f),
                            new Item("Baseball ütő", "Most pofázzá'.", 1.0f, 20, 0.0f, 0.0f,0.0f,0.0f,0.0f,0.0f),
                            new Item("Törött borosüveg", "Lafiesta édes élmény.", 1.0f, 20, 0.0f, 0.0f,0.0f,0.0f,0.0f,0.0f),
                    }),
                    new WeightedContainer<>(new Item[]{
                            new Item("Buzogány", "Menőn néz ki.", 1.0f, 20, 0.0f, 0.0f,0.0f,0.0f,0.0f,0.0f),
                            new Item("Bicska", "Van cigid? Buszjegyre kell.", 1.0f, 20, 0.0f, 0.0f,0.0f,0.0f,0.0f,0.0f),
                            new Item("Rozsdás bökő", "Tetanusz is coming.", 1.0f, 20, 0.0f, 0.0f,0.0f,0.0f,0.0f,0.0f),
                    }),
                    new WeightedContainer<>(new Item[]{
                            new Item("MP5", "\uD83C\uDFB6All the other kids with the pumped up kicks\uD83C\uDFB6", 1.0f, 20, 0.0f, 0.0f,0.0f,0.0f,0.0f,0.0f),
                            new Item("Láncfűrész", "Vrr-vrrrrrr", 1.0f, 20, 0.0f, 0.0f,0.0f,0.0f,0.0f,0.0f),
                            new Item("Stielhandgranate", "*insert német szöveg here*.", 1.0f, 20, 0.0f, 0.0f,0.0f,0.0f,0.0f,0.0f),
                    })
            }
    };

    public static final WeightedContainer<Modifier>[] Modifiers = (WeightedContainer<Modifier>[])new WeightedContainer[]{
            /* RUHÁRA */
            new WeightedContainer<>(new Modifier[]{
                    new Modifier("Szakadt", 2.0f, 0.6f, 0.0f, 0.0f,0.0f,0.0f,0.0f,0.0f),
                    new Modifier("Rongyos", 5.0f, 0.8f, 0.0f, 0.0f,0.0f,0.0f,0.0f,0.0f),
                    new Modifier("Átlagos", 12.0f, 1.0f, 0.0f, 0.0f,0.0f,0.0f,0.0f,0.0f),
                    new Modifier("H&M-es", 5.0f, 1.3f, 0.0f, 0.0f,0.0f,0.0f,0.0f,0.0f),
                    new Modifier("Epikus", 1.0f, 1.8f, 0.0f, 0.0f,0.0f,0.0f,0.0f,0.0f),
                    new Modifier("Legendás", 0.5f, 2.2f, 0.0f, 0.0f,0.0f,0.0f,0.0f,0.0f),
            }),
            /* FEGYVERRE */
            new WeightedContainer<>(new Modifier[]{
                    new Modifier("Kurva szar", 2.0f, 0.6f, 0.0f, 0.0f,0.0f,0.0f,0.0f,0.0f),
                    new Modifier("Törött", 5.0f, 0.8f, 0.0f, 0.0f,0.0f,0.0f,0.0f,0.0f),
                    new Modifier("Átlagos", 12.0f, 1.0f, 0.0f, 0.0f,0.0f,0.0f,0.0f,0.0f),
                    new Modifier("Nagyon klassz", 5.0f, 1.3f, 0.0f, 0.0f,0.0f,0.0f,0.0f,0.0f),
                    new Modifier("Epikus", 1.0f, 1.8f, 0.0f, 0.0f,0.0f,0.0f,0.0f,0.0f),
                    new Modifier("Legendás", 0.5f, 2.2f, 0.0f, 0.0f,0.0f,0.0f,0.0f,0.0f),
            })
    };

    public static Targy Generate(int Slot, int Tier){
        WeightedContainer<Item> c = GetItemContainer(Slot, Tier);
        WeightedContainer<Modifier> m = GetModifierContainer(Slot);
        if(c == null || m == null)
            throw new IllegalArgumentException("Nem létező Slot / Tier.");
        return new Targy(Slot, Tier, c.GetRandom(), m.GetRandom());
    }
}
