package com.sonikaarora.meettheneedy.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sonikaarora.meettheneedy.MainActivity;
import com.sonikaarora.meettheneedy.R;
import com.sonikaarora.meettheneedy.database.DataBaseAdapter;

/**
 * Created by sonikaarora on 6/27/16.
 * This activity will take the inputs from the user like username and password, validate it from sqlite database and on
 * successful login, user is redirected to  MainActivity.
 */
public class LoginActivity extends Activity {
    EditText userName;
    EditText password;
    Button login;
    DataBaseAdapter dataBaseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        dataBaseAdapter = new DataBaseAdapter(this);
        dataBaseAdapter = dataBaseAdapter.open();
        userName = (EditText) findViewById(R.id.usernameLogin);
        password = (EditText) findViewById(R.id.passwordLogin);
        login = (Button) findViewById(R.id.loginbutton);

        login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String user = userName.getText().toString();
                String pwd = password.getText().toString();

                if(user.equals("") || pwd.equals(""))
                {

                    Toast.makeText(getApplicationContext(),"Please provide all fields",Toast.LENGTH_LONG).show();
                }else
                {
                    String pass = dataBaseAdapter.getSinlgeEntry(user);
                    if(!pass.equals(pwd))
                    {
                        Toast.makeText(getApplicationContext(),"No such user registered.",Toast.LENGTH_LONG).show();
                        ((EditText) findViewById(R.id.usernameLogin)).setText("");
                        ((EditText) findViewById(R.id.passwordLogin)).setText("");
                    }
                    else
                    {
                        /*
                        On successful login, user is redirected to MainActivity
                         */
                        Bundle bundle = new Bundle();
                        bundle.putBoolean("login",true);
                        Toast.makeText(getApplicationContext(),"Login Successful",Toast.LENGTH_LONG).show();
                        Intent mainActivityIntent = new Intent(LoginActivity.this, MainActivity.class);
                        mainActivityIntent.putExtras(bundle);
                        startActivity(mainActivityIntent);
                        finish();
                    }

                }
            }
        });

    }
}
