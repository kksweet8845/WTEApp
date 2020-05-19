package com.example.eatanddrink.adapter;

import android.content.res.Resources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eatanddrink.databinding.FragmentRestaurantCateItemBinding;
import com.example.eatanddrink.databinding.FragmentRestaurantItemBinding;
import com.example.eatanddrink.model.Restaurant;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;

public class RestaurantCateAdapter extends FirestoreAdapter<RestaurantCateAdapter.ViewHolder> {


    private static final String TAG = "RestaurantAdapter";

    @Override
    protected void onDataChanged() {

    }

    public interface OnRestaurantCateSelectedListener {

        void onRestaurantCateSelectedListener(DocumentSnapshot restaurant);
    }

    private OnRestaurantCateSelectedListener mListener;

    public RestaurantCateAdapter(Query query, OnRestaurantCateSelectedListener listener){
        super(query);
        mListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

//        Log.w(TAG, "Create View Holder");

        return new ViewHolder(FragmentRestaurantCateItemBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // TODO: bind data
//        Log.w(TAG, "Binding data");
        holder.bind(getSnapshot(position), mListener);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {


        private FragmentRestaurantCateItemBinding binding;


        public ViewHolder(FragmentRestaurantCateItemBinding binding){
            super(binding.getRoot());
            this.binding = binding;
        }

        public ViewHolder(View itemView){
            super(itemView);
        }


        public void bind(final DocumentSnapshot snapshot,
                         final OnRestaurantCateSelectedListener listener){

            Restaurant restaurant = snapshot.toObject(Restaurant.class);
            Resources resources = itemView.getResources();


//            binding.restaurantItemName.setText(restaurant.getName());
//            Log.w(TAG, restaurant.getAddress());
//            binding.restaurantItemAddress.setText(restaurant.getAddress());

            // Click listener
            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view){
                    if(listener != null){
                        listener.onRestaurantCateSelectedListener(snapshot);
                    }
                }
            });
        }

    }
}
