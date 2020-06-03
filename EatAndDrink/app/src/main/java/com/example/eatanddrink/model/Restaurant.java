package com.example.eatanddrink.model;


import com.google.firebase.firestore.IgnoreExtraProperties;
import java.util.List;

@IgnoreExtraProperties
public class Restaurant {

    public static final String FIELD_TITLE = "name";
    public static final String FIELD_ADDR = "address";
    public static final String FIELD_ID = "id";

    private String name;
    private String address;
    private String id;
    private List<String> categories;
    private Double rating;
    private String cover_url;

    public Restaurant() {}


    public Restaurant(String id,
                      String name,
                      String address,
                      List<String> categories,
                      Double rating,
                      String cover_url){
        this.name = name;
        this.address = address;
        this.id = id;
        this.categories = categories;
        this.rating = rating;
        this.cover_url = cover_url;
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

    public void setCategories(List<String> list) {
        this.categories = list;
    }

    public List<String> getCategories() {
        return this.categories;
    }

    public void setRating(Double grade){
        this.rating = grade;
    }

    public Double getRating(){
        return this.rating;
    }

    public void setCover_url(String str){
        this.cover_url = str;
    }

    public String getCover_url() {
        return this.cover_url;
    }

}
