package com.example.eatanddrink;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {




    /**
     * Fragment Name
     */
    static final String HOME_FRA        = "home fragment";
    static final String WIZARD_FRA      = "wizard fragment";
    static final String CATEGORY_FRA    = "category fragment";
    static final String CURRENT_FRA     = "current fragment name";



    /**
     * Bundle key value table
     *      'key'        'value'
     *    CURRENT_FRA    HOMW_FRA|WIZARD_FRA|CATEGORY_FRA
     */


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        // Initialize main layout UI
        initUI();
        // Create a ViewModel the first time the system calls an activity's onCreate() method.
        // Re-created activities receive the same MyViewModel instance created by the first activity.





        loadFragment(savedInstanceState);


    }

    private void initUI() {
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        Toolbar toolbar =  (Toolbar) findViewById(R.id.toolbar);
        // Use toolbar as ActionBar of the app
        setSupportActionBar(toolbar);

        // Migrate drawerLayout and toolbar, it will show up toogle button
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this,
                drawerLayout, toolbar, R.string.open, R.string.close);

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }


    private void loadFragment(Bundle savedInstanceState) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        String current_fra;
        if(savedInstanceState == null)
            current_fra = HOME_FRA;
        else
            current_fra = savedInstanceState.getString(CURRENT_FRA);

        Fragment fragment = null;

        switch (current_fra){
            case HOME_FRA:
                fragment = (HomeFragment) HomeFragment.newInstance(savedInstanceState);
                fragmentTransaction.add(R.id.drawer_layout, fragment); // drawer layout is our main layout
                break;
            case WIZARD_FRA:
                // TODO: WIZARD FRA
                break;
            case CATEGORY_FRA:
                // TODO: CATEGORY FRA
                break;
        }
        fragmentTransaction.commit();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        // TODO: restoer instance state
    }


    @Override
    protected void onDestroy() {
        FragmentManager mana = getSupportFragmentManager();
        // TODO: remove all fragment
        Fragment fra = mana.findFragmentById(R.id.home_fragment);
        FragmentTransaction fragmentTransaction = mana.beginTransaction();
        if(fra != null)
            fragmentTransaction.remove(fra);
        super.onDestroy();
    }
}
