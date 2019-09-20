package com.example.budapestquest;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.budapestquest.Karakterek.Karakter;

import java.util.Random;


public class Tab3 extends Fragment {

    View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tab3, container, false);
        view = v;
        Button button = (Button) view.findViewById(R.id.doboButton);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (GameController.En == null)
                    return;
                Random r = new Random();
                TextView tv = view.findViewById(R.id.doboTextView);
                int result;
                if (GameController.En.UNI == Karakter.TE_ID)
                    result = r.nextInt(8) + 1;
                else
                    result = r.nextInt(6) + 1;
                tv.setText(String.valueOf(result));
            }
        });

        return v;
    }
}
