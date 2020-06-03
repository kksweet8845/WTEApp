package com.example.eatanddrink;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.eatanddrink.databinding.FragmentWizardBinding;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import com.google.firebase.firestore.EventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WizardFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WizardFragment
        extends Fragment
        implements EventListener<QuerySnapshot> {

    Button gomenu;

    private ListenerRegistration mRegistration;
    private Query mQuery;
    private FirebaseFirestore mFirestore;

    private FragmentWizardBinding binding;
    private ArrayList<DocumentSnapshot> mSnapshots = new ArrayList<>();
    private HashMap<String, List<String>> mHashMap = new HashMap<>(50);
    private volatile Boolean isTransformed = false;

    private String TAG = "WizardFragment";
    public WizardFragment() {
        // Required empty public constructor
        mFirestore = FirebaseFirestore.getInstance();
        mQuery = mFirestore.collection("love2eat");
        this.startListening();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentWizardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        gomenu = binding.buttonNav;
        gomenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Menu menu = new Menu();
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container,menu)
                        .addToBackStack(null)
                        .commit();
            }
        });

        while(isTransformed){



        }

        return root;
    }

    private void initState(Bundle state) {
        // TODO : initialize the bundle
    }

    public static WizardFragment newInstance(Bundle state) {
        WizardFragment fragment = new WizardFragment();
        // TODO : Initialize the state
        if(state == null)
            state = new Bundle();
        fragment.initState(state);
        fragment.setArguments(state);
        return fragment;
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

        transformData(documentSnapshots);
        isTransformed = true;
    }

    private void transformData(QuerySnapshot documentSnapshots){
        for(DocumentChange change : documentSnapshots.getDocumentChanges()){
            DocumentSnapshot documentSnapshot = change.getDocument();
            mHashMap.put((String) documentSnapshot.get("name"), (List<String>) documentSnapshot.get("categories"));
        }
    }


    private ArrayList<String> getCategories(int iter){

        switch(iter){
            case 0:

                break;
            default:

                break;
        }
        return null;
    }

    private void startListening() {
        if (mQuery != null && mRegistration == null) {
            mRegistration = mQuery.addSnapshotListener(this);
        }
    }

    private void stopListening() {
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

    private void onDocumentAdded(DocumentChange change){
        mSnapshots.add(change.getNewIndex(), change.getDocument());
    }

    private void onDocumentModified(DocumentChange change) {
        if(change.getOldIndex() == change.getNewIndex()){
            // Item changed but remained in same position
            mSnapshots.set(change.getOldIndex(), change.getDocument());
        }else {
            // Item changed and changed position
            mSnapshots.remove(change.getOldIndex());
            mSnapshots.add(change.getNewIndex(), change.getDocument());
        }
    }

    private void onDocumentRemoved(DocumentChange change) {
        mSnapshots.remove(change.getOldIndex());
    }

    private void onError(FirebaseFirestoreException err){
        Log.w(TAG, "onError", err);
    }
}
