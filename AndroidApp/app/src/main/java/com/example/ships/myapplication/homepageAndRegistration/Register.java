package com.example.ships.myapplication.homepageAndRegistration;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.ships.myapplication.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.UUID;
public class Register extends AppCompatActivity {
    private static String firstNameB;
    private static String lastNameB;
    private static String emailB;
    private static String uid;
    private ProgressDialog pDialog;
    private SessionManager session;
    private SQLiteDatabase mySqlDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        mySqlDB = DBManager.getInstance(this).getWritableDatabase();
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
        EditText userName = (EditText) findViewById(R.id.preferNameInput);

        TextView emailtext = (TextView) findViewById(R.id.emailText);
        TextView pwtext = (TextView) findViewById(R.id.pwView);
        TextView comfirmpwtext = (TextView) findViewById(R.id.pwConfirmView);
        TextView firstNametext = (TextView) findViewById(R.id.firstNameView);
        TextView lastNametext = (TextView) findViewById(R.id.lastNameView);
        TextView userNametext = (TextView) findViewById(R.id.preferNameView);

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
            userNametext.setTextColor(Color.BLACK);
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
            userNametext.setTextColor(Color.BLACK);
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
            userNametext.setTextColor(Color.BLACK);
            if (firstName.length() == 0)
            {
                firstNametext.setTextColor(Color.RED);
            }
            if (lastName.length() == 0)
            {
                lastNametext.setTextColor(Color.RED);
            }
        }else if (userName.getText().toString().length() == 0)
        {
            Context context = getApplicationContext();
            CharSequence text = "You must enter a Valid Username";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            emailtext.setTextColor(Color.BLACK);
            pwtext.setTextColor(Color.BLACK);
            comfirmpwtext.setTextColor(Color.BLACK);
            firstNametext.setTextColor(Color.BLACK);
            lastNametext.setTextColor(Color.BLACK);
                userNametext.setTextColor(Color.RED);
            userNametext.setTextColor(Color.BLACK);
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
            String username = userName.getText().toString();
            String salt;
            String hashedPassword;


            try {
                SessionManager session = new SessionManager(getApplicationContext());
                String q = "SELECT uid FROM users WHERE EMAIL = ? or USERNAME=?";
                Cursor results = mySqlDB.rawQuery(q, new String[]{email.getText().toString(), username});
                if (results.getCount() > 0){
                    Context context = getApplicationContext();
                    CharSequence text = "Error: Email/Or Username already used";
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
                registerUser(username, firstName.getText().toString(), lastName.getText().toString(),email.getText().toString(), pw.getText().toString());

            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }
    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }
    private void registerSuccess(String email, String uuid, String lastName, String firstName){
        emailB = email;
        lastNameB = lastName;
        firstNameB = firstName;
        uid = uuid;
        Intent in = new Intent(this, RegisterSuccess.class);
        in.putExtras(createBundle());
        startActivity(in);
    }
    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
    private void registerUser(final String username, final String firstName, final String lastName, final String email,
                              final String password) {
        // Tag used to cancel the request
        String tag_string_req = "req_register";

        pDialog.setMessage("Registering ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_REGISTER, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                System.out.println("Register Response: " + response.toString());
                response = response.substring(response.indexOf('{'));
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (!error) {
                        String uid = jObj.getString("uid");
                        JSONObject user = jObj.getJSONObject("user");
                        String username = user.getString("username");
                        String email = user.getString("email");
                        String firstName = user.getString("display_name");
                        String lastName = " ";
                        if (firstName.contains(" ")){
                            lastName = firstName.substring(firstName.indexOf(' ')+1);
                            firstName = firstName.substring(0, firstName.indexOf(' '));
                        }

                        String created_at = user.getString("created_at");
                        // Inserting row in users table
                        DBManager.insertUser(uid, username,email, firstName, lastName, password, mySqlDB);
                        Toast.makeText(getApplicationContext(), "User successfully registered. Try login now!", Toast.LENGTH_LONG).show();
                        // Launch login activity
                        registerSuccess(email, uid, lastName, firstName);
                         } else {

                        // Error occurred in registration. Get the error
                        // message
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("Registration Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("username", username);
                params.put("firstname", firstName);
                params.put("lastname", lastName);
                params.put("email", email);
                params.put("password", password);
                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
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
