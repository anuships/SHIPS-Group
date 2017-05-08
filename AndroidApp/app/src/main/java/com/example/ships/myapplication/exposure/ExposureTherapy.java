package com.example.ships.myapplication.exposure;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.ships.myapplication.GSR.GSRGraphActivity;
import com.example.ships.myapplication.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

public class ExposureTherapy extends AppCompatActivity {
    boolean showImage = true;
    String level = "1";
    Dialog dialog;
    MediaPlayer mp = new MediaPlayer();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exposure_therapy);
        try {
            String filename = "exposure.txt";
            File file = new File(filename);
            if (file.exists()){
                FileInputStream fis = openFileInput(filename);
                InputStreamReader isr = new InputStreamReader(fis);
                BufferedReader bufferedReader = new BufferedReader(isr);
                String line;
                HashMap<String, String> fileContent = new HashMap<>();
                while ((line = bufferedReader.readLine()) != null) {
                    String[] strarr = line.split(":");
                    fileContent.put(strarr[0],strarr[1]);
                }
                Button b3 = (Button) findViewById(R.id.button3);
                level = fileContent.get("ExposureLevel").toString();
                String bText = "Level" + level;
                b3.setText(bText);
                isr.close();
                fis.close();
            }
            else {
                filename = "exposure.txt";
                try {
                    FileOutputStream output = openFileOutput(filename, MODE_PRIVATE);
                    output.write("ExposureLevel:1".getBytes());
                    Button b3 = (Button) findViewById(R.id.button3);
                    String bText = "Level" + level;
                    b3.setText(bText);
                    output.close();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        String resName = "i" + level;
        ImageView v = (ImageView) findViewById(R.id.imageView1);
        InputStream imageIS = this.getResources().openRawResource(getResources().getIdentifier(resName,"raw",this.getPackageName()));
        Bitmap myImage = BitmapFactory.decodeStream(imageIS);
        v.setImageBitmap(myImage);
        if (level.equals("5")){
            mp = MediaPlayer.create(this, R.raw.s1);
            mp.start();
            mp.setLooping(true);
        }
    }

    public void back(View v){
        startActivity(new Intent(this, ExposureDes.class));
    }

    public void stop(View v){
        if (showImage == true){
            ImageView v2 = (ImageView) findViewById(R.id.imageView1);
            Button b = (Button) findViewById(R.id.button2);
            b.setText("Restart");
            v2.setImageResource(0);
            if (mp.isPlaying()) {
                mp.stop();
            }
            showImage = false;
        }
        else{
            ImageView v3 = (ImageView) findViewById(R.id.imageView1);
            String resName = "i" + level;
            InputStream imageIS = this.getResources().openRawResource(getResources().getIdentifier(resName,"raw",this.getPackageName()));
            Bitmap myImage = BitmapFactory.decodeStream(imageIS);
            v3.setImageBitmap(myImage);
            showImage = true;
            Button b = (Button) findViewById(R.id.button2);
            b.setText("Stop");
            if (level.equals("5")){
                    mp = MediaPlayer.create(this, R.raw.s1);
                    mp.start();
                    mp.setLooping(true);
            }
        }
    }
    public void goToRelaxationTraning(View v){
        startActivity(new Intent(this, GSRGraphActivity.class));
    }

    public void selectLevel(View v){
        showRadioButtonDialog();
    }

    private void showRadioButtonDialog() {
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.level_dialog);
        dialog.show();

        RadioGroup rg = (RadioGroup) dialog.findViewById(R.id.levelChosen);

        for(int i=0;i<5;i++){
            RadioButton rb=new RadioButton(this);
            rb.setText(Integer.toString(i+1));
            rg.addView(rb);
        }
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int childCount = group.getChildCount();
                for (int x = 0; x < childCount; x++) {
                    RadioButton btn = (RadioButton) group.getChildAt(x);
                    if (btn.getId() == checkedId) {
                        level = btn.getText().toString();
                    }
                }
            }
        });
    }

    public void changeLevel(View v){
        Button b3 = (Button) findViewById(R.id.button3);
        String bText = "Level" +  level;
        b3.setText(bText);
        String filename = "exposure.txt";
        try {
            FileOutputStream output = openFileOutput(filename, MODE_PRIVATE);
            String fileStr = "ExposureLevel:" + level;
            output.write(fileStr.getBytes());
            output.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        dialog.dismiss();
        ImageView v4 = (ImageView) findViewById(R.id.imageView1);
        String resName = "i" + level;
        InputStream imageIS = this.getResources().openRawResource(getResources().getIdentifier(resName,"raw",this.getPackageName()));
        Bitmap myImage = BitmapFactory.decodeStream(imageIS);
        v4.setImageBitmap(myImage);
        showImage = true;
        if (level.equals("5")){
            mp = MediaPlayer.create(this, R.raw.s1);
            mp.start();
            mp.setLooping(true);
        }
        else {
            if (mp.isPlaying()) {
                mp.stop();
            }
        }
    }




}
