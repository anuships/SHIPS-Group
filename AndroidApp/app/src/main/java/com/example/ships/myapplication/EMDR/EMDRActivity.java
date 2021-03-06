//Lalit Prasad 2017
//activity for eye movement exercises as part of EMDR therapy

package com.example.ships.myapplication.EMDR;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.GradientDrawable;
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
import android.view.LayoutInflater;
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
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ships.myapplication.OtherInterfaces.DrawerActivity;
import com.example.ships.myapplication.R;
import com.example.ships.myapplication.homepageAndRegistration.DBManager;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.example.ships.myapplication.R.layout.activity_emdr;
import static com.example.ships.myapplication.R.layout.animationemdr;



public class EMDRActivity extends DrawerActivity {
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
    private static int EMDR_DURATION = 2500;

    //base dimensions for the screen. Immediately updated when activity starts
    private static int SCREEN_WIDTH  = 500;
    private static int SCREEN_HEIGHT = 900;

    //how long the whole emdr session lasts in milliseconds
    private static int EMDR_TOTAL_DURATION = 15000;

    //used to limit emdr repetitions. Currently unused
    private static int EMDR_REPEATS = 20;

    //plays ticking audio
    private MediaPlayer mediaPlayer;

    //circular, horizontal etc
    public EMDRMovementTypes emdrMovementType = null;

    private static Boolean SHADOW_ON = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        readIntent();
//        setContentView(R.layout.activity_emdr);
        //Add drawer by Jason
        FrameLayout frameLayout = (FrameLayout)findViewById(R.id.content_frame);
        LayoutInflater layoutInflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View activityView = layoutInflater.inflate(R.layout.activity_emdr, null,false);
        frameLayout.addView(activityView);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //gets user's chosen movement type from settings activity
        Intent intent = getIntent();
        String emdr_movement_type = intent.getStringExtra("emdr_Movement_Type");
        String emdr_total_duration = intent.getStringExtra("emdr_Total_Duration");
        String emdr_shadow = intent.getStringExtra("emdr_Shadow");


        //Add records by Jason
        addRecords();

        final AnimatorSet animSet = new AnimatorSet();

        //gets user's chosen speed from settings activity
        String emdr_speed = intent.getStringExtra("emdr_Speed");
        if (emdr_speed.equals("Slow")) {
            EMDR_DURATION = 2500;
        } else if (emdr_speed.equals("Medium")) {
            EMDR_DURATION = 1500;
        } else if (emdr_speed.equals("Fast")) {
            EMDR_DURATION = 500;
        }

        if (emdr_movement_type.equals("Horizontal")) {
            emdrMovementType = EMDRMovementTypes.SIMPLE_HORIZONTAL;
        } else if (emdr_movement_type.equals("Vertical")) {
            emdrMovementType = EMDRMovementTypes.SIMPLE_VERTICAL;
        } else if (emdr_movement_type.equals("Circular")) {
            emdrMovementType = EMDRMovementTypes.CIRCULAR;
        } else if (emdr_movement_type.equals("Figure of eight")) {
            emdrMovementType = EMDRMovementTypes.FIGURE_OF_EIGHT;
        }

        if (emdr_total_duration.equals("10 seconds")) {
            EMDR_TOTAL_DURATION = 10000;
        } else if (emdr_total_duration.equals("15 seconds")) {
            EMDR_TOTAL_DURATION = 15000;
        } else if (emdr_total_duration.equals("20 seconds")) {
            EMDR_TOTAL_DURATION = 20000;
        } else if (emdr_total_duration.equals("25 seconds")) {
            EMDR_TOTAL_DURATION = 25000;
        } else if (emdr_total_duration.equals("30 seconds")) {
            EMDR_TOTAL_DURATION = 30000;
        }

        SHADOW_ON = emdr_shadow.equals("On");

        EMDR_REPEATS = (int) EMDR_TOTAL_DURATION / EMDR_DURATION;

        //ticking sound used in conjunction with eye movement
        mediaPlayer = MediaPlayer.create(this, R.raw.ticksound);

        final Handler handler = new Handler();

