package com.example.ships.myapplication;

import android.graphics.drawable.shapes.OvalShape;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import static com.example.ships.myapplication.R.layout.animationemdr;

public class EMDRActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emdr);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TranslateAnimation moveLeftToRight = new TranslateAnimation(0, 400, 0, 0);
        moveLeftToRight.setDuration(1000);
        moveLeftToRight.setFillAfter(true);

        Animation emdrMovement;
        LinearLayout layoutEMDRMovement = (LinearLayout) findViewById(R.id.emdrlayout);
        layoutEMDRMovement.startAnimation(moveLeftToRight);


//
//        TextView testText = new TextView(this);
//        testText.setText("Test");
//        testText.startAnimation(moveLeftToRight);




//        emdrMovement = AnimationUtils.loadAnimation(this, R.layout.animationemdr);
//        emdrMovement.reset();
//        emdrMovement.setFillAfter(true);
//        emdrMovement.setAnimationListener((Animation.AnimationListener) this);
//        layoutEMDRMovement.startAnimation(emdrMovement);

    }

}
