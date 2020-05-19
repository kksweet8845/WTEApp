package com.example.eatanddrink.adapter;

import android.content.res.Resources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eatanddrink.databinding.FragmentCategoryItemBinding;
import com.example.eatanddrink.databinding.FragmentRestaurantItemBinding;
import com.example.eatanddrink.model.Category;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;

public class CategoryAdapter extends FirestoreAdapter<CategoryAdapter.ViewHolder> {


    private static final String TAG = "CategoryAdapter";

    @Override
    protected void onDataChanged() {

    }

    public interface OnCategorySelectedListener {

        void onCategorySelectedListener(DocumentSnapshot category);
    }

    private OnCategorySelectedListener mListener;

    public CategoryAdapter(Query query, OnCategorySelectedListener listener){
        super(query);
        mListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

//        Log.w(TAG, "Create View Holder");

        return new ViewHolder(FragmentCategoryItemBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // TODO: bind data
//        Log.w(TAG, "Binding data");
        holder.bind(getSnapshot(position), mListener);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {


        private FragmentCategoryItemBinding binding;


        public ViewHolder(FragmentCategoryItemBinding binding){
            super(binding.getRoot());
            this.binding = binding;
        }

        public ViewHolder(View itemView){
            super(itemView);
        }


        public void bind(final DocumentSnapshot snapshot,
                         final OnCategorySelectedListener listener){

            Category category = snapshot.toObject(Category.class);
            Resources resources = itemView.getResources();


            // binding.restaurantItemName.setText(Category.getName());
            // Log.w(TAG, Category.getAddress());
            // binding.restaurantItemAddress.setText(Category.getAddress());

            // Click listener
            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view){
                    if(listener != null){
                        listener.onCategorySelectedListener(snapshot);
                    }
                }
            });
        }

    }
}
