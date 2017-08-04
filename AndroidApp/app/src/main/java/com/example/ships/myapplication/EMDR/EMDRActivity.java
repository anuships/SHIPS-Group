//Lalit Prasad 2017
//activity for eye movement exercises as part of EMDR therapy

package com.example.ships.myapplication.EMDR;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
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
    private static String firstName;
    private static String lastName;
    private static String email;
    private static String uid;
    private static String typeOfTerm;

    private void readIntent(){
        Bundle b = getIntent().getExtras();
        firstName = b.getString("firstName");
        lastName = b.getString("lastName");
        email = b.getString("email");
        uid = b.getString("uid");
        typeOfTerm = b.getString("typeOfTerm");
    }

    private Bundle createBundle(){
        Bundle b = new Bundle();
        b.putString("firstName", firstName);
        b.putString("uid", uid);
        b.putString("lastName", lastName);
        b.putString("email", email);
        b.putString("typeOfTerm",typeOfTerm);//treatment term
        return b;
    }

    //how long each emdr movement cycle lasts in milliseconds
    private static final int EMDR_DURATION = 2500;

    //base dimensions for the screen. Immediately updated when activity starts
    private static int SCREEN_WIDTH  = 500;
    private static int SCREEN_HEIGHT = 900;

    //used to limit emdr repetitions. Currently unused
    private static final int EMDR_REPEATS = 20;

    //plays ticking audio
    private MediaPlayer mediaPlayer;

    //circular, horizontal etc
    public EMDRMovementTypes emdrMovementType = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        readIntent();
        setContentView(R.layout.activity_emdr);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //gets user's chosen movement type from settings activity
        Intent intent = getIntent();
        String emdr_movement_type = intent.getStringExtra("emdr_Movement_Type");

        if (emdr_movement_type.equals("Horizontal")) {
            emdrMovementType = EMDRMovementTypes.SIMPLE_HORIZONTAL;
        } else if (emdr_movement_type.equals("Vertical")) {
            emdrMovementType = EMDRMovementTypes.SIMPLE_VERTICAL;
        } else if (emdr_movement_type.equals("Circular")) {
            emdrMovementType = EMDRMovementTypes.CIRCULAR;
        } else if (emdr_movement_type.equals("Figure of eight")) {
            emdrMovementType = EMDRMovementTypes.FIGURE_OF_EIGHT;
        }

        //ticking sound used in conjunction with eye movement
        mediaPlayer = MediaPlayer.create(this, R.raw.ticksound);

        final Handler handler = new Handler();

        //found that a delay was required so the audio lined up with the emdr movement
        final int delay = EMDR_DURATION - 150; //milliseconds

        //play the audio until the activity is exited
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
                //if (n <= EMDR_REPEATS) {
                if (n >= 0 && n%2==1) {
                    handler.postDelayed(loopingRunnable, delay);
                    //tick in right headphone
                    mediaPlayer.setVolume(0.f, 1.f);
                    n++;
                } else if (n >=0 && n%2==0) {
                    handler.postDelayed(loopingRunnable, delay);
                    //tick in left headphone
                    mediaPlayer.setVolume(1.f, 0.f);
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


        //get dimensions of display
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;

        SCREEN_WIDTH = width;
        SCREEN_HEIGHT = height;


        //add emdr circle to view
        float emdrCircleDiameter = getResources().getDimension(R.dimen.emdr_circle_diameter);
        View emdrView = findViewById(R.id.emdrlayout);
        emdrView.setVisibility(View.VISIBLE);
        View emdrCircleView = findViewById(R.id.emdr_circle_layout);

        //horizontal movement (simple harmonic)
        ObjectAnimator horizontal_Centre_To_Right = ObjectAnimator.ofFloat(emdrCircleView, "x", (SCREEN_WIDTH - emdrCircleDiameter)/2, (SCREEN_WIDTH - emdrCircleDiameter));
        ObjectAnimator horizontal_Right_To_Centre = ObjectAnimator.ofFloat(emdrCircleView, "x", (SCREEN_WIDTH - emdrCircleDiameter), (SCREEN_WIDTH - emdrCircleDiameter)/2);
        ObjectAnimator horizontal_Centre_To_Left = ObjectAnimator.ofFloat(emdrCircleView, "x", (SCREEN_WIDTH - emdrCircleDiameter)/2, 0);
        ObjectAnimator horizontal_Left_To_Centre = ObjectAnimator.ofFloat(emdrCircleView, "x", 0, (SCREEN_WIDTH - emdrCircleDiameter)/2);

        horizontal_Centre_To_Right.setInterpolator(new DecelerateInterpolator());
        horizontal_Right_To_Centre.setInterpolator(new AccelerateInterpolator());
        horizontal_Centre_To_Left.setInterpolator(new DecelerateInterpolator());
        horizontal_Left_To_Centre.setInterpolator(new AccelerateInterpolator());


        //repeat of horizontal movement animation. Used in figure-of-eight movement, which requires two horizontal cycles with each vertical cycle
        ObjectAnimator horizontal_Centre_To_Right2 = ObjectAnimator.ofFloat(emdrCircleView, "x", (SCREEN_WIDTH - emdrCircleDiameter)/2, (SCREEN_WIDTH - emdrCircleDiameter));
        ObjectAnimator horizontal_Right_To_Centre2 = ObjectAnimator.ofFloat(emdrCircleView, "x", (SCREEN_WIDTH - emdrCircleDiameter), (SCREEN_WIDTH - emdrCircleDiameter)/2);
        ObjectAnimator horizontal_Centre_To_Left2 = ObjectAnimator.ofFloat(emdrCircleView, "x", (SCREEN_WIDTH - emdrCircleDiameter)/2, 0);
        ObjectAnimator horizontal_Left_To_Centre2 = ObjectAnimator.ofFloat(emdrCircleView, "x", 0, (SCREEN_WIDTH - emdrCircleDiameter)/2);

        horizontal_Centre_To_Right2.setInterpolator(new DecelerateInterpolator());
        horizontal_Right_To_Centre2.setInterpolator(new AccelerateInterpolator());
        horizontal_Centre_To_Left2.setInterpolator(new DecelerateInterpolator());
        horizontal_Left_To_Centre2.setInterpolator(new AccelerateInterpolator());



        //vertical movement
        ObjectAnimator vertical_Top_To_Centre = ObjectAnimator.ofFloat(emdrCircleView, "y", 0, (SCREEN_HEIGHT - 2*emdrCircleDiameter)/2);
        ObjectAnimator vertical_Centre_To_Bottom = ObjectAnimator.ofFloat(emdrCircleView, "y", (SCREEN_HEIGHT - 2*emdrCircleDiameter)/2, (SCREEN_HEIGHT - 2*emdrCircleDiameter));
        ObjectAnimator vertical_Bottom_To_Centre = ObjectAnimator.ofFloat(emdrCircleView, "y", (SCREEN_HEIGHT - 2*emdrCircleDiameter), (SCREEN_HEIGHT - 2*emdrCircleDiameter)/2);
        ObjectAnimator vertical_Centre_To_Top = ObjectAnimator.ofFloat(emdrCircleView, "y", (SCREEN_HEIGHT - 2*emdrCircleDiameter)/2, 0);

        vertical_Top_To_Centre.setInterpolator(new AccelerateInterpolator());
        vertical_Centre_To_Bottom.setInterpolator(new DecelerateInterpolator());
        vertical_Bottom_To_Centre.setInterpolator(new AccelerateInterpolator());
        vertical_Centre_To_Top.setInterpolator(new DecelerateInterpolator());

        final AnimatorSet animSet = new AnimatorSet();

        //play animation corresponding to user selection
        if (emdrMovementType.equals(EMDRMovementTypes.SIMPLE_VERTICAL)) {
                   animSet.play(vertical_Top_To_Centre).before(vertical_Centre_To_Bottom);
                   animSet.play(vertical_Bottom_To_Centre).after(vertical_Centre_To_Bottom).before(vertical_Centre_To_Top);
            animSet.setDuration(EMDR_DURATION/2);
        } else if (emdrMovementType.equals(EMDRMovementTypes.SIMPLE_HORIZONTAL)) {
            animSet.play(horizontal_Centre_To_Right).before(horizontal_Right_To_Centre);
            animSet.play(horizontal_Centre_To_Left).after(horizontal_Right_To_Centre).before(horizontal_Left_To_Centre);
            animSet.setDuration(EMDR_DURATION/2);
        } else if (emdrMovementType.equals(EMDRMovementTypes.CIRCULAR)) {
            animSet.play(horizontal_Centre_To_Right).with(vertical_Top_To_Centre);
            animSet.play(horizontal_Right_To_Centre).after(horizontal_Centre_To_Right).with(vertical_Centre_To_Bottom);
            animSet.play(horizontal_Centre_To_Left).after(horizontal_Right_To_Centre).with(vertical_Bottom_To_Centre);
            animSet.play(horizontal_Left_To_Centre).after(horizontal_Centre_To_Left).with(vertical_Centre_To_Top);
            animSet.setDuration(EMDR_DURATION/2);

        } else if (emdrMovementType.equals(EMDRMovementTypes.FIGURE_OF_EIGHT)) {

            vertical_Top_To_Centre.setDuration(EMDR_DURATION);
            vertical_Centre_To_Bottom.setDuration(EMDR_DURATION);
            vertical_Bottom_To_Centre.setDuration(EMDR_DURATION);
            vertical_Centre_To_Top.setDuration(EMDR_DURATION);

            horizontal_Centre_To_Right.setDuration(EMDR_DURATION/2);
            horizontal_Right_To_Centre.setDuration(EMDR_DURATION/2);
            horizontal_Centre_To_Left.setDuration(EMDR_DURATION/2);
            horizontal_Left_To_Centre.setDuration(EMDR_DURATION/2);


            horizontal_Centre_To_Right2.setDuration(EMDR_DURATION/2);
            horizontal_Right_To_Centre2.setDuration(EMDR_DURATION/2);
            horizontal_Centre_To_Left2.setDuration(EMDR_DURATION/2);
            horizontal_Left_To_Centre2.setDuration(EMDR_DURATION/2);

            animSet.play(horizontal_Centre_To_Right).with(vertical_Top_To_Centre).before(horizontal_Right_To_Centre);
            animSet.play(horizontal_Centre_To_Left).with(vertical_Centre_To_Bottom).before(horizontal_Left_To_Centre).after(horizontal_Right_To_Centre);
            animSet.play(horizontal_Centre_To_Right2).with(vertical_Bottom_To_Centre).before(horizontal_Right_To_Centre2).after(horizontal_Left_To_Centre);
            animSet.play(horizontal_Centre_To_Left2).with(vertical_Centre_To_Top).before(horizontal_Left_To_Centre2).after(horizontal_Right_To_Centre2);
        }


        //play animation forever
        animSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                animSet.start();
            }
        });

        animSet.start();

        //return to settings
        Button start_emdr_settings_button = (Button) findViewById(R.id.start_emdr_settings_button);
        start_emdr_settings_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){
                finish();
            }
        });
            }

    //stop audio when leaving activity or rotating screen (!)
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