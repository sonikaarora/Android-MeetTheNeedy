package com.sonikaarora.meettheneedy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.sonikaarora.meettheneedy.activities.LoginActivity;
import com.sonikaarora.meettheneedy.activities.SignUpActivity;
import com.sonikaarora.meettheneedy.fragments.SearchFragment;
import com.sonikaarora.meettheneedy.fragments.TabsFragment;

import java.util.List;

public class MainActivity extends AppCompatActivity  {

    DrawerLayout drawerLayout;
    NavigationView menuLayout;
    public  static FragmentManager fragmentManager;
    FragmentTransaction mFragmentTransaction;
    private  static ActionBar ab;
    private TabsFragment tabFragment;
    private String title = "Non Profit Organizations";
    public static boolean loginflag = false;


    public static String getActionBarTitle()
    {
        if(ab.getTitle() == null || ab.getTitle().equals(""))
        {
            ab.setTitle("Non Profit Organizations");
        }
        return ab.getTitle().toString();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        menuLayout = (NavigationView) findViewById(R.id.menuid) ;

        /**
         * Lets inflate the very first fragment
         * Here , we are inflating the TabFragment as the first Fragment
         */

        fragmentManager = getSupportFragmentManager();
        mFragmentTransaction = fragmentManager.beginTransaction();
        tabFragment = new TabsFragment();
        mFragmentTransaction.add(R.id.containerView,tabFragment, "tabFrag");
        mFragmentTransaction.replace(R.id.containerView,tabFragment).commit();


        /*
         User can make selection to donate either to Non Profit organization or to libraries. User selection is send as extra
         information to intent before re-invoking the MainActivity. Thus title should be picked from Intent.
         */

        Intent intent = getIntent();
        if (null != intent) { //Null Checking
            title= intent.getStringExtra("title");
            loginflag = intent.getBooleanExtra("login", false);
        }

        /**
         * Setup click events on the Navigation View Items.
         */

        menuLayout.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                drawerLayout.closeDrawers();

                if (menuItem.getItemId() == R.id.signup) {

                    Intent intentSignUp = new Intent(getApplicationContext(),
                            SignUpActivity.class);
                    startActivity(intentSignUp);

                }
                else if (menuItem.getItemId() == R.id.signin) {
                    Intent intentLogin = new Intent(getApplicationContext(),
                            LoginActivity.class);
                    startActivity(intentLogin);

                }
                else if (menuItem.getItemId() == R.id.ngo) {
                  ab.setTitle("Non Profit Organizations"); // This will set the title in actionbar that is visible to the user.

                    /*
                    User selection is put as bundle in Intent, before re-invoking MainActivity.
                    MainActivity is re-invoked here as search fragment needs to display the locations according to user selection.
                     */
                    Intent mainactivityintent = new Intent(getApplicationContext(),
                            MainActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putCharSequence("title","Non Profit Organizations");
                    bundle.putBoolean("login",loginflag);
                    mainactivityintent.putExtras(bundle);

                    startActivity(mainactivityintent);



                }
                else if (menuItem.getItemId() == R.id.libraries) {
                    ab.setTitle("Libraries");
                    Bundle bundle = new Bundle();
                    bundle.putCharSequence("title","Libraries");
                    bundle.putBoolean("login",loginflag);
                    Intent mainactivityintent = new Intent(getApplicationContext(),
                            MainActivity.class);
                    mainactivityintent.putExtras(bundle);
                    startActivity(mainactivityintent);
                }

                return false;
            }

        });

        /**
         * Setup Drawer Toggle of the Toolbar
         */

        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);


//        setSupportActionBar(toolbar);
        ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,R.string.app_name,
                R.string.app_name);


        ((Toolbar) findViewById(R.id.toolbar)).setTitle(title);


        drawerLayout.setDrawerListener(mDrawerToggle);

        mDrawerToggle.syncState();

    }
}
