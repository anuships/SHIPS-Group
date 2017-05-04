package com.example.ships.myapplication.exposure;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.ships.myapplication.GSR.GSRGraphActivity;
import com.example.ships.myapplication.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

public class ExposureTherapy extends AppCompatActivity {
    boolean showImage = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_exposure_therapy);
        ImageView v = (ImageView) findViewById(R.id.imageView1);
        InputStream imageIS = this.getResources().openRawResource(R.raw.i1);
        Bitmap myImage = BitmapFactory.decodeStream(imageIS);
        v.setImageBitmap(myImage);
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
            showImage = false;
        }
        else{
            ImageView v3 = (ImageView) findViewById(R.id.imageView1);
            InputStream imageIS = this.getResources().openRawResource(R.raw.i1);
            Bitmap myImage = BitmapFactory.decodeStream(imageIS);
            v3.setImageBitmap(myImage);
            showImage = true;
            Button b = (Button) findViewById(R.id.button2);
            b.setText("Stop");
        }
    }
    public void goToRelaxationTraning(View v){
        startActivity(new Intent(this, GSRGraphActivity.class));
    }

    public void selectLevel(View v){
        Button b3 = (Button) findViewById(R.id.button3);
        String filename = "exposure.txt";
        try {
            FileOutputStream output = openFileOutput(filename, MODE_PRIVATE);
            output.write("ExposureLevel:".getBytes());
            output.write(b3.getText().toString().getBytes());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }


}
