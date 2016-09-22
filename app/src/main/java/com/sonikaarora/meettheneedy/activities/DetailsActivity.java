package com.sonikaarora.meettheneedy.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.sonikaarora.meettheneedy.R;

/**
 * Created by sonikaarora on 7/2/16.
 */
public class DetailsActivity extends Activity
{
    Button donate;
    boolean loginflag = false;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ngodonation);
        Intent intent = getIntent();
        if (null != intent) { //Null Checking
            loginflag = intent.getBooleanExtra("login", false);
        }


        donate = (Button) findViewById(R.id.donatengo);

        donate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                User is redirected to Donation activity.
                 */
                Bundle bundle = new Bundle();
                bundle.putBoolean("loginflag",loginflag);
                Intent intent = new Intent(DetailsActivity.this, Donation.class);
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
            }
        });
    }

}
