package com.example.ships.myapplication.EMDR;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.drawable.shapes.OvalShape;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Display;
import android.view.SoundEffectConstants;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ships.myapplication.R;

import java.io.IOException;

import static com.example.ships.myapplication.R.layout.activity_emdr;
import static com.example.ships.myapplication.R.layout.animationemdr;

public class EMDRActivity extends AppCompatActivity {

    private static final int EMDR_DURATION = 2000;
//    private static final int SCREEN_WIDTH = 1000;
//    private static final int SCREEN_HEIGHT = 1800;
    private static int SCREEN_WIDTH  = 500;
    private static int SCREEN_HEIGHT = 900;
    private static final int EMDR_REPEATS = 10;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emdr);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//                AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
//        for (int i = 0; i < 10; i++) {
//            audioManager.playSoundEffect(SoundEffectConstants.CLICK);
//
//        }







        final MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.ticksound);



        final Handler handler = new Handler();
        final int delay = EMDR_DURATION; //milliseconds

        final Runnable loopingRunnable = new Runnable() {
            @Override
            public void run() {
                if (mediaPlayer != null) {
                    if (mediaPlayer.isPlaying()) {
                        mediaPlayer.stop();
                    }
                        mediaPlayer.start();
                }
            }
        };

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

            int n = 0;

            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                if (n < 10) {
                    handler.postDelayed(loopingRunnable, delay);
                    n++;
                }
            }
        });

        mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
                return true;
            }
        });

//        @Override
//        protected void onDestroy(){
//            super.onDestroy();
//            handler.removeCallbacks(loopingRunnable);
//
//            if (mediaPlayer != null) {
//                if (mediaPlayer.isPlaying()) {
//                    mediaPlayer.stop();
//                }
//
//                mediaPlayer.release();
//                mediaPlayer = null;
//            }
//
//        }


//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                mediaPlayer.start();
//                mediaPlayer.release();
//                handler.postDelayed(this, delay);
//            }
//        }, delay);

//        MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.ticksound);
//        for (int i = 0; i < EMDR_REPEATS; i++) {
//            mediaPlayer.start();
//            try {
//                mediaPlayer.prepare();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }

//        mediaPlayer.setLooping(true);
        mediaPlayer.start();

                Display display = getWindowManager().getDefaultDisplay();
                Point size = new Point();
                display.getSize(size);
                int width = size.x;
                int height = size.y;

                SCREEN_WIDTH = width;
                SCREEN_HEIGHT = height;

                float emdrCircleDiameter = getResources().getDimension(R.dimen.emdr_circle_diameter);

                AnimationSet ballMovementSet = new AnimationSet(true);

//                TranslateAnimation moveLeftToRight = new TranslateAnimation(-((SCREEN_WIDTH - emdrCircleDiameter)/2), ((SCREEN_WIDTH - emdrCircleDiameter)/2),
//                        ((SCREEN_HEIGHT - emdrCircleDiameter)/2), ((SCREEN_HEIGHT - emdrCircleDiameter)/2));
                TranslateAnimation moveLeftToRight = new TranslateAnimation(-((SCREEN_WIDTH - emdrCircleDiameter)/2), ((SCREEN_WIDTH - emdrCircleDiameter)/2),
                        (0), (0));
                moveLeftToRight.setDuration(EMDR_DURATION);
                moveLeftToRight.setFillAfter(true);
                moveLeftToRight.setRepeatMode(Animation.REVERSE);
                moveLeftToRight.setRepeatCount(EMDR_REPEATS);
                ballMovementSet.addAnimation(moveLeftToRight);

                TranslateAnimation moveUpAndDown = new TranslateAnimation(0, 0, 0, SCREEN_HEIGHT);
                moveUpAndDown.setDuration(3*EMDR_DURATION);
                moveUpAndDown.setFillAfter(true);
                moveUpAndDown.setRepeatMode(Animation.REVERSE);
                moveUpAndDown.setRepeatCount(EMDR_REPEATS);
                //ballMovementSet.addAnimation(moveUpAndDown);

                View emdrView = findViewById(R.id.emdrlayout);
                emdrView.setVisibility(View.VISIBLE);


                LinearLayout layoutEMDRMovement = (LinearLayout) findViewById(R.id.emdr_circle_layout);
                layoutEMDRMovement.startAnimation(ballMovementSet);
//        for (int i = 0; i < 10; i++) {
//            emdrView.playSoundEffect(SoundEffectConstants.CLICK);
//        }
//                layoutEMDRMovement.playSoundEffect(SoundEffectConstants.CLICK);
////        emdrView.playSoundEffect(SoundEffectConstants.CLICK);
//
//        View viewEMDRMovement = findViewById(R.id.emdr_circle_layout);
//        viewEMDRMovement.playSoundEffect(SoundEffectConstants.CLICK);



        Button start_emdr_settings_button = (Button) findViewById(R.id.start_emdr_settings_button);
        start_emdr_settings_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){
                finish();
            }
        });


            }




    }