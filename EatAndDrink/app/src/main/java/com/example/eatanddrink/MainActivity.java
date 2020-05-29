package com.example.eatanddrink;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {




    /**
     * Fragment Name
     */
    static final String HOME_FRA            = "home fragment";
    static final String WIZARD_FRA          = "wizard fragment";
    static final String CATEGORY_FRA        = "category fragment";
    static final String RESTAURANTITEM_FRA  = "restaurant fragment";
    static final String CURRENT_FRA         = "current fragment name";
    static final String MENU_FRA         = "menu";



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

        loadFragment(savedInstanceState);    //Pei-Delete
//        HomeFragment  homefragment = new HomeFragment();
//        FragmentManager fm = getSupportFragmentManager();
//        fm.beginTransaction().add(R.id.drawer_layout,homefragment).commit();

    }

    private void initUI() {
//        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
//        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
//        Toolbar toolbar =  (Toolbar) findViewById(R.id.toolbar);
////         Use toolbar as ActionBar of the app
//      setSupportActionBar(toolbar);
////
////      toolbar.setBackgroundColor(Color.GRAY);
////         Migrate drawerLayout and toolbar, it will show up toogle button
//        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this,
//                drawerLayout, toolbar, R.string.open, R.string.close);
////        actionBarDrawerToggle.getDrawerArrowDrawable().setColor(Color.BLACK);
//        drawerLayout.addDrawerListener(actionBarDrawerToggle);
//        actionBarDrawerToggle.syncState();
    }


    private void loadFragment(Bundle savedInstanceState) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        String current_fra;
        if(savedInstanceState == null)
            current_fra = CATEGORY_FRA;   //test
        else
            current_fra = savedInstanceState.getString(CURRENT_FRA);

        Fragment fragment = null;

        switch (current_fra){
            case HOME_FRA:
                fragment = (HomeFragment) HomeFragment.newInstance(savedInstanceState);
                fragmentTransaction.add(R.id.fragment_container, fragment); // drawer layout is our main layout
                break;
            case WIZARD_FRA:
                fragment = (WizardFragment) WizardFragment.newInstance(savedInstanceState);
                fragmentTransaction.add(R.id.fragment_container, fragment);
                break;
            case CATEGORY_FRA:
                fragment = (CategoryItemFragment) CategoryItemFragment.newInstance(savedInstanceState);
                fragmentTransaction.replace(R.id.fragment_container, fragment);
                break;
            case RESTAURANTITEM_FRA:
                Log.w("MainActivity", "Load rest fragment");
                fragment = (RestaurantItemFragment) RestaurantItemFragment.newInstance(null);
                fragmentTransaction.add(R.id.drawer_layout, fragment);
                break;
            case MENU_FRA:
                fragment = (Menu) Menu.newInstance(savedInstanceState);
                fragmentTransaction.add(R.id.drawer_layout, fragment);
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
        // TODO: restore instance state
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
