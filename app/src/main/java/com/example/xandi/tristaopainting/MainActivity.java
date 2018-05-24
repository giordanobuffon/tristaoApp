package com.example.xandi.tristaopainting;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.os.Vibrator; // Importa funções de vibrar

import java.util.UUID;

public class MainActivity extends Activity implements OnClickListener {

    private float smallBrush, mediumBrush, largeBrush;
    private DrawingView drawView;
    private ImageButton currPaint, drawBtn, eraseBtn, newBtn, saveBtn;
    private Vibrator vibrator;
    private boolean autorizaVibracao;
    private View decorView;
    private int uiOptions;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onClick(final View view) {

        drawView.setOnTouchListener(new View.OnTouchListener() {

            private Handler handler;

            @Override
            public boolean onTouch(View v, MotionEvent event) {


                switch (event.getAction()) {

                    case MotionEvent.ACTION_DOWN:
                        if (handler != null) return true;
                        handler = new Handler();
                        handler.postDelayed(action, 200);
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
                    if (autorizaVibracao) {
                        vibrar(250);
                    }

                    handler.postDelayed(this, 200);
                }
            };
        });


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        decorView = getWindow().getDecorView();

        uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        decorView.setSystemUiVisibility(uiOptions);

//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

//        drawBtn = findViewById(R.id.draw_btn);
//        smallBrush = getResources().getInteger(R.integer.small_size);
//        mediumBrush = getResources().getInteger(R.integer.medium_size);
//        largeBrush = getResources().getInteger(R.integer.large_size);
        drawView = findViewById(R.id.drawing);
        LinearLayout paintLayout = findViewById(R.id.paint_colors);
        currPaint = (ImageButton) paintLayout.getChildAt(0);
        currPaint.setImageDrawable(getResources().getDrawable(R.drawable.paint));
        drawView.setBrushSize(mediumBrush);
//        drawBtn.setOnClickListener(this);
//        eraseBtn = findViewById(R.id.erase_btn);
//        eraseBtn.setOnClickListener(this);

//        newBtn = findViewById(R.id.new_btn);
//        newBtn.setOnClickListener(this);

//        saveBtn = findViewById(R.id.save_btn);
//        saveBtn.setOnClickListener(this);
        Bundle extra = getIntent().getExtras();
        autorizaVibracao = extra.getBoolean("switchVibra");

    }

    public void paintClicked(View view) {
        drawView.setErase(false);
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
        }
    }

    public void vibrar(int segundos) {
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate((long) segundos);
    }


    @Override
    public void onBackPressed() {
        vibrar(100);
    }

}
