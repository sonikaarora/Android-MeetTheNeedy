package com.sonikaarora.meettheneedy.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sonikaarora.meettheneedy.database.DataBaseAdapter;
import com.sonikaarora.meettheneedy.MainActivity;
import com.sonikaarora.meettheneedy.R;

/**
 * Created by sonikaarora on 6/27/16.
 * This activity will help the user to do registration, user is asked to enter username, password and confirm password.
 * Validations are also present that will ensure user enters the same password and confirm password.
 * Sqlite database is used to used to store username and password, and on successful registration user credentials are saved in sqlite database.
 *
 */
public class SignUpActivity extends Activity{
    EditText userName;
    EditText password;
    EditText confirmpassword;
    Button create;
    Context context = this;
    DataBaseAdapter dataBaseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        dataBaseAdapter = new DataBaseAdapter(this);
        dataBaseAdapter = dataBaseAdapter.open();
        userName = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        confirmpassword = (EditText) findViewById(R.id.conformpassword);
        create = (Button) findViewById(R.id.create);

        create.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String user = userName.getText().toString();
                String pwd = password.getText().toString();
                String cfmPwd = confirmpassword.getText().toString();

                if(user.equals("") || pwd.equals("") || cfmPwd.equals(""))
                {

                    Toast.makeText(getApplicationContext(),"Please provide all fields",Toast.LENGTH_LONG).show();
                }else if(!pwd.equals(cfmPwd))
                {
                    Toast.makeText(getApplicationContext(),"Passwords does not match",Toast.LENGTH_LONG).show();
                }else
                {

                    dataBaseAdapter.insertEntry(user,pwd);
                    Toast.makeText(getApplicationContext(),"Account Creation successful",Toast.LENGTH_LONG).show();
                    /*
                    On successful registration, user is redirected to login activity
                     */
                    Intent mainActivityIntent = new Intent(SignUpActivity.this, LoginActivity.class);
                    startActivity(mainActivityIntent);
                    finish();
                }
            }
        });

    }
}

