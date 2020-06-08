package com.example.eatanddrink.adapter;

import android.content.res.Resources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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
    public  static ArrayList<String> mList;
    private static int index = 0;

    @Override
    protected void onDataChanged(QuerySnapshot documentSnapshot) {

    }


    public void onDocumentAdded(DocumentChange change) {
        // RecyclerViewAdapter method

        if(mList == null){
            mList = new ArrayList<String>();
        }

        DocumentSnapshot snape = change.getDocument();
        Category category = snape.toObject(Category.class);
        for(String str : category.getCategories()){

            Log.w(TAG, "have been added");
            if(!mList.contains(str)){
                mList.add(index++, str);
                Collections.sort(mList, new Comparator<String>() {
                    @Override
                    public int compare(String o1, String o2) {
                        return ( o1.length() < o2.length() ? -1 : (
                                 o1.length() == o2.length() ? 0 : 1
                                ));
                    }
                });
            }
        }

        for(int i=0;i<index;i++){
            notifyItemInserted(i);
        }
    }

    @Override
    public int getItemCount() {
        return index;
    }

    public void onDocumentModified(DocumentChange change) {

        Log.w(TAG, "document modified ");
    }

    public void onDocumentRemoved(DocumentChange change) {
        Log.w(TAG, "document removed");
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

//        Log.w(TAG, "Create View Holder");

        return new ViewHolder(FragmentCategoryItemBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if(mList != null)
            holder.bind(mList.get(position), mListener);
    }

    public void clear() {
        Log.w(TAG, "deleteing mList");
        int size = mList.size();
        mList.clear();
        notifyItemRangeRemoved(0 , size);
        index = mList.size();
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

        public void bind(final String category,
                         final OnCategorySelectedListener listener){

            Resources resources = itemView.getResources();

            binding.categoryText.setText(category);
            // Click listener
            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view){
                    if(listener != null){
                        listener.onCategorySelectedListener(category);
                    }
                }
            });
        }

    }
}
