package com.example.eatanddrink;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Menu#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Menu extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    Button gowizard;
    Button gomainpage;
    Button gocategory;
    Button goaboutus;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Menu() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
     * @return A new instance of fragment Menu.
     */
    // TODO: Rename and change types and number of parameters
    public static Menu newInstance() {
        Menu fragment = new Menu();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_menu, container, false);

        gocategory = root.findViewById(R.id.button_category);
        gocategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CategoryItemFragment categoryItemFragment = new CategoryItemFragment();
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container,categoryItemFragment)
                        .addToBackStack(null)
                        .commit();

            }
        });

        gowizard = root.findViewById(R.id.button_wizard);
        gowizard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WizardFragment wizardfragment = new WizardFragment();
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container,wizardfragment)
                        .addToBackStack(null)
                        .commit();

            }
        });


        gomainpage = root.findViewById(R.id.button_home);
        gomainpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeFragment homefragment = new HomeFragment();
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container,homefragment)
                        .addToBackStack(null)
                        .commit();

            }
        });

        goaboutus = root.findViewById(R.id.button_we);
        goaboutus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                About_us about_us_fragment = new About_us();
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container,about_us_fragment)
                        .addToBackStack(null)
                        .commit();

            }
        });

        return  root;
    }

    private void initState(Bundle state) {
        // TODO : initialize the bundle
    }

    public static Menu newInstance(Bundle state) {
        Menu fragment = new Menu();
        // TODO : Initialize the state
        if(state == null)
            state = new Bundle();
        fragment.initState(state);
        fragment.setArguments(state);
        return fragment;
    }
}
