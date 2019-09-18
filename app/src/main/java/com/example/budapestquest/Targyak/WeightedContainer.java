package com.example.budapestquest.Targyak;

class Containerable{
    double Weight = 1.0f;
}

public class WeightedContainer<T extends Containerable>{
    public final T[] Items;
    public double CalculatedWeight = 0.0f;

    public WeightedContainer(T[] _Items){
        Items = _Items;

        if(Items.length == 0)
            throw new IllegalArgumentException("Nem lehet üres a tömb.");

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
        return -1;
    }
}
