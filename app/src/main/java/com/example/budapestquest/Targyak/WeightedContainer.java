package com.example.budapestquest.Targyak;

class Containerable{
    double Weight = 1.0f;
}

public class WeightedContainer<T extends Containerable>{
    public final T[] Items;
    public double CalculatedWeight = 0.0f;

    public WeightedContainer(T[] _Items){
        Items = _Items;
        //TODO: assert(Items.length != 0)
        for (int i = 0; i < Items.length; i++) {
            CalculatedWeight += Items[i].Weight;
        }
    }

    public int GetRandom(){
        double goal = Math.random() * CalculatedWeight, current = 0;
        for (int i = 0; i < Items.length; i++) {
            if(goal <= (current += Items[i].Weight)){
                return i;
            }
        }

        // Ha minden igaz, akkor ez akkor következhet csak be, hogyha üres tömbbel rendelkezünk.
        return -1;
    }
}
