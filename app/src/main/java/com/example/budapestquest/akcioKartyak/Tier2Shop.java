package com.example.budapestquest.akcioKartyak;
import com.example.budapestquest.Targyak.Targy;
import com.example.budapestquest.GameController;

public  class Tier2Shop {
    Targy[] targyak;
    int capacity;

    //Másik két shopra is meg kell csinálni, csak nem tudom hogy Activity lesz-e belőlük
    public Tier2Shop() {
        capacity = 4;
        targyak = new Targy[capacity];
        feltolt(capacity, targyak);
    }

    //Feltölti a boltot 4 különböző itemmel
    public static void feltolt(int capacity, Targy[] test) {
        for (int j = 0; j < capacity; j++) {
            test[j] = Targy.Generate(j, 2);
        }
    }

    //Megvesz a 4 item közül egyet
    public void vasarlas(int index) {
        double actualPrice = targyak[index].item.Price * targyak[index].modifier.PriceWeight;
        if (GameController.En.FT >= actualPrice) {
            GameController.En.FT -= actualPrice;
            GameController.En.Felszereles[index] = targyak[index];
        }

    }
}

