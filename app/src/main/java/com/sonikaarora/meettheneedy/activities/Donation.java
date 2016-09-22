package com.sonikaarora.meettheneedy.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.sonikaarora.meettheneedy.R;

/**
 * Created by sonikaarora on 7/2/16.
 * This activity enables the user to do selection for the amount that user wants to donate.
 */
public class Donation extends Activity {

    Button give;
    final Context context = this;
    private boolean loginFlag = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.donation);

        Intent intent = getIntent();
        if (null != intent) { //Null Checking
            loginFlag = intent.getBooleanExtra("loginflag", false);
        }

        give = (Button) findViewById(R.id.give);

        give.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!loginFlag)
                {
                    Toast.makeText(getApplicationContext(),"You need to login to application before donatiing",Toast.LENGTH_LONG).show();
                    return;
                }


                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setMessage("Are you sure,You want to go with this amount for donation");

                alertDialogBuilder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                        Toast.makeText(getApplicationContext(),"You bought smile to someone today.. CONGRATS",Toast.LENGTH_LONG).show();
                    }
                });

                alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();



            }
        });
    }
}