        //found that a delay was required so the audio lined up with the emdr movement
        //TODO: sync ticking with movement for different speeds
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
                if (n <= EMDR_REPEATS) {
                    if (n >= 0 && n % 2 == 1) {
                        handler.postDelayed(loopingRunnable, delay);
                        //tick in right headphone
                        mediaPlayer.setVolume(0.f, 1.f);
                        n++;
                    } else if (n >= 0 && n % 2 == 0) {
                        handler.postDelayed(loopingRunnable, delay);
                        //tick in left headphone
                        mediaPlayer.setVolume(1.f, 0.f);
                        n++;
                    }
                } else {
                    animSet.cancel();
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
        final View emdrCircleView = findViewById(R.id.emdr_circle_layout);
        //View emdrCircleView = findViewById(R.id.emdrcircle);
        //ImageView emdrCircleImageView = (ImageView) findViewById(R.id.emdr_circle_layout);

        //horizontal movement (simple harmonic)
        ObjectAnimator horizontal_Centre_To_Right = ObjectAnimator.ofFloat(emdrCircleView, "x", (SCREEN_WIDTH - emdrCircleDiameter)/2, (SCREEN_WIDTH - emdrCircleDiameter));
        ObjectAnimator horizontal_Right_To_Centre = ObjectAnimator.ofFloat(emdrCircleView, "x", (SCREEN_WIDTH - emdrCircleDiameter), (SCREEN_WIDTH - emdrCircleDiameter)/2);
        ObjectAnimator horizontal_Centre_To_Left = ObjectAnimator.ofFloat(emdrCircleView, "x", (SCREEN_WIDTH - emdrCircleDiameter)/2, 0);
        ObjectAnimator horizontal_Left_To_Centre = ObjectAnimator.ofFloat(emdrCircleView, "x", 0, (SCREEN_WIDTH - emdrCircleDiameter)/2);

        horizontal_Centre_To_Right.setInterpolator(new DecelerateInterpolator());
        horizontal_Right_To_Centre.setInterpolator(new AccelerateInterpolator());
        horizontal_Centre_To_Left.setInterpolator(new DecelerateInterpolator());
        horizontal_Left_To_Centre.setInterpolator(new AccelerateInterpolator());


        final View emdrCircleShadowView = findViewById(R.id.emdr_circle_shadow_layout);
        if (SHADOW_ON) {
            emdrCircleShadowView.setVisibility(View.VISIBLE);

        } else {
            emdrCircleShadowView.setVisibility(View.INVISIBLE);
        }
        //View emdrCircleView = findViewById(R.id.emdrcircle);
        //ImageView emdrCircleImageView = (ImageView) findViewById(R.id.emdr_circle_layout);

        //horizontal movement (simple harmonic) for shadow
        ObjectAnimator horizontal_Centre_To_Right_Shadow = ObjectAnimator.ofFloat(emdrCircleShadowView, "x", (SCREEN_WIDTH - emdrCircleDiameter)/2, ((SCREEN_WIDTH - emdrCircleDiameter)+(SCREEN_WIDTH - emdrCircleDiameter)/12));
        ObjectAnimator horizontal_Right_To_Centre_Shadow = ObjectAnimator.ofFloat(emdrCircleShadowView, "x", ((SCREEN_WIDTH - emdrCircleDiameter)+(SCREEN_WIDTH - emdrCircleDiameter)/12), (SCREEN_WIDTH - emdrCircleDiameter)/2);
        ObjectAnimator horizontal_Centre_To_Left_Shadow = ObjectAnimator.ofFloat(emdrCircleShadowView, "x", (SCREEN_WIDTH - emdrCircleDiameter)/2, (-(SCREEN_WIDTH - emdrCircleDiameter)/12));
        ObjectAnimator horizontal_Left_To_Centre_Shadow = ObjectAnimator.ofFloat(emdrCircleShadowView, "x", (-(SCREEN_WIDTH - emdrCircleDiameter)/12), (SCREEN_WIDTH - emdrCircleDiameter)/2);

        horizontal_Centre_To_Right_Shadow.setInterpolator(new DecelerateInterpolator());
        horizontal_Right_To_Centre_Shadow.setInterpolator(new AccelerateInterpolator());
        horizontal_Centre_To_Left_Shadow.setInterpolator(new DecelerateInterpolator());
        horizontal_Left_To_Centre_Shadow.setInterpolator(new AccelerateInterpolator());



        //repeat of horizontal movement animation. Used in figure-of-eight movement, which requires two horizontal cycles with each vertical cycle
        ObjectAnimator horizontal_Centre_To_Right2 = ObjectAnimator.ofFloat(emdrCircleView, "x", (SCREEN_WIDTH - emdrCircleDiameter)/2, (SCREEN_WIDTH - emdrCircleDiameter));
        ObjectAnimator horizontal_Right_To_Centre2 = ObjectAnimator.ofFloat(emdrCircleView, "x", (SCREEN_WIDTH - emdrCircleDiameter), (SCREEN_WIDTH - emdrCircleDiameter)/2);
        ObjectAnimator horizontal_Centre_To_Left2 = ObjectAnimator.ofFloat(emdrCircleView, "x", (SCREEN_WIDTH - emdrCircleDiameter)/2, 0);
        ObjectAnimator horizontal_Left_To_Centre2 = ObjectAnimator.ofFloat(emdrCircleView, "x", 0, (SCREEN_WIDTH - emdrCircleDiameter)/2);

        horizontal_Centre_To_Right2.setInterpolator(new DecelerateInterpolator());
        horizontal_Right_To_Centre2.setInterpolator(new AccelerateInterpolator());
        horizontal_Centre_To_Left2.setInterpolator(new DecelerateInterpolator());
        horizontal_Left_To_Centre2.setInterpolator(new AccelerateInterpolator());

        //repeat of horizontal movement for shadow
        ObjectAnimator horizontal_Centre_To_Right_Shadow2 = ObjectAnimator.ofFloat(emdrCircleShadowView, "x", (SCREEN_WIDTH - emdrCircleDiameter)/2, ((SCREEN_WIDTH - emdrCircleDiameter)+(SCREEN_WIDTH - emdrCircleDiameter)/12));
        ObjectAnimator horizontal_Right_To_Centre_Shadow2 = ObjectAnimator.ofFloat(emdrCircleShadowView, "x", ((SCREEN_WIDTH - emdrCircleDiameter)+(SCREEN_WIDTH - emdrCircleDiameter)/12), (SCREEN_WIDTH - emdrCircleDiameter)/2);
        ObjectAnimator horizontal_Centre_To_Left_Shadow2 = ObjectAnimator.ofFloat(emdrCircleShadowView, "x", (SCREEN_WIDTH - emdrCircleDiameter)/2, (-(SCREEN_WIDTH - emdrCircleDiameter)/12));
        ObjectAnimator horizontal_Left_To_Centre_Shadow2 = ObjectAnimator.ofFloat(emdrCircleShadowView, "x", (-(SCREEN_WIDTH - emdrCircleDiameter)/12), (SCREEN_WIDTH - emdrCircleDiameter)/2);

        horizontal_Centre_To_Right_Shadow2.setInterpolator(new DecelerateInterpolator());
        horizontal_Right_To_Centre_Shadow2.setInterpolator(new AccelerateInterpolator());
        horizontal_Centre_To_Left_Shadow2.setInterpolator(new DecelerateInterpolator());
        horizontal_Left_To_Centre_Shadow2.setInterpolator(new AccelerateInterpolator());



        //vertical movement
        ObjectAnimator vertical_Top_To_Centre = ObjectAnimator.ofFloat(emdrCircleView, "y", 0, (SCREEN_HEIGHT - 2*emdrCircleDiameter)/2);
        ObjectAnimator vertical_Centre_To_Bottom = ObjectAnimator.ofFloat(emdrCircleView, "y", (SCREEN_HEIGHT - 2*emdrCircleDiameter)/2, (SCREEN_HEIGHT - 2*emdrCircleDiameter));
        ObjectAnimator vertical_Bottom_To_Centre = ObjectAnimator.ofFloat(emdrCircleView, "y", (SCREEN_HEIGHT - 2*emdrCircleDiameter), (SCREEN_HEIGHT - 2*emdrCircleDiameter)/2);
        ObjectAnimator vertical_Centre_To_Top = ObjectAnimator.ofFloat(emdrCircleView, "y", (SCREEN_HEIGHT - 2*emdrCircleDiameter)/2, 0);

        vertical_Top_To_Centre.setInterpolator(new AccelerateInterpolator());
        vertical_Centre_To_Bottom.setInterpolator(new DecelerateInterpolator());
        vertical_Bottom_To_Centre.setInterpolator(new AccelerateInterpolator());
        vertical_Centre_To_Top.setInterpolator(new DecelerateInterpolator());

        //vertical movement for shadow
        ObjectAnimator vertical_Top_To_Centre_Shadow = ObjectAnimator.ofFloat(emdrCircleShadowView, "y", (-(SCREEN_HEIGHT - 2*emdrCircleDiameter)/15), (SCREEN_HEIGHT - 2*emdrCircleDiameter)/2);
        ObjectAnimator vertical_Centre_To_Bottom_Shadow = ObjectAnimator.ofFloat(emdrCircleShadowView, "y", (SCREEN_HEIGHT - 2*emdrCircleDiameter)/2, ((SCREEN_HEIGHT - 2*emdrCircleDiameter) + (SCREEN_HEIGHT - 2*emdrCircleDiameter)/15));
        ObjectAnimator vertical_Bottom_To_Centre_Shadow = ObjectAnimator.ofFloat(emdrCircleShadowView, "y", ((SCREEN_HEIGHT - 2*emdrCircleDiameter) + (SCREEN_HEIGHT - 2*emdrCircleDiameter)/15), (SCREEN_HEIGHT - 2*emdrCircleDiameter)/2);
        ObjectAnimator vertical_Centre_To_Top_Shadow = ObjectAnimator.ofFloat(emdrCircleShadowView, "y", (SCREEN_HEIGHT - 2*emdrCircleDiameter)/2, (-(SCREEN_HEIGHT - 2*emdrCircleDiameter)/15));

        vertical_Top_To_Centre_Shadow.setInterpolator(new AccelerateInterpolator());
        vertical_Centre_To_Bottom_Shadow.setInterpolator(new DecelerateInterpolator());
        vertical_Bottom_To_Centre_Shadow.setInterpolator(new AccelerateInterpolator());
        vertical_Centre_To_Top_Shadow.setInterpolator(new DecelerateInterpolator());


        //final AnimatorSet animSet = new AnimatorSet();

        //play animation corresponding to user selection
        //commented-out code is an implementation without shadow. Left in as it may be useful later
        if (emdrMovementType.equals(EMDRMovementTypes.SIMPLE_VERTICAL)) {
//                   animSet.play(vertical_Top_To_Centre).before(vertical_Centre_To_Bottom);
//                   animSet.play(vertical_Bottom_To_Centre).after(vertical_Centre_To_Bottom).before(vertical_Centre_To_Top);
            animSet.play(vertical_Top_To_Centre).with(vertical_Top_To_Centre_Shadow);
            animSet.play(vertical_Centre_To_Bottom).after(vertical_Top_To_Centre).with(vertical_Centre_To_Bottom_Shadow);
            animSet.play(vertical_Bottom_To_Centre).after(vertical_Centre_To_Bottom).with(vertical_Bottom_To_Centre_Shadow);
            animSet.play(vertical_Centre_To_Top).after(vertical_Bottom_To_Centre).with(vertical_Centre_To_Top_Shadow);
            animSet.setDuration(EMDR_DURATION/2);
        } else if (emdrMovementType.equals(EMDRMovementTypes.SIMPLE_HORIZONTAL)) {
//            animSet.play(horizontal_Centre_To_Right).before(horizontal_Right_To_Centre);
//            animSet.play(horizontal_Centre_To_Left).after(horizontal_Right_To_Centre).before(horizontal_Left_To_Centre);
            animSet.play(horizontal_Centre_To_Right).with(horizontal_Centre_To_Right_Shadow);
            animSet.play(horizontal_Right_To_Centre).after(horizontal_Centre_To_Right).with(horizontal_Right_To_Centre_Shadow);
            animSet.play(horizontal_Centre_To_Left).after(horizontal_Right_To_Centre).with(horizontal_Centre_To_Left_Shadow);
            animSet.play(horizontal_Left_To_Centre).after(horizontal_Centre_To_Left).with(horizontal_Left_To_Centre_Shadow);
            animSet.setDuration(EMDR_DURATION/2);
        } else if (emdrMovementType.equals(EMDRMovementTypes.CIRCULAR)) {
//            animSet.play(horizontal_Centre_To_Right).with(vertical_Top_To_Centre);
//            animSet.play(horizontal_Right_To_Centre).after(horizontal_Centre_To_Right).with(vertical_Centre_To_Bottom);
//            animSet.play(horizontal_Centre_To_Left).after(horizontal_Right_To_Centre).with(vertical_Bottom_To_Centre);
//            animSet.play(horizontal_Left_To_Centre).after(horizontal_Centre_To_Left).with(vertical_Centre_To_Top);
            animSet.play(horizontal_Centre_To_Right).with(vertical_Top_To_Centre).with(horizontal_Centre_To_Right_Shadow).with(vertical_Top_To_Centre_Shadow);
            animSet.play(horizontal_Right_To_Centre).after(horizontal_Centre_To_Right).with(vertical_Centre_To_Bottom).with(horizontal_Right_To_Centre_Shadow).with(vertical_Centre_To_Bottom_Shadow);
            animSet.play(horizontal_Centre_To_Left).after(horizontal_Right_To_Centre).with(vertical_Bottom_To_Centre).with(horizontal_Centre_To_Left_Shadow).with(vertical_Bottom_To_Centre_Shadow);
            animSet.play(horizontal_Left_To_Centre).after(horizontal_Centre_To_Left).with(vertical_Centre_To_Top).with(horizontal_Left_To_Centre_Shadow).with(vertical_Centre_To_Top_Shadow);
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

            vertical_Top_To_Centre_Shadow.setDuration(EMDR_DURATION);
            vertical_Centre_To_Bottom_Shadow.setDuration(EMDR_DURATION);
            vertical_Bottom_To_Centre_Shadow.setDuration(EMDR_DURATION);
            vertical_Centre_To_Top_Shadow.setDuration(EMDR_DURATION);

            horizontal_Centre_To_Right_Shadow.setDuration(EMDR_DURATION/2);
            horizontal_Right_To_Centre_Shadow.setDuration(EMDR_DURATION/2);
            horizontal_Centre_To_Left_Shadow.setDuration(EMDR_DURATION/2);
            horizontal_Left_To_Centre_Shadow.setDuration(EMDR_DURATION/2);


            horizontal_Centre_To_Right_Shadow2.setDuration(EMDR_DURATION/2);
            horizontal_Right_To_Centre_Shadow2.setDuration(EMDR_DURATION/2);
            horizontal_Centre_To_Left_Shadow2.setDuration(EMDR_DURATION/2);
            horizontal_Left_To_Centre_Shadow2.setDuration(EMDR_DURATION/2);

//            animSet.play(horizontal_Centre_To_Right).with(vertical_Top_To_Centre).before(horizontal_Right_To_Centre);
//            animSet.play(horizontal_Centre_To_Left).with(vertical_Centre_To_Bottom).before(horizontal_Left_To_Centre).after(horizontal_Right_To_Centre);
//            animSet.play(horizontal_Centre_To_Right2).with(vertical_Bottom_To_Centre).before(horizontal_Right_To_Centre2).after(horizontal_Left_To_Centre);
//            animSet.play(horizontal_Centre_To_Left2).with(vertical_Centre_To_Top).before(horizontal_Left_To_Centre2).after(horizontal_Right_To_Centre2);

            animSet.play(horizontal_Centre_To_Right).with(horizontal_Centre_To_Right_Shadow).with(vertical_Top_To_Centre).with(vertical_Top_To_Centre_Shadow);
            animSet.play(horizontal_Right_To_Centre).after(horizontal_Centre_To_Right).with(horizontal_Right_To_Centre_Shadow);
            animSet.play(horizontal_Centre_To_Left).after(horizontal_Right_To_Centre).with(vertical_Centre_To_Bottom).with(vertical_Centre_To_Bottom_Shadow).with(horizontal_Centre_To_Left_Shadow);
            animSet.play(horizontal_Left_To_Centre).with(horizontal_Left_To_Centre_Shadow).after(horizontal_Centre_To_Left);

            animSet.play(horizontal_Centre_To_Right2).with(horizontal_Centre_To_Right_Shadow2).with(vertical_Bottom_To_Centre).with(vertical_Bottom_To_Centre_Shadow).after(horizontal_Left_To_Centre);
            animSet.play(horizontal_Right_To_Centre2).after(horizontal_Centre_To_Right2).with(horizontal_Right_To_Centre_Shadow2);
            animSet.play(horizontal_Centre_To_Left2).after(horizontal_Right_To_Centre2).with(vertical_Centre_To_Top).with(vertical_Centre_To_Top_Shadow).with(horizontal_Centre_To_Left_Shadow2);
            animSet.play(horizontal_Left_To_Centre2).with(horizontal_Left_To_Centre_Shadow2).after(horizontal_Centre_To_Left2);
        }


        //play animation until cancelled
        animSet.addListener(new AnimatorListenerAdapter() {

            private boolean cancelled;

            @Override
            public void onAnimationStart(Animator animation) {
                cancelled = false;
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                cancelled = true;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                //super.onAnimationEnd(animation);
                if (!cancelled) {
                    animSet.start();
                } else {
                    emdrCircleView.setX(SCREEN_WIDTH/2);
                    emdrCircleView.setY(SCREEN_HEIGHT/2);

                    emdrCircleShadowView.setX(SCREEN_WIDTH/2);
                    emdrCircleShadowView.setY(SCREEN_HEIGHT/2);

                }
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


    //Add user records by Jason
    public void addRecords(){
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = new Date();
            SQLiteDatabase mySqlDB = DBManager.getInstance(this).getWritableDatabase();
            mySqlDB.execSQL("CREATE TABLE IF NOT EXISTS userRecords(UID VARCHAR,TIME VARCHAR, MODULE VARCHAR,PRIMARY KEY (UID, TIME));");
            String insertQuery = "INSERT INTO userRecords (uid, TIME, MODULE) VALUES(?,?,?)";
            mySqlDB.execSQL(insertQuery, new String[]{uid, dateFormat.format(date), "EMDR"});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    }

