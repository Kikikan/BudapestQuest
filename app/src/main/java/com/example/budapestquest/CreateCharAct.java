package com.example.budapestquest;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.common.api.CommonStatusCodes;

public class CreateCharAct extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.createchar);
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    public void CreateButton(View v) {
        try {
            String name = ((EditText) findViewById(R.id.nevEditText)).getText().toString();
            if(name.isEmpty()) {
                Toast.makeText(getApplicationContext(), "Meg kell adnod egy nevet!", Toast.LENGTH_SHORT).show();
                return;
            }

            RadioGroup kasztGroup = findViewById(R.id.kasztgroup);
            int kasztId = kasztGroup.indexOfChild(kasztGroup.findViewById(kasztGroup.getCheckedRadioButtonId()));

            RadioGroup uniGroup = findViewById(R.id.unigroup);
            int uniId = uniGroup.indexOfChild(uniGroup.findViewById(uniGroup.getCheckedRadioButtonId()));

            GameController.CreateKarakter(name, kasztId, uniId);

            setResult(CommonStatusCodes.SUCCESS);
            finish();
        }catch(Exception e){
            Toast.makeText(getApplicationContext(), "Hiba: " + e.toString(), Toast.LENGTH_SHORT).show();
        }
    }
}
