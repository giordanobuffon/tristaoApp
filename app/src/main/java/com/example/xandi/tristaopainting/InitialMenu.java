package com.example.xandi.tristaopainting;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Switch;

/**
 * Created by Xandi on 5/3/2018.
 */

public class InitialMenu extends AppCompatActivity {

    private Button btnPronto;
    private Switch switch1;
    private boolean switchVibra;
    private Switch switch2;
    private boolean switchPaleta;
    private Switch switch3;
    private boolean switchSom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.initial_menu);
        //final Intent intent = new Intent(this, MainActivity.class);

        btnPronto = (Button) findViewById(R.id.btnPronto);
        switch1 = (Switch) findViewById(R.id.switchVibra);
        switch2 = (Switch) findViewById(R.id.switchPaleta);
        switch3 = (Switch) findViewById(R.id.switchSom);


        btnPronto.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if(switch1.isChecked()){
                    switchVibra = true;
                }else{
                    switchVibra = false;
                }
                if(switch2.isChecked()){
                    switchPaleta = true;
                }else{
                    switchPaleta = false;
                }
                if(switch3.isChecked()){
                    switchSom = true;
                }else{
                    switchSom = false;
                }
                Intent intent = new Intent(InitialMenu.this, MainActivity.class);
                intent.putExtra("switchVibra", switchVibra);
                intent.putExtra("switchPaleta", switchPaleta);
                intent.putExtra("switchSom", switchSom);
                startActivity(intent);
            }

        });
    }
}
