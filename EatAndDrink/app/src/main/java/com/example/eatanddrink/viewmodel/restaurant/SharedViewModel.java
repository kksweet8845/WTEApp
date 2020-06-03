package com.example.eatanddrink.viewmodel.restaurant;

import android.support.v4.os.IResultReceiver;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;

public class SharedViewModel extends ViewModel {


    private final MutableLiveData<ArrayList<DocumentSnapshot>> restaurantData = new MutableLiveData<ArrayList<DocumentSnapshot>>();

    public void setRestaurantData(ArrayList<DocumentSnapshot> list){
        restaurantData.setValue(list);
    }

    public LiveData<ArrayList<DocumentSnapshot>> getRestaurantData() {
        return restaurantData;
    }


}
