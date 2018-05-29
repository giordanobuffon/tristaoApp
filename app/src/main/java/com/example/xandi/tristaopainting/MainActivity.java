package com.example.xandi.tristaopainting;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.Layout;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.os.Vibrator; // Importa funções de vibrar

import java.util.UUID;

public class MainActivity extends Activity implements OnClickListener {

    private float smallBrush, mediumBrush, largeBrush;
    private DrawingView drawView;
    private ImageButton currPaint;
    private Vibrator vibrator;
    private boolean autorizaVibracao;
    private boolean mostraPaleta;
    private boolean autorizaSom;
    private LinearLayout paleta;
    private Handler handler;

    @Override
    public void onClick(final View view) {

    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        paleta = (LinearLayout)findViewById(R.id.paleta);
        drawView = (DrawingView)findViewById(R.id.drawing);
        LinearLayout paintLayout = (LinearLayout)findViewById(R.id.paint_colors);
        currPaint = (ImageButton)paintLayout.getChildAt(0);
        currPaint.setImageDrawable(getResources().getDrawable(R.drawable.paint));
        drawView.setBrushSize(mediumBrush);
        Bundle extra = getIntent().getExtras();
        autorizaVibracao = extra.getBoolean("switchVibra");
        mostraPaleta = extra.getBoolean("switchPaleta");
        autorizaSom = extra.getBoolean("switchSom");

        visibilidadePaleta();
        drawView.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {

                    case MotionEvent.ACTION_DOWN:
                        if (handler != null) return true;
                        handler = new Handler();
                        handler.postDelayed(action, 0);
                        break;

                    case MotionEvent.ACTION_UP:
                        if (handler == null) return true;
                        handler.removeCallbacks(action);
                        handler = null;
                        break;
                }
                return false;
            }

            Runnable action = new Runnable() {

                @Override
                public void run() {
                    tocaSom(drawView);
                    vibrar(250);
                    handler.postDelayed(this, 1);
                }
            };
        });
    }

    public void paintClicked(View view) {
        drawView.setBrushSize(drawView.getLastBrushSize());
        //use chosen color
        if (view != currPaint) {
//update color
            ImageButton imgView = (ImageButton) view;
            String color = view.getTag().toString();
            drawView.setColor(color);
            imgView.setImageDrawable(getResources().getDrawable(R.drawable.paint));
            currPaint.setImageDrawable(getResources().getDrawable(R.drawable.paint));
            currPaint = (ImageButton) view;
            vibrar(50);
        }
    }

    public void vibrar(int segundos)
    {
        if(autorizaVibracao){
        long milliseconds = segundos;
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(milliseconds);
    }}

    public void visibilidadePaleta(){
        if(mostraPaleta) {
            paleta.setVisibility(View.VISIBLE);
        }else{
        paleta.setVisibility(View.INVISIBLE);
    }}

    public void tocaSom(View view){
        if(autorizaSom){
        MediaPlayer jumping = MediaPlayer.create(this, R.raw.jumping);
        jumping.start();
        }
    }


}
