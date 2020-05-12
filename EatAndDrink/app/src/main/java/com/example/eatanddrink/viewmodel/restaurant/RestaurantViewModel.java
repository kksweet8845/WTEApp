package com.example.eatanddrink.viewmodel.restaurant;

import android.util.EventLog;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

// List is abstract
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class RestaurantViewModel extends ViewModel {

    private static final int LIMIT = 50;

    private FirebaseFirestore mFirestore;
    private static String TAG = "RestaurantViewModel";


    private MutableLiveData<Query> mQuery = null;


    public LiveData<Query> getRestaurant() {
        if(mQuery == null){
            mQuery = new MutableLiveData<Query>();
            loadRests();
        }

        return mQuery;
    }


    private void loadRests() {
        // Firestore
        mFirestore = FirebaseFirestore.getInstance();

        // GET rests
        Query query_result = mFirestore.collection("love2eat")
                .orderBy("name", Query.Direction.DESCENDING)
                .limit(LIMIT);

        mQuery.setValue(query_result);
        Log.i(TAG, "load rests finished");
    }

}
