package com.example.budapestquest.Targyak;

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

    // Habár ezek itt redundánsak, az ID-ket célszerű tárolni a serializáció miatt + késöbb nem kell indexOf
    public final Item item;
    public final Modifier modifier;

    public Targy(int _Slot, int _Tier, int _ItemID, int _ModifierID){
        if(_Slot < 0 || _Slot > 3)
            throw new IllegalArgumentException("Nem létező slot.");
        if(_Tier < 0 || _Tier > 2)
            throw new IllegalArgumentException("Nem létező tier.");

        Slot = _Slot;
        Tier = _Tier;

        ItemID = _ItemID;
        ModifierID = _ModifierID;

        if((item = GetItem(Slot, Tier, ItemID)) == null)
            throw new IllegalArgumentException("Nem létező item.");
        if((modifier = GetModifier(ModifierID)) == null)
            throw new IllegalArgumentException("Nem létező modifier.");
    }

    public static Item GetItem(int Slot, int Tier, int ItemID){
        try{
            switch (Slot){
                case FEJ_ID:        return Fejek[Tier].Items[ItemID];
                case MELLKAS_ID:    return Mellkasok[Tier].Items[ItemID];
                case LAB_ID:        return Labak[Tier].Items[ItemID];
                case FEGYVER_ID:    return Fegyverek[Tier].Items[ItemID];
                default: return null;
            }
        }catch (IndexOutOfBoundsException e){
            return null;
        }
    }

    public static Modifier GetModifier(int ModifierID){
        try {
            return Modifiers.Items[ModifierID];
        }catch (IndexOutOfBoundsException e){
            return null;
        }
    }

    //TODO: Ezt itt alul mind helyrerakni + feltölteni contenttel

    //TODO: Esetleg valami adatbázisba kiszervezni az egészet, amit aztán lehet majd frissíteni?

    /* FEJ ITEMEK */
    public static final Item[] Fej_T1 = {
            new Item("Hálósapka", "Menőn néz ki.", 3.0f, 0, 0.0f,0.0f,0.0f,0.0f,0.0f),
            new Item("Alufólia sapka", "Megvéd az UFÓktól.", 1.0f, 0, 0.0f,0.0f,0.0f,0.0f,0.0f),
    };
    public static final Item[] Fej_T2 = {
            new Item("Hálósapka", "Menőn néz ki.", 1.0f, 0, 0.0f,0.0f,0.0f,0.0f,0.0f),
            new Item("Alufólia sapka", "Megvéd az UFÓktól.", 1.0f, 0, 0.0f,0.0f,0.0f,0.0f,0.0f),
    };
    public static final Item[] Fej_T3 = {
            new Item("Hálósapka", "Menőn néz ki.", 1.0f, 0, 0.0f,0.0f,0.0f,0.0f,0.0f),
            new Item("Alufólia sapka", "Megvéd az UFÓktól.", 1.0f, 0, 0.0f,0.0f,0.0f,0.0f,0.0f),
    };
    public static final WeightedContainer<Item>[] Fejek = new WeightedContainer[]{
            new WeightedContainer<>(Fej_T1),
            new WeightedContainer<>(Fej_T2),
            new WeightedContainer<>(Fej_T3)
    };

    /* MELLKAS ITEMEK */
    public static final Item[] Mellkas_T1 = {
            new Item("Hálósapka", "Menőn néz ki.", 1.0f, 0, 0.0f,0.0f,0.0f,0.0f,0.0f),
            new Item("Alufólia sapka", "Megvéd az UFÓktól.", 1.0f, 0, 0.0f,0.0f,0.0f,0.0f,0.0f),
    };
    public static final Item[] Mellkas_T2 = {
            new Item("Hálósapka", "Menőn néz ki.", 1.0f, 0, 0.0f,0.0f,0.0f,0.0f,0.0f),
            new Item("Alufólia sapka", "Megvéd az UFÓktól.", 1.0f, 0, 0.0f,0.0f,0.0f,0.0f,0.0f),
    };
    public static final Item[] Mellkas_T3 = {
            new Item("Hálósapka", "Menőn néz ki.", 1.0f, 0, 0.0f,0.0f,0.0f,0.0f,0.0f),
            new Item("Alufólia sapka", "Megvéd az UFÓktól.", 1.0f, 0, 0.0f,0.0f,0.0f,0.0f,0.0f),
    };
    public static final WeightedContainer<Item>[] Mellkasok = new WeightedContainer[]{
            new WeightedContainer<>(Mellkas_T1),
            new WeightedContainer<>(Mellkas_T2),
            new WeightedContainer<>(Mellkas_T3)
    };

    /* LÁB ITEMEK */
    public static final Item[] Lab_T1 = {
            new Item("Hálósapka", "Menőn néz ki.", 1.0f, 0, 0.0f,0.0f,0.0f,0.0f,0.0f),
            new Item("Alufólia sapka", "Megvéd az UFÓktól.", 1.0f, 0, 0.0f,0.0f,0.0f,0.0f,0.0f),
    };
    public static final Item[] Lab_T2 = {
            new Item("Hálósapka", "Menőn néz ki.", 1.0f, 0, 0.0f,0.0f,0.0f,0.0f,0.0f),
            new Item("Alufólia sapka", "Megvéd az UFÓktól.", 1.0f, 0, 0.0f,0.0f,0.0f,0.0f,0.0f),
    };
    public static final Item[] Lab_T3 = {
            new Item("Hálósapka", "Menőn néz ki.", 1.0f, 0, 0.0f,0.0f,0.0f,0.0f,0.0f),
            new Item("Alufólia sapka", "Megvéd az UFÓktól.", 1.0f, 0, 0.0f,0.0f,0.0f,0.0f,0.0f),
    };
    public static final WeightedContainer<Item>[] Labak = new WeightedContainer[]{
            new WeightedContainer<>(Lab_T1),
            new WeightedContainer<>(Lab_T2),
            new WeightedContainer<>(Lab_T3)
    };

    /* FEGYVER ITEMEK */
    public static final Item[] Fegyver_T1 = {
            new Item("Hálósapka", "Menőn néz ki.", 1.0f, 0, 0.0f,0.0f,0.0f,0.0f,0.0f),
            new Item("Alufólia sapka", "Megvéd az UFÓktól.", 1.0f, 0, 0.0f,0.0f,0.0f,0.0f,0.0f),
    };
    public static final Item[] Fegyver_T2 = {
            new Item("Hálósapka", "Menőn néz ki.", 1.0f, 0, 0.0f,0.0f,0.0f,0.0f,0.0f),
            new Item("Alufólia sapka", "Megvéd az UFÓktól.", 1.0f, 0, 0.0f,0.0f,0.0f,0.0f,0.0f),
    };
    public static final Item[] Fegyver_T3 = {
            new Item("Hálósapka", "Menőn néz ki.", 1.0f, 0, 0.0f,0.0f,0.0f,0.0f,0.0f),
            new Item("Alufólia sapka", "Megvéd az UFÓktól.", 1.0f, 0, 0.0f,0.0f,0.0f,0.0f,0.0f),
    };
    public static final WeightedContainer<Item>[] Fegyverek = new WeightedContainer[]{
            new WeightedContainer<Item>(Fegyver_T1),
            new WeightedContainer<Item>(Fegyver_T2),
            new WeightedContainer<Item>(Fegyver_T3)
    };

    /* MÓDOSÍTÓK */
    public static final Modifier[] Modifiers_T = {
            new Modifier("Átlagos", 10.0f, 0, 0.0f,0.0f,0.0f,0.0f,0.0f),
            new Modifier("Epikus", 1.0f, 0, 0.0f,0.0f,0.0f,0.0f,0.0f),
            new Modifier("Nagyon klassz", 2.0f, 0, 0.0f,0.0f,0.0f,0.0f,0.0f),
            new Modifier("Kurva szar", 5.0f, 0, 0.0f,0.0f,0.0f,0.0f,0.0f),
    };
    public static final WeightedContainer<Modifier> Modifiers = new WeightedContainer<>(Modifiers_T);

    public static Targy Generate(int slot, int tier){
        if(tier < 0 || tier > 3) return null;
        int itemid;
        switch (slot){
            case FEJ_ID:        itemid = Fejek[tier].GetRandom(); break;
            case MELLKAS_ID:    itemid = Mellkasok[tier].GetRandom(); break;
            case LAB_ID:        itemid = Labak[tier].GetRandom(); break;
            case FEGYVER_ID:    itemid = Fegyverek[tier].GetRandom(); break;
            default: return null;
        }
        return new Targy(slot, tier, itemid, Modifiers.GetRandom());
    }
}
