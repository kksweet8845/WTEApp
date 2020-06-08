package com.example.eatanddrink.adapter;

import android.content.res.Resources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.eatanddrink.databinding.FragmentCategoryItemBinding;
import com.example.eatanddrink.databinding.ReviewItemBinding;
import com.example.eatanddrink.model.Category;
import com.example.eatanddrink.model.Review;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;
import java.util.stream.IntStream;

public class ReviewAdapter extends FirestoreAdapter<ReviewAdapter.ViewHolder> {


    private static final String TAG = "ReviewAdapter";

    public ReviewAdapter(Query query){
        super(query);
    }

    static private String[] avatar = new String[] {
            "https://i.imgur.com/4T1GwKh.png",
            "https://i.imgur.com/mMljqZz.png",
            "https://i.imgur.com/ajwyKzd.png",
            "https://i.imgur.com/nAcLQEn.png",
            "https://i.imgur.com/Solx8oe.png"
    };


    @Override
    protected void onDataChanged(QuerySnapshot documentSnapshot) {

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

//        Log.w(TAG, "Create View Holder");

        return new ViewHolder(ReviewItemBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(getSnapshot(position));
    }
    static class ViewHolder extends RecyclerView.ViewHolder {

        private ReviewItemBinding binding;

        public ViewHolder(ReviewItemBinding binding){
            super(binding.getRoot());
            this.binding = binding;
        }

        public ViewHolder(View itemView) {
            super(itemView);
        }


        public void bind(final DocumentSnapshot snapshot){
            Review rev = snapshot.toObject(Review.class);
//            binding.poster.setText(snapshot.getId());
            Random random = new Random();

            Glide.with(binding.poster.getContext())
                    .load(avatar[random.nextInt(5)])
                    .into(binding.poster);
            binding.postedReview.setText(rev.getText());

        }

    }
}
