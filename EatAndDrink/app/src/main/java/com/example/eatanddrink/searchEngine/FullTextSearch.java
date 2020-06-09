package com.example.eatanddrink.searchEngine;

import android.util.Log;

import androidx.annotation.Nullable;

import com.example.eatanddrink.model.RestaurantDetail;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class FullTextSearch implements EventListener<QuerySnapshot> {


    private String target_text;

    private static final String TAG = "FirestoreAdapter";

    private Query mQuery;

    private ListenerRegistration mRegistration;

    private ArrayList<DocumentSnapshot> mSnapshots = new ArrayList<>();
    private FirebaseFirestore mFirestore;

//    private ArrayList<String> regex_text;

    private String regex_text;
    private ArrayList<String> rest_name;
    private ArrayList<String> result;

    public FullTextSearch() {
    }

    public void genRegex() {
//        char[] target_arr = target_text.toCharArray();
        this.regex_text = String.format(".*(%s)+.*", target_text);
    }

    public void setTarget_text(String s) {


        s = s.trim();
        this.target_text = s;
    }

    public void prepareData() {
        mFirestore = FirebaseFirestore.getInstance();
        mQuery = mFirestore.collection("love2eat");
        startListening();
    }


    public ArrayList<String> search(String target_str) {
        // Perform single or double word sesarch

        this.target_text = target_str;
        genRegex();

        if(result == null){
            result = new ArrayList<>();
        }else{
            result.clear();
        }

        for(DocumentSnapshot snapshot : mSnapshots){
            RestaurantDetail rest = snapshot.toObject(RestaurantDetail.class);
            boolean matches = Pattern.matches(regex_text, rest.getName());
            if(matches){
                result.add(rest.getName());
            }
        }

        return result;
    }

    @Override
    public void onEvent(@Nullable QuerySnapshot documentSnapshots, @Nullable FirebaseFirestoreException err) {
        if(err != null){
            Log.w(TAG, "onEvent:error", err);
            onError(err);
            return;
        }

        // Dispatch the event
        Log.d(TAG, "onEvent:numchanges:" + documentSnapshots.getDocumentChanges().size());
        for(DocumentChange change : documentSnapshots.getDocumentChanges()){
            switch(change.getType()){
                case ADDED:
                    onDocumentAdded(change);
                    break;
                case MODIFIED:
                    onDocumentModified(change);
                    break;
                case REMOVED:
                    onDocumentRemoved(change);
                    break;
            }
        }

        onDataChanged(documentSnapshots);
    }

    public void startListening() {
        if (mQuery != null && mRegistration == null) {
            mRegistration = mQuery.addSnapshotListener(this);
        }
    }

    public void stopListening() {
        if (mRegistration != null) {
            mRegistration.remove();
            mRegistration = null;
        }

        mSnapshots.clear();
    }

    public void setQuery(Query query) {
        // Stop listening
        stopListening();

        // Clear existing data
        mSnapshots.clear();

        // Listen to new query
        mQuery = query;
        startListening();
    }


    public void onDocumentAdded(DocumentChange change) {
        mSnapshots.add(change.getNewIndex(), change.getDocument());
        // RecyclerViewAdapter method

    }

    public int getItemCount() {
        return mSnapshots.size();
    }

    protected DocumentSnapshot getSnapshot(int index) {
        return mSnapshots.get(index);
    }

    public void onDocumentModified(DocumentChange change) {
        if(change.getOldIndex() == change.getNewIndex()){
            // Item changed but remained in same position
            mSnapshots.set(change.getOldIndex(), change.getDocument());
        }else {
            // Item changed and changed position
            mSnapshots.remove(change.getOldIndex());
            mSnapshots.add(change.getNewIndex(), change.getDocument());
        }
    }

    public void onDocumentRemoved(DocumentChange change) {
        mSnapshots.remove(change.getOldIndex());
    }

    protected void onError(FirebaseFirestoreException err){
        Log.w(TAG, "onError", err);
    }

    protected void onDataChanged(QuerySnapshot documentSnapshot){

    }

}
