package com.example.budapestquest.Targyak;

public class Targy {
    public static final int FELSO_ID = 0;
    public static final int NADRAG_ID = 1;
    public static final int CIPO_ID = 2;
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

    /*
    *   Visszaadják a tárgy statjat
    * */
    public double SumHP(){ return item.HP * modifier.HP; }
    public double SumDMG(){ return item.DMG * modifier.DMG; }
    public double SumDaP(){ return item.DaP * modifier.DaP; }
    public double SumDeP(){ return item.DeP * modifier.DeP; }

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

    /*
    *   Milyen ritka egy tárgy a többihez képest.
    * */
    public double GetRarity(){
        return GetItemRarity() * GetModifierRarity();
    }

    /*
     *   Milyen ritka egy item a többihez képest.
     * */
    public double GetItemRarity(){
        WeightedContainer<Item> t = GetItemContainer(Slot, Tier);
        return t.Items[ItemID].Weight / t.CalculatedWeight;
    }

    /*
     *   Milyen ritka egy modifier a többihez képest.
     * */
    public double GetModifierRarity(){
        WeightedContainer<Modifier> m = GetModifierContainer(Slot);
        return m.Items[ModifierID].Weight / m.CalculatedWeight;
    }

    /*
     *   A Schema-ból visszaadja a Slot, Tier és ItemID-nek megfelelő itemet.
     * */
    public static Item GetItem(int Slot, int Tier, int ItemID){
        WeightedContainer<Item> c = GetItemContainer(Slot, Tier);
        if(c == null)
            throw new IllegalArgumentException("Nem létező Slot / Tier.");
        if(ItemID < 0 || ItemID > c.Items.length)
            throw new IllegalArgumentException("Nem létező Tárgy.");
        return c.Items[ItemID];
    }

