package com.example.eatanddrink;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.eatanddrink.searchEngine.FullTextSearch;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    Button gowizard;
    Button gomenu;
    Button submitSearch;
    private View rootView;

    private static final String HEADLINE = "head line";
    private static final String TYPE = "type";
    private static final String CATEGORY = "category";
    private static final String WIZARD = "wizard";
    private static final String RESTPARCEL = "parcel";
    private static final String SEARCH = "search";

    private static final String BACK_STACK_ROOT_TAG = "root_fragment";

    FullTextSearch fullTextSearch;

    public HomeFragment() {
        // Required empty public constructor
        // Which is recommended to leave empty
        fullTextSearch = new FullTextSearch();
        fullTextSearch.prepareData();
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
        rootView = root;
        //change fragment
        gowizard = root.findViewById(R.id.button_first);
        gowizard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment wizard = (WizardFragment) WizardFragment.newInstance(null);
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, wizard)
                        .addToBackStack(null)
                        .commit();
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
//                else
            }
        });
        submitSearch = root.findViewById(R.id.search_btn);
        if(submitSearch == null){
            Log.e("ff", "search is null");
        }
        submitSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView searchBar = rootView.findViewById(R.id.searchBar);
                ArrayList<String> rst = fullTextSearch.search(searchBar.getText().toString());
                hideSoftKeyboard();
                Bundle state = new Bundle();
                state.putString(TYPE, SEARCH);
                state.putStringArrayList(SEARCH, rst);
                Fragment restaurantItemFragment = (RestaurantItemFragment) RestaurantItemFragment.newInstance(state);
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, restaurantItemFragment)
                        .addToBackStack(null)
                        .commit();

            }
        });

        /**
         * mListView = (ListView) root.findViewById(R.id.list_view); // example
         */
        if(savedInstanceState != null){
            // Restore some state right after inflating our layout
            // Note: Our views haven't had their states restored yet
        }

        return root;

    }
    public void hideSoftKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
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
