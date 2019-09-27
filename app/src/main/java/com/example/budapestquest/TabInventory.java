package com.example.budapestquest;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.budapestquest.ActionCards.BoltAct;
import com.example.budapestquest.Karakterek.Karakter;

import java.util.Random;


public class TabInventory extends Fragment {

    View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tabinventory, container, false);
        view = v;
        Button button = (Button) view.findViewById(R.id.doboButton);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                /*if (GameController.Instance.En == null)
                    return;
                Random r = new Random();
                TextView tv = view.findViewById(R.id.doboTextView);
                int result;
                if (GameController.Instance.En.UNI == Karakter.TE_ID)
                    result = r.nextInt(8) + 1;
                else
                    result = r.nextInt(6) + 1;
                tv.setText(String.valueOf(result));*/

                Intent intent = new Intent(getContext(), BoltAct.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("TIER", 1);
                getContext().startActivity(intent);
            }
        });

        return v;
    }

    public void Update(){

    }
}
