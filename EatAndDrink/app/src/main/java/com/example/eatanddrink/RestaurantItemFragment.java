package com.example.eatanddrink;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.eatanddrink.adapter.RestaurantAdapter;
import com.example.eatanddrink.viewmodel.restaurant.RestaurantViewModel;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the
 * interface.
 */
public class RestaurantItemFragment extends Fragment implements
        RestaurantAdapter.OnRestaurantSelectedListener, View.OnClickListener{


    private RecyclerView recyclerView;
    private RestaurantAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
//    private RestaurantViewModel restaurantViewModel;
    private Query rest_query;
    private View rootView;
    private FirebaseFirestore mFirestore;
    private Query mQuery;
    private RestaurantAdapter.OnRestaurantSelectedListener root;


    private static final String TAG = "RestaurantItemFragment";

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public RestaurantItemFragment() {

    }

    // TODO: Customize parameter initialization
    public static RestaurantItemFragment newInstance() {
        RestaurantItemFragment fragment = new RestaurantItemFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FirebaseFirestore.setLoggingEnabled(true);
        root = this;
        // set up View Model
        Log.i(TAG, "Set up View Model");
//        restaurantViewModel = new ViewModelProvider(this).get(RestaurantViewModel.class);
//        restaurantViewModel.getRestaurant().observe(this, new Observer<Query>() {
//            @Override
//            public void onChanged(Query rests) {
//                // Update Adapter
//                rest_query = rests;
//
//            }
//        });
        mFirestore = FirebaseFirestore.getInstance();

        mQuery = mFirestore.collection("love2eat")
                .orderBy("name", Query.Direction.DESCENDING)
                .limit(50);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_restaurant_item_list, container, false);

        rootView = view;
        recyclerView = view.findViewById(R.id.recyclerRestaurants);

        Context context = view.getContext();

        mAdapter = new RestaurantAdapter(mQuery, this) {
            @Override
            protected void onDataChanged() {
                Log.i(TAG, "Data changed");
                if(getItemCount() == 0){
                    rootView.findViewById(R.id.recyclerRestaurants).setVisibility(View.GONE);
                    rootView.findViewById(R.id.viewEmpty).setVisibility(View.VISIBLE);
                }else{
                    rootView.findViewById(R.id.recyclerRestaurants).setVisibility(View.VISIBLE);
                    rootView.findViewById(R.id.viewEmpty).setVisibility(View.GONE);
                }

            }
        };

        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(mAdapter);
        mAdapter.startListening();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onClick(View v) {
        Log.d("TAG", String.format("View ID : %d", v.getId()));
    }



    @Override
    public void onRestaurantSelectedListener(DocumentSnapshot restaurant) {
        // TODO: Go to the details page for the selected restaurant
        // Intent
    }
}
