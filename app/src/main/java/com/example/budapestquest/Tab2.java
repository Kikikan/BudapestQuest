package com.example.budapestquest;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Tab2 extends Fragment {

    GameController gameController = MainActivity.gameController;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tab2, container, false);
        gameController.Initialize(v);

        return v;
    }

    public void levelUPHP()
    {
        if(GameController.En.XP >= 1)
        {
            GameController.En.HP += 1*100;
            GameController.En.XP -= 1;
        }
        else
        {
            //NINCS ELÉG XP-éd
        }
    }

    public void levelUPDMG()
    {
        if(GameController.En.XP >= 1)
        {
            GameController.En.DMG += 1*10;
            GameController.En.XP -= 1;
        }
        else
        {
            //NINCS ELÉG XP-éd
        }
    }

    public void levelUPDaP()
    {
        if(GameController.En.XP >= 1)
        {
            GameController.En.DaP += 1;
            GameController.En.XP -= 1;
        }
        else
        {
            //NINCS ELÉG XP-éd
        }
    }

    public void levelUPDeP()
    {
        if(GameController.En.XP >= 1)
        {
            GameController.En.DeP += 1;
            GameController.En.XP -= 1;
        }
        else
        {
            //NINCS ELÉG XP-éd
        }
    }

    public void levelUPCR()
    {
        if(GameController.En.XP >= 1)
        {
            GameController.En.CR += 1;
            GameController.En.XP -= 1;
        }
        else
        {
            //NINCS ELÉG XP-éd
        }
    }

    public void levelUPDO()
    {
        if(GameController.En.XP >= 1)
        {
            GameController.En.DO += 1;
            GameController.En.XP -= 1;
        }
        else
        {
            //NINCS ELÉG XP-éd
        }
    }
/*    ImageView kep = findViewById(R.id.profpict);

    if(GameController.En.Name == "creeper"){
        kep.setImageResource(R.drawable.creeper);
    }
    else if(GameController.En.Name == "pepe"){
        kep.setImageResource(R.drawable.pepe);
    }
    else
    {
        kep.setImageResource(R.drawable.face);
    }*/
}