    /*
     *   A Schema-ból visszaadja a Slot és ModifierID-nek megfelelő modifiert.
     * */
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
            /* FELSŐ ITEMEK */
            new WeightedContainer[]{
                    new WeightedContainer<>(new Item[]{
                            new Item("Hálóing", "Elnézést a késésért.", 1.0f, 120, 400.0f, 0.0f,2.0f,0.0f),
                            new Item("Trikó", "Feszítős.", 1.0f, 120, 400.0f, 0.0f,0.0f,2.0f),
                    }),
                    new WeightedContainer<>(new Item[]{
                            new Item("Kockás ing", "Szexi.", 1.0f, 140, 500.0f, 0.0f,3.0f,0.0f),
                            new Item("Téli kabát", "Jó meleg.", 1.0f, 140, 500.0f, 0.0f,0.0f,3.0f),
                    }),
                    new WeightedContainer<>(new Item[]{
                            new Item("Golyóálló mellény", "Amerikai középiskola feeling.", 1.0f, 180, 700.0f, 0.0f,4.0f,0.0f),
                            new Item("Gyémánt mellvért", "Protection IV", 1.0f, 180, 700.0f, 0.0f,0.0f,4.0f),
                    })
            },
            /* NADRÁG ITEMEK */
            new WeightedContainer[]{
                    new WeightedContainer<>(new Item[]{
                            new Item("Farmer", "Klasszikus.", 1.0f, 120, 200.0f, 0.0f,0.0f,4.0f),
                            new Item("Mackónadrág", "Király Gabi ajánlásával.", 1.0f, 120, 0.0f, 20.0f,0.0f,4.0f),
                    }),
                    new WeightedContainer<>(new Item[]{
                            new Item("Nadrág #1", "A kollekció első darabja.", 1.0f, 140, 300.0f, 0.0f,0.0f,5.0f),
                            new Item("Nadrág #2", "A kollekció második darabja.", 1.0f, 140, 0.0f, 30.0f,0.0f,5.0f),
                    }),
                    new WeightedContainer<>(new Item[]{
                            new Item("Lábszárvédő", "Megvéd sok mindentől.", 1.0f, 180, 400.0f, 0.0f,0.0f,7.0f),
                            new Item("Vászonnadrág", "Nemtudom... talán kemény?", 1.0f, 180, 0.0f, 40.0f,0.0f,7.0f),
                    })
            },
            /* CIPŐ ITEMEK */
            new WeightedContainer[]{
                    new WeightedContainer<>(new Item[]{
                            new Item("Strandpapucs", "Szép.", 1.0f, 100, 200.0f, 0.0f,3.0f,0.0f),
                            new Item("Zokniszandál", "Mindenki kedvence.", 1.0f, 100, 0.0f, 20.0f,3.0f,0.0f),
                    }),
                    new WeightedContainer<>(new Item[]{
                            new Item("Sportcipő", "Teljesen átlagos.", 1.0f, 120, 300.0f, 0.0f,4.0f,0.0f),
                            new Item("Futócipő", "Sebesség vagyok.", 1.0f, 120, 0.0f, 30.0f,4.0f,0.0f),
                    }),
                    new WeightedContainer<>(new Item[]{
                            new Item("Acélbetétes bakancs", "Kemény.", 1.0f, 160, 400.0f, 0.0f,6.0f,0.0f),
                            new Item("Lakkcipő", "Elegante.", 1.0f, 160, 0.0f, 40.0f,6.0f,0.0f),
                    })
            },
            /* FEGYVER ITEMEK */
            new WeightedContainer[]{
                    new WeightedContainer<>(new Item[]{
                            new Item("Ásó", "Aki másnak...", 1.0f, 140, 0.0f, 40.0f,3.0f,0.0f),
                            new Item("Törött borosüveg", "Lafiesta édes élmény.", 1.0f, 140, 0.0f, 40.0f,0.0f,3.0f),
                    }),
                    new WeightedContainer<>(new Item[]{
                            new Item("Bicska", "Van cigid? Buszjegyre kell.", 1.0f, 160, 0.0f, 50.0f,0.0f,4.0f),
                            new Item("Rozsdás bökő", "Tetanusz is coming.", 1.0f, 160, 0.0f, 50.0f,4.0f,0.0f),
                    }),
                    new WeightedContainer<>(new Item[]{
                            new Item("MP5", "\uD83C\uDFB6All the other kids with the pumped up kicks\uD83C\uDFB6", 1.0f, 200, 0.0f, 70.0f,5.0f,0.0f),
                            new Item("Láncfűrész", "Vrr-vrrrrrr", 1.0f, 200, 0.0f, 70.0f,0.0f,5.0f),
                    })
            }
    };

    //TODO: Színeket resourceból
    public static final WeightedContainer<Modifier>[] Modifiers = (WeightedContainer<Modifier>[])new WeightedContainer[]{
            /* RUHÁRA */
            new WeightedContainer<>(new Modifier[]{
                    new Modifier("Szakadt", 2.0f, 0.6f, 0xff005500, 1.0f, 1.0f,1.0f,1.0f),
                    new Modifier("Rongyos", 5.0f, 0.8f, 0xff005500, 1.0f, 1.0f,1.0f,1.0f),
                    new Modifier("Átlagos", 12.0f, 1.0f,0xff005500, 1.0f, 1.0f,1.0f,1.0f),
                    new Modifier("H&M-es", 5.0f, 1.3f, 0xff005500,1.0f, 1.0f,1.0f,1.0f),
                    new Modifier("Epikus", 1.0f, 1.8f, 0xff005500, 1.0f, 1.0f,1.0f,1.0f),
                    new Modifier("Legendás", 0.5f, 2.2f, 0xff005500, 1.0f, 1.0f,1.0f,1.0f),
            }),
            /* FEGYVERRE */
            new WeightedContainer<>(new Modifier[]{
                    new Modifier("Borzalmas", 2.0f, 0.6f, 0xff005500, 1.0f, 1.0f,1.0f,1.0f),
                    new Modifier("Törött", 5.0f, 0.8f, 0xff005500, 1.0f, 1.0f,1.0f,1.0f),
                    new Modifier("Átlagos", 12.0f, 1.0f, 0xff005500, 1.0f, 1.0f,1.0f,1.0f),
                    new Modifier("Nagyon klassz", 5.0f, 1.3f, 0xff005500, 1.0f, 1.0f,1.0f,1.0f),
                    new Modifier("Epikus", 1.0f, 1.8f, 0xff005500, 1.0f, 1.0f,1.0f,1.0f),
                    new Modifier("Legendás", 0.5f, 2.2f, 0xff005500, 1.0f, 1.0f,1.0f,1.0f),
            })
    };

    /*
    *   Generál egy random itemet a súlyok szerint az adott Slot-ból és Tier-ből.
    * */
    public static Targy Generate(int Slot, int Tier){
        WeightedContainer<Item> c = GetItemContainer(Slot, Tier);
        WeightedContainer<Modifier> m = GetModifierContainer(Slot);
        if(c == null || m == null)
            throw new IllegalArgumentException("Nem létező Slot / Tier.");
        return new Targy(Slot, Tier, c.GetRandom(), m.GetRandom());
    }
}
