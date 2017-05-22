package com.example.ships.myapplication.EMDR;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
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
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
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
    private static final int EMDR_REPEATS = 20;
    private MediaPlayer mediaPlayer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emdr);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        mediaPlayer = MediaPlayer.create(this, R.raw.ticksound);

        final Handler handler = new Handler();
        final int delay = EMDR_DURATION - 150; //milliseconds

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
                if (n <= EMDR_REPEATS) {
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
                //ballMovementSet.addAnimation(moveLeftToRight);

                TranslateAnimation moveUpAndDown = new TranslateAnimation(0, 0, -((SCREEN_HEIGHT - 2*emdrCircleDiameter)/2), ((SCREEN_HEIGHT - 2*emdrCircleDiameter)/2));
                moveUpAndDown.setDuration(2*EMDR_DURATION);
                moveUpAndDown.setFillAfter(true);
                moveUpAndDown.setRepeatMode(Animation.REVERSE);
                moveUpAndDown.setRepeatCount(EMDR_REPEATS/2);
                //ballMovementSet.addAnimation(moveUpAndDown);

                View emdrView = findViewById(R.id.emdrlayout);
                emdrView.setVisibility(View.VISIBLE);

        float scale = getResources().getDisplayMetrics().density;
        View emdrCircleView = findViewById(R.id.emdr_circle_layout);

        ObjectAnimator horizontal_Centre_To_Right = ObjectAnimator.ofFloat(emdrCircleView, "x", (SCREEN_WIDTH - emdrCircleDiameter)/2, (SCREEN_WIDTH - emdrCircleDiameter));
        ObjectAnimator horizontal_Right_To_Centre = ObjectAnimator.ofFloat(emdrCircleView, "x", (SCREEN_WIDTH - emdrCircleDiameter), (SCREEN_WIDTH - emdrCircleDiameter)/2);
        ObjectAnimator horizontal_Centre_To_Left = ObjectAnimator.ofFloat(emdrCircleView, "x", (SCREEN_WIDTH - emdrCircleDiameter)/2, 0);
        ObjectAnimator horizontal_Left_To_Centre = ObjectAnimator.ofFloat(emdrCircleView, "x", 0, (SCREEN_WIDTH - emdrCircleDiameter)/2);

        horizontal_Centre_To_Right.setInterpolator(new DecelerateInterpolator());
        horizontal_Right_To_Centre.setInterpolator(new AccelerateInterpolator());
        horizontal_Centre_To_Left.setInterpolator(new DecelerateInterpolator());
        horizontal_Left_To_Centre.setInterpolator(new AccelerateInterpolator());



        AnimatorSet animSet = new AnimatorSet();

        animSet.play(horizontal_Centre_To_Right).after(500);
        animSet.play(horizontal_Right_To_Centre).after(horizontal_Centre_To_Right);
        animSet.play(horizontal_Centre_To_Left).after(horizontal_Right_To_Centre);
        animSet.play(horizontal_Left_To_Centre).after(horizontal_Centre_To_Left);
        animSet.setDuration(1000);
        //animSet.setInterpolator(new AccelerateDecelerateInterpolator());
        animSet.start();


                LinearLayout layoutEMDRMovement = (LinearLayout) findViewById(R.id.emdr_circle_layout);
                //layoutEMDRMovement.startAnimation(ballMovementSet);




        Button start_emdr_settings_button = (Button) findViewById(R.id.start_emdr_settings_button);
        start_emdr_settings_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){
                finish();
            }
        });


            }

        @Override
        protected void onDestroy(){
            super.onDestroy();
            if (mediaPlayer != null) {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                }

                mediaPlayer.release();
                mediaPlayer = null;
            }

        }

    }