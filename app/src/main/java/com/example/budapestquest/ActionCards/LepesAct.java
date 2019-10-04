package com.example.budapestquest.ActionCards;

import androidx.appcompat.app.AppCompatActivity;

import com.example.budapestquest.GameController;
import com.example.budapestquest.Karakterek.Karakter;
import com.example.budapestquest.R;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class LepesAct extends AppCompatActivity {

    public static final int bliccbirsag = 60;
    public static final int blicckorkimaradas = 3;

    private TextView lepes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lepes);

        lepes = findViewById(R.id.lepes);

        ((TextView)findViewById(R.id.statom)).setText("Jegyeim: "+ GameController.En.vonaljegy + " db");
        ((TextView)findViewById(R.id.lepes)).setText("Hányat: "+ (GameController.rand.nextInt((GameController.En.UNI == Karakter.TE_ID ? 8 : 6)) + 1));

        Button LyukasztasButton = findViewById(R.id.LyukasztasButton);
        if(GameController.En.vonaljegy == 0) {
            LyukasztasButton.getBackground().setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);
            LyukasztasButton.setClickable(false);
        }
    }

    // Ne lehessen visszalépni, ezzel megúszva a lyukasztást
    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    public void ButtonBlicc(View v){
        if (GameController.rand.nextDouble() < (GameController.En.UNI == Karakter.ELTE_ID ? 0.125 : 0.25)) {
            if(GameController.En.SpendMoney(bliccbirsag)) {
                Toast.makeText(getApplicationContext(), "Elkapott a kaller és megbírságolt téged " + bliccbirsag +" Ft-ra.", Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(getApplicationContext(), "Elkapott a keller, viszont nem tudtad kifizetni a helyszíni bírságot ( " + bliccbirsag + " Ft ), ezért bevágtak téged a böribe " + blicckorkimaradas + " körre.", Toast.LENGTH_LONG).show();
                GameController.En.kimaradas = blicckorkimaradas;
            }
            GameController.tabStats.Update();
            finish();
        } else {
            Toast.makeText(getApplicationContext(), "Sikerült! Nincs olyan ellenőr aki elbánhatna veled! ;)", Toast.LENGTH_LONG).show();
            finish();
        }
    }

    public void ButtonLyukasztas(View v){
        if(GameController.En.vonaljegy > 0) {
            GameController.En.vonaljegy--;
            Toast.makeText(getApplicationContext(), "Lyukasztottál, egyel kevesebb jegyed van.", Toast.LENGTH_LONG).show();
            GameController.tabStats.Update();
            finish();
        }else // Ide elvileg nem juthatunk el
            Toast.makeText(getApplicationContext(), "Nincs vonaljegyed.", Toast.LENGTH_LONG).show();
    }
}
