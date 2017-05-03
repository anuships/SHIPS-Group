package com.example.ships.myapplication.OtherInterfaces;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.ships.myapplication.R;

public class UpdateUserDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user_detail);
    }

    public void cencel(View view) {
        super.onBackPressed();
    }

    public void updateUserDetail(View view) {
        startActivity(new Intent(this, Updated.class));
    }
}
