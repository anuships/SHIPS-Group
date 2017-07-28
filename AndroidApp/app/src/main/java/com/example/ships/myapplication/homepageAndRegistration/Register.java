package com.example.ships.myapplication.homepageAndRegistration;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ships.myapplication.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.UUID;
public class Register extends AppCompatActivity {
    private static String firstNameB;
    private static String lastNameB;
    private static String emailB;
    private static String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    public void back(View v)
    {
        startActivity(new Intent(this, TermsAndConditions.class));
    }

    public void finish(View v)
    {
        EditText email = (EditText) findViewById(R.id.emailInput);
        EditText pw = (EditText) findViewById(R.id.pwInput);
        EditText comfirmpw = (EditText) findViewById(R.id.pwConfirmInput);
        EditText firstName = (EditText) findViewById(R.id.firstNameInput);
        EditText lastName = (EditText) findViewById(R.id.lastNameInput);

        TextView emailtext = (TextView) findViewById(R.id.emailText);
        TextView pwtext = (TextView) findViewById(R.id.pwView);
        TextView comfirmpwtext = (TextView) findViewById(R.id.pwConfirmView);
        TextView firstNametext = (TextView) findViewById(R.id.firstNameView);
        TextView lastNametext = (TextView) findViewById(R.id.lastNameView);

        if (!isEmailValid(email.getText().toString()))
        {
            Context context = getApplicationContext();
            CharSequence text = "Please enter a valid e-mail";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            emailtext.setTextColor(Color.RED);
            pwtext.setTextColor(Color.BLACK);
            comfirmpwtext.setTextColor(Color.BLACK);
            firstNametext.setTextColor(Color.BLACK);
            lastNametext.setTextColor(Color.BLACK);
        }
        else if ((pw.getText().toString().length() == 0) || !pw.getText().toString().equals(comfirmpw.getText().toString()))
        {
            Context context = getApplicationContext();
            CharSequence text = "Please enter a password and make sure your password is consistent";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            emailtext.setTextColor(Color.BLACK);
            pwtext.setTextColor(Color.RED);
            comfirmpwtext.setTextColor(Color.RED);
            firstNametext.setTextColor(Color.BLACK);
            lastNametext.setTextColor(Color.BLACK);
        }
        else if (firstName.length() == 0 || lastName.length() == 0 )
        {
            Context context = getApplicationContext();
            CharSequence text = "You must enter your first and last name";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            emailtext.setTextColor(Color.BLACK);
            pwtext.setTextColor(Color.BLACK);
            comfirmpwtext.setTextColor(Color.BLACK);
            firstNametext.setTextColor(Color.BLACK);
            lastNametext.setTextColor(Color.BLACK);
            if (firstName.length() == 0)
            {
                firstNametext.setTextColor(Color.RED);
            }
            if (lastName.length() == 0)
            {
                lastNametext.setTextColor(Color.RED);
            }
        }
        else
        {
            //
            SQLiteDatabase mySqlDB = DBManager.getInstance(this).getWritableDatabase();
            email = (EditText) findViewById(R.id.emailInput);
            pw = (EditText) findViewById(R.id.pwInput);
            comfirmpw = (EditText) findViewById(R.id.pwConfirmInput);
            firstName = (EditText) findViewById(R.id.firstNameInput);
            lastName = (EditText) findViewById(R.id.lastNameInput);
            String salt;
            String hashedPassword;
            try {
                String q = "SELECT uid FROM users WHERE email = ?";
                Cursor results = mySqlDB.rawQuery(q, new String[]{email.getText().toString()});
                if (results.getCount() > 0){
                    Context context = getApplicationContext();
                    CharSequence text = "Error: Email already used";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                    emailtext.setTextColor(Color.RED);
                    pwtext.setTextColor(Color.BLACK);
                    comfirmpwtext.setTextColor(Color.BLACK);
                    firstNametext.setTextColor(Color.BLACK);
                    lastNametext.setTextColor(Color.BLACK);
                    return;
                }

                salt = new String(PasswordEncrypter.generateSalt());
                hashedPassword = new String(PasswordEncrypter.encryptPassword(pw.getText().toString(),salt.getBytes()));
                UUID tempID = UUID.randomUUID();
                uid = String.valueOf(tempID);
                System.out.println(uid);
                String insertQuery = "INSERT INTO users (UID, USERNAME, EMAIL, PASSWORD, SALT, FIRST_NAME, LAST_NAME) VALUES(?,' ',?,?,?,?,?)";
                mySqlDB.execSQL(insertQuery, new String[]{uid, email.getText().toString(),hashedPassword, salt,firstName.getText().toString(),lastName.getText().toString()});

                Cursor resultSet = mySqlDB.rawQuery("Select * from users where email=?",new String[]{email.getText().toString()});
                resultSet.moveToFirst();

                emailB = email.getText().toString();
                lastNameB = lastName.getText().toString();
                firstNameB = firstName.getText().toString();
                Intent in = new Intent(this, RegisterSuccess.class);
                in.putExtras(createBundle());
                startActivity(in);
            }catch(Exception e){
                e.printStackTrace();
            }
//            startActivity(new Intent(this,RegisterSuccess.class));
        }
    }
    private Bundle createBundle(){
        Bundle b = new Bundle();
        b.putString("firstName", firstNameB);
        b.putString("uid", uid);
        b.putString("lastName", lastNameB);
        b.putString("email", emailB);
        return b;
    }
    public boolean isEmailValid(String email)
    {
        String regExpn =
                "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                        +"((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                        +"([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                        +"([a-zA-Z0-9]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";

        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(regExpn, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);

        if(matcher.matches())
            return true;
        else
            return false;
    }
}
