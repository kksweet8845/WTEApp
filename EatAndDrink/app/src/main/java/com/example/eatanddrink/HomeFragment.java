package com.example.eatanddrink;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {


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
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
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
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        /**
         * mListView = (ListView) root.findViewById(R.id.list_view); // example
         */
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
     * destory the activity which it has been attached (add to
     * the transaction).
     */
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        // TODO : Store the simple data in here
        super.onSaveInstanceState(outState);
    }



}
