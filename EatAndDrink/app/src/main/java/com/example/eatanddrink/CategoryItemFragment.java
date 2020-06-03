package com.example.eatanddrink;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.eatanddrink.adapter.CategoryAdapter;
import com.example.eatanddrink.dummy.DummyContent;
import com.example.eatanddrink.dummy.DummyContent.DummyItem;
import com.example.eatanddrink.model.Category;
import com.example.eatanddrink.viewmodel.restaurant.SharedViewModel;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class CategoryItemFragment extends Fragment implements
        CategoryAdapter.OnCategorySelectedListener, View.OnClickListener {


    private RecyclerView recyclerView;
    private CategoryAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private Query cate_query;
    private View rootView;
    private FirebaseFirestore mFirestore;
    private Query mQuery;
    private CategoryAdapter.OnCategorySelectedListener root;
    private SharedViewModel model;
    private Button gomenu;


    private static final String HEADLINE = "head line";
    private static final String TYPE = "type";
    private static final String CATEGORY = "category";


    private static final String TAG = "CategoryItemFragment";

    public CategoryItemFragment() {
    }

    // TODO: Customize paramekter initialization
    public static CategoryItemFragment newInstance(Bundle savedState) {
        CategoryItemFragment fragment = new CategoryItemFragment();
        if(savedState != null)
            fragment.setArguments(savedState);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FirebaseFirestore.setLoggingEnabled(true);
        root = this;
        // set up view model
        model = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        mFirestore = FirebaseFirestore.getInstance();
        mQuery = mFirestore.collection("love2eat");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category_item_list, container, false);

        // Set the adapter

        rootView = view;
        recyclerView = view.findViewById(R.id.recyclerCategories);

        Context context = view.getContext();

        if(mAdapter != null){
            mAdapter.clear();
            mAdapter.setQuery(mQuery);
        }else {
            mAdapter = new CategoryAdapter(mQuery, this) {
                @Override
                protected void onDataChanged(QuerySnapshot documentSnapshots) {
                    Log.i(TAG, "Data changed");

                    if(getItemCount() == 0){
                        rootView.findViewById(R.id.recyclerCategories).setVisibility(View.GONE);
                    }else{
                        rootView.findViewById(R.id.recyclerCategories).setVisibility(View.VISIBLE);
                    }
                }
            };
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(mAdapter);
        mAdapter.startListening();

        gomenu = view.findViewById(R.id.button_nav);
        gomenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment menu = (Menu) Menu.newInstance(null);
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container,menu)
                        .addToBackStack(null)
                        .commit();

            }
        });

        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        rootView.findViewById(R.id.recyclerCategories).setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {
        Log.d("TAG", String.format("View ID : %d", v.getId()));
    }

    @Override
    public void onCategorySelectedListener(String category) {
        Log.w(TAG, category);
        Bundle state = new Bundle();
        state.putString(HEADLINE, category);
        state.putString(TYPE, "category");
        state.putString(CATEGORY, category);
        Fragment restaurantItemFragment = (RestaurantItemFragment) RestaurantItemFragment.newInstance(state);
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, restaurantItemFragment)
                .addToBackStack(null)
                .commit();

    }
}
