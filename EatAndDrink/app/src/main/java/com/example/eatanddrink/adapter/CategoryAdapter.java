package com.example.eatanddrink.adapter;

import android.content.res.Resources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.eatanddrink.R;
import com.example.eatanddrink.databinding.FragmentCategoryItemBinding;
import com.example.eatanddrink.databinding.FragmentRestaurantItemBinding;
import com.example.eatanddrink.model.Category;
import com.example.eatanddrink.utils.MyString;
import com.example.eatanddrink.utils.MyStringSorter;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

public class CategoryAdapter extends FirestoreAdapter<CategoryAdapter.ViewHolder> {


    private static final String TAG = "CategoryAdapter";

    @Override
    protected void onDataChanged(QuerySnapshot documentSnapshot) {

    }

    public interface OnCategorySelectedListener {

        void onCategorySelectedListener(String category);
    }

    private OnCategorySelectedListener mListener;

    public CategoryAdapter(Query query, OnCategorySelectedListener listener){
        super(query);
        mListener = listener;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(FragmentCategoryItemBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(getSnapshot(position), mListener);
    }



    static class ViewHolder extends RecyclerView.ViewHolder {

        private FragmentCategoryItemBinding binding;

        public ViewHolder(FragmentCategoryItemBinding binding){
            super(binding.getRoot());
            this.binding = binding;
        }

        public ViewHolder(View itemView) {
            super(itemView);
        }

        public void bind(final DocumentSnapshot category,
                         final OnCategorySelectedListener listener){

            Resources resources = itemView.getResources();
            final String image_url,cate_name;
            image_url = (String) category.get("url");
            cate_name = (String) category.get("name");
            binding.categoryText.setText(cate_name);
            // Click listener
            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view){
                    if(listener != null){
                        listener.onCategorySelectedListener(cate_name);
                    }
                }
            });
            Glide.with(binding.categoryImage.getContext())
                    .load(image_url)
                    .into(binding.categoryImage);
        }

    }
}
