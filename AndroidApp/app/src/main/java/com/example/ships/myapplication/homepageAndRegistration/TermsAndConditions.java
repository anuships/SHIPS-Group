package com.example.ships.myapplication.homepageAndRegistration;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

import com.example.ships.myapplication.R;

public class TermsAndConditions extends AppCompatActivity {
    boolean readAndAgree = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.termsandconditions);
    }

    public void onCheckboxClicked(View view) {
        boolean checked = ((CheckBox) view).isChecked();
        switch (view.getId()) {
            case R.id.checkBox:
                if (checked) {
                    readAndAgree = true;
                }
                else
                {
                    readAndAgree = false;
                }
        }
    }

    public void back(View v)
    {
        startActivity(new Intent(this, MainActivity.class));
    }

    public void next(View v)
    {
        if (readAndAgree) {
            startActivity(new Intent(this, Register.class));
        }
        else
        {
            Context context = getApplicationContext();
            CharSequence text = "Please read the terms and conditions before registration";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
    }
}
