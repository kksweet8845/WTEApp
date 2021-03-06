package com.example.eatanddrink;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class CategoryViewModel extends ViewModel {

    private MutableLiveData<List<FoodCategory>> categories;

    public LiveData<List<FoodCategory>> getCategory() {
        if(categories == null){
            categories = new MutableLiveData<List<FoodCategory>>();
            loadCategory();
        }

        return categories;
    }

    private void loadCategory() {
        // TODO Do an asynchronous operation to fetch categories
    }

}

