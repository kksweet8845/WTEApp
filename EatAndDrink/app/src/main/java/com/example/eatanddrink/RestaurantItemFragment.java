package com.example.eatanddrink;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.eatanddrink.adapter.RestaurantAdapter;
import com.example.eatanddrink.model.RestaurantDetail;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

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

    private String category_name;
    private String type;
    private String headLineText;

    private ArrayList<String> categories;

    private Button gomenu;



    private static final String HEADLINE = "head line";
    private static final String TYPE = "type";
    private static final String CATEGORY = "category";
    private static final String WIZARD = "wizard";
    private static final String RESTPARCEL = "parcel";


    private static final String TAG = "RestaurantItemFragment";

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public RestaurantItemFragment() {

    }

    // TODO: Customize parameter initialization
    public static RestaurantItemFragment newInstance(Bundle state) {
        RestaurantItemFragment fragment = new RestaurantItemFragment();
        if(state != null)
            fragment.setArguments(state);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        FirebaseFirestore.setLoggingEnabled(true);
        root = this;
        mFirestore = FirebaseFirestore.getInstance();
        try{
            if(getArguments() != null)
                type = getArguments().getString(TYPE);
            else{
                type = "category";
            }
        } catch (NullPointerException err){
            type = "category";
        }
        switch(type) {
            case CATEGORY:
                if(getArguments() != null)
                    category_name = getArguments().getString(CATEGORY);
                else
                    category_name = "咖啡";
                mQuery = mFirestore.collection("love2eat")
                        .whereArrayContains("categories", category_name)
                        .orderBy("name", Query.Direction.DESCENDING);
                headLineText = category_name;
                break;
            case WIZARD:
                if(getArguments() != null){
                    categories = getArguments().getStringArrayList(CATEGORY);
                }
                mQuery = mFirestore.collection("love2eat")
                        .whereArrayContainsAny("categories", categories)
                        .orderBy("name", Query.Direction.DESCENDING)
                        .limit(categories.size() * 5);
                headLineText = "你可能會喜歡~~";
                break;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_restaurant_item_list, container, false);

        rootView = view;
        recyclerView = view.findViewById(R.id.recyclerRestaurants);
        TextView textView = view.findViewById(R.id.headLine);
        textView.setText(headLineText);
        Context context = view.getContext();
        mAdapter = new RestaurantAdapter(mQuery, this) {
            @Override
            protected void onDataChanged(QuerySnapshot documentSnapshot) {
                Log.i(TAG, "Data changed");
                if(getItemCount() == 0){
                    rootView.findViewById(R.id.recyclerRestaurants).setVisibility(View.GONE);
                }else{
                    rootView.findViewById(R.id.recyclerRestaurants).setVisibility(View.VISIBLE);
                }

            }
        };

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
        Bundle state = new Bundle();
        RestaurantDetail rest = restaurant.toObject(RestaurantDetail.class);
        state.putParcelable(RESTPARCEL, rest);
        Fragment restaurantDetailFragment = (RestaurantDetailFragment) RestaurantDetailFragment.newInstance(state);
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, restaurantDetailFragment)
                .addToBackStack(null)
                .commit();
    }

}
