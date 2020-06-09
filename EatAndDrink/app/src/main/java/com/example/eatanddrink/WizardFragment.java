package com.example.eatanddrink;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
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
import java.util.HashSet;
import java.util.List;
import java.util.Map;


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

    private HashSet<String> cur_cate = new HashSet<>();
    private HashSet<String> cur_rest = new HashSet<>();
    private HashSet<String> pot_cate = new HashSet<>();
    private HashSet<String> comm_cate = new HashSet<>();
    private HashSet<String> final_cate = new HashSet<>();

    private ArrayList<ArrayList<String>> questions = new ArrayList<>();
    private static final String TYPE = "type";
    private static final String CATEGORY = "category";
    private static final String WIZARD = "wizard";


    static int cur_iter = 0;

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
        comm_cate.add("早餐");
        comm_cate.add("午餐");
        comm_cate.add("晚餐");
        comm_cate.add("早午餐");
        comm_cate.add("宵夜");
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
        binding.wizardQuestion.setText("想吃什麼種類的食物呢？");

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
        getCategories(0);
    }

    private void transformData(QuerySnapshot documentSnapshots){
        for(DocumentChange change : documentSnapshots.getDocumentChanges()){
            DocumentSnapshot documentSnapshot = change.getDocument();
            mHashMap.put((String) documentSnapshot.get("name"), (List<String>) documentSnapshot.get("categories"));
        }

    }

    private void splitCategories() {

        int i=0;
        int ques_index = 0;
        ArrayList<String> tmp = null;
        pot_cate.removeAll(comm_cate);
        for(String s : pot_cate){

            switch(i % 3) {
                case 0:
                    if(tmp != null){
                        questions.add(ques_index++, tmp);
                    }
                    tmp = new ArrayList<>();
                    tmp.add(s);
                    i++;
                    break;
                case 1:
                case 2:
                    tmp.add(s);
                    i++;
                    break;
            }
        }

        if(i%3 >= 2){
            questions.add(ques_index, tmp);
        }
        pot_cate.clear();

    }

    private void getPotCategory(String s){
        for(Map.Entry<String, List<String>> entry : mHashMap.entrySet()) {
            String key = entry.getKey();
            List<String> value = entry.getValue();
            if(value.contains(s)){
                pot_cate.addAll(value);
            }
        }

        splitCategories();
    }


    private void printCategory(HashSet<String> cate){
        for( String s : cate){
            System.out.print(s);
            System.out.print(", ");
        }
        System.out.println("\n");
    }
    private void printList(List<String> cate){
        for( String s : cate){
            System.out.print(s);
            System.out.print(", ");
        }
        System.out.println("\n");
    }

    private void loadRestaurantFragment() {
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        Bundle bundle = new Bundle();
        bundle.putString(TYPE, WIZARD);
        bundle.putStringArrayList(CATEGORY, new ArrayList<String>(pot_cate));

        Fragment fragment = RestaurantItemFragment.newInstance(bundle);
        fragmentTransaction.replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();

    }

    private void getCategories(int iter){

        switch(iter){
            case 0:
                pot_cate.clear();
                binding.wizardQuestion1.setText("早餐");
                binding.wizardQuestion1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Button btn = (Button) v;
                        getPotCategory(btn.getText().toString());
                        getCategories(++cur_iter);
                    }
                });
                binding.wizardQuestion2.setText("午餐");
                binding.wizardQuestion2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Button btn = (Button) v;
                        getPotCategory(btn.getText().toString());
                        getCategories(++cur_iter);
                    }
                });
                binding.wizardQuestion3.setText("晚餐");
                binding.wizardQuestion3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Button btn = (Button) v;
                        getPotCategory(btn.getText().toString());
                        getCategories(++cur_iter);
                    }
                });
                break;
            default:
                System.out.println(cur_iter);
                System.out.println(cur_rest.size());
                if(questions.size() == 0 && pot_cate.size() < 12){
                    // TODO load fragment
                    System.out.println("Load fragment");
                    printCategory(pot_cate);
                    loadRestaurantFragment();
                    return;
                }else if(questions.size() == 0){
                    // Split the question again, iter reset
                    splitCategories();
                    iter = 1;
                }

                // take out question
                ArrayList<String> quest = questions.get(0);
                System.out.println("Before" + questions.size());
                questions.remove(quest);
                System.out.println("After" + questions.size());
                printCategory(pot_cate);
//                System.out.println((String)tmp_cate[0]);
                try{
                    binding.wizardQuestion1.setText(quest.get(0));
                    binding.wizardQuestion1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Button btn = (Button) v;
                            pot_cate.add(btn.getText().toString());
                            getCategories(++cur_iter);
                        }
                    });

                    binding.wizardQuestion1.setVisibility(View.VISIBLE);
                }catch (IndexOutOfBoundsException err){
                    binding.wizardQuestion1.setVisibility(View.GONE);
                }
                try{

                    binding.wizardQuestion2.setText(quest.get(1));
                    binding.wizardQuestion2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Button btn = (Button) v;
                            pot_cate.add(btn.getText().toString());
                            getCategories(++cur_iter);
                        }
                    });
                    binding.wizardQuestion2.setVisibility(View.VISIBLE);
                }catch(IndexOutOfBoundsException err) {
                    binding.wizardQuestion2.setVisibility(View.GONE);
                }

                try{
                    binding.wizardQuestion3.setText(quest.get(2));
                    binding.wizardQuestion3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Button btn = (Button) v;
                            pot_cate.add(btn.getText().toString());
                            getCategories(++cur_iter);
                        }
                    });
                    binding.wizardQuestion3.setVisibility(View.VISIBLE);
                }catch(IndexOutOfBoundsException err){
                    binding.wizardQuestion3.setVisibility(View.GONE);
                }

                break;
        }
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

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getCategories(0);
    }
}
