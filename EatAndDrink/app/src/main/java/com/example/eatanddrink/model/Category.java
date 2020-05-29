package com.example.eatanddrink.model;


import com.google.firebase.firestore.IgnoreExtraProperties;

import java.util.List;

@IgnoreExtraProperties
public class Category {

    public static final String FIELD_TITLE = "name";
    public static final String FIELD_ADDR = "address";
    public static final String FIELD_ID = "id";

    private List<String> categories;

    public Category() {}


    public Category(List<String> categories){
        this.categories = categories;
    }


    public List<String> getCategories(){
        return this.categories;
    }

    public void setCategories() {

    }

}
