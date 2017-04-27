package com.example.ships.myapplication.EMDR;

import android.graphics.drawable.shapes.OvalShape;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ships.myapplication.R;

import static com.example.ships.myapplication.R.layout.animationemdr;

public class EMDRActivity extends AppCompatActivity {

    private static final int EMDR_DURATION = 2000;
    private static final int SCREEN_WIDTH = 1000;
    private static final int SCREEN_HEIGHT = 1800;
    private static final int EMDR_REPEATS = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emdr);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        AnimationSet ballMovementSet = new AnimationSet(true);

        TranslateAnimation moveLeftToRight = new TranslateAnimation(-(SCREEN_WIDTH/2), (SCREEN_WIDTH/2), (SCREEN_HEIGHT/2), (SCREEN_HEIGHT/2));
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
        
        LinearLayout layoutEMDRMovement = (LinearLayout) findViewById(R.id.emdrlayout);
        layoutEMDRMovement.startAnimation(ballMovementSet);

    }

}
