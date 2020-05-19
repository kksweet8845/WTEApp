package com.example.eatanddrink.model;


import com.google.firebase.firestore.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Category {

    public static final String FIELD_TITLE = "name";
    public static final String FIELD_ADDR = "address";
    public static final String FIELD_ID = "id";

    private String name;
    private String address;
    private String id;

    public Category() {}


    public Category(String id, String name, String address){
        this.name = name;
        this.address = address;
        this.id = id;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }

    public void setAddress(String addr){
        this.address = addr;
    }

    public String getAddress() {
        return this.address;
    }

}
