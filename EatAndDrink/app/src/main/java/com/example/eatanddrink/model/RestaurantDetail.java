package com.example.eatanddrink.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class RestaurantDetail implements Parcelable {


    private String address;
    private Double avg_price;
    private List<String> categories;
    private String city;
    private String cover_url;
    private Double lat;
    private Double lng;
    private String name;
    private List<String> opening_hours_list;
    private String phone;
    private Double rating;

    // Default cons
    public RestaurantDetail() {

    }


    protected RestaurantDetail(Parcel in) {
        this.address = in.readString();
        this.avg_price = in.readDouble();
        in.readStringList(this.categories);
        this.city = in.readString();
        this.cover_url = in.readString();
        this.lat = in.readDouble();
        this.lng = in.readDouble();
        this.name = in.readString();
        in.readStringList(this.opening_hours_list);
        this.phone = in.readString();
        this.rating = in.readDouble();

    }

    public static final Creator<RestaurantDetail> CREATOR = new Creator<RestaurantDetail>() {
        @Override
        public RestaurantDetail createFromParcel(Parcel in) {
            return new RestaurantDetail(in);
        }

        @Override
        public RestaurantDetail[] newArray(int size) {
            return new RestaurantDetail[size];
        }
    };

    public void setAddress(String address) {
        this.address = address;
    }

    public void setAvg_price(Double avg_price) {
        this.avg_price = avg_price;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setCover_url(String cover_url) {
        this.cover_url = cover_url;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOpening_hours_list(List<String> opening_hours_list) {
        this.opening_hours_list = opening_hours_list;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public String getAddress() {
        return address;
    }

    public Double getAvg_price() {
        return avg_price;
    }

    public List<String> getCategories() {
        return categories;
    }

    public String getCity() {
        return city;
    }

    public String getCover_url() {
        return cover_url;
    }

    public Double getLat() {
        return lat;
    }

    public Double getLng() {
        return lng;
    }

    public String getName() {
        return name;
    }

    public List<String> getOpening_hours_list() {
        return opening_hours_list;
    }

    public String getPhone() {
        return phone;
    }

    public Double getRating() {
        return rating;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.address);
        dest.writeDouble(this.avg_price);
        dest.writeStringList(this.categories);
        dest.writeString(this.city);
        dest.writeString(this.cover_url);
        dest.writeDouble(this.lat);
        dest.writeDouble(this.lng);
        dest.writeString(this.name);
        dest.writeStringList(this.opening_hours_list);
        dest.writeString(this.phone);
        dest.writeDouble(this.rating);
    }





}
