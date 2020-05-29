package com.example.eatanddrink;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    Button gowizard;
    Button gomenu;
    private static final String BACK_STACK_ROOT_TAG = "root_fragment";
    // TODO Specify the simple data which is needed to be stored
    /**
     * Example :
     *  If I have a user name is needed to be store, I need to specify
     *  the following 'key'.
     *  ```
     *  private static final String USER_NAME = "userName";
     *  ```
     *  Then, you can simply use this key to acccess or store user name.
     *  ```
     *  Bundle().putString(USER_NAME, 'Eric');
     *  ```
     */
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    // TODO: Specify your UI reference variable

    public HomeFragment() {
        // Required empty public constructor
        // Which is recommended to leave empty
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(Bundle state) {
        HomeFragment fragment = new HomeFragment();
        // TODO : Initialize the state
        if(state == null)
            state = new Bundle();
            fragment.initState(state);
        fragment.setArguments(state);
        return fragment;
    }


    private void initState(Bundle state) {
        // TODO : initialize the bundle
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // This is the root view which you can use in findViewById()
        final View root = inflater.inflate(R.layout.fragment_home, container, false);

        //change fragment
        gowizard = root.findViewById(R.id.button_first);
        gowizard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WizardFragment wizardfragment = new WizardFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.drawer_layout,wizardfragment);
                transaction.commit();

            }
        });

        gomenu = root.findViewById(R.id.button_nav);
        gomenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment menu = (Menu) Menu.newInstance(null);
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, menu)
                    .addToBackStack(null)
                    .commit();
//                final DrawerLayout navDrawer = getActivity().findViewById(R.id.drawer_layout);
//                if(!navDrawer.isDrawerOpen(GravityCompat.START))
//                    navDrawer.openDrawer(GravityCompat.START);
//                else
//                    navDrawer.closeDrawer(GravityCompat.END);
            }
        });
        /**
         * mListView = (ListView) root.findViewById(R.id.list_view); // example
         */
//        Button btn = root.findViewById(R.id.button_nav);
//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                DrawerLayout drawerLayout = getActivity().findViewById(R.id.drawer_layout);
//                NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
//                setSupportActionBar(toolbar);
//
////         Migrate drawerLayout and toolbar, it will show up toogle button
//                      ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this,
//                      drawerLayout, toolbar, R.string.open, R.string.close);
////
//                 drawerLayout.addDrawerListener(actionBarDrawerToggle);
//                 actionBarDrawerToggle.syncState();
//
//            }
//        });
        if(savedInstanceState != null){
            // Restore some state right after inflating our layout
            // Note: Our views haven't had their states restored yet
        }

        return root;

    }


    /**
     * This method will be callback immediately after onCreateView
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        /**
         * It is recommended to gives subclasses a chance to initialize
         * themselves once they know their view hierarchy has been completely created.
         */
        // TODO initialize the reference of the UI

        super.onViewCreated(view, savedInstanceState);
    }


    /**
     * Called when the fragment's activity has been created and this fragment's
     * view hierarchy instantiated.
     * @param savedInstanceState
     */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        /**
         * Restore some state that needs to happen after the Activity was created
         *
         * Note #1: Our views haven't had their states restored yet
         * This could be a good place to restore a ListView's contents (and it's your
         * last opportunity if you want your scroll position to be restored properly.
         *
         *
         */
        if(savedInstanceState != null ){

        }else {

        }
    }


    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null) {
            // Restore some state that needs to happen after our own views have had
            // their state restored
            // DON'T try to restore ListViews here because their scroll position will
            // not be restored properly
        }
    }

    /**
     * onSaveInstanceState will be call when the system want to
     * destroy the activity which it has been attached (add to
     * the transaction).
     */
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        // TODO : Store the simple data in here
        super.onSaveInstanceState(outState);
    }



}
