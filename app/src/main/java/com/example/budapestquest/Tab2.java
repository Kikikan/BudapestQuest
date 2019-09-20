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
