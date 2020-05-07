package com.example.eatanddrink.dummy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class RestaurantContent {
    /**
     * An array of sample restaurant items
     */
    public static final List<RestaurantItem> ITEMS = new ArrayList<RestaurantItem>();
    /**
     * A map of restaurant items, by ID
     */
    public static final Map<String, RestaurantItem> ITEM_MAP = new HashMap<String, RestaurantItem>();


    /**
     * A restaurant item
     */
    public static class RestaurantItem {
        public final String id;
        public final String title;
        public final String address;

        public RestaurantItem(String id, String title, String address) {
            this.id = id;
            this.title = title;
            this.address = address;
        }

        @Override
        public String toString() {
            return String.format("id: %s\ntitle: %s\naddress: %s", id, title, address);
        }
    }


}
