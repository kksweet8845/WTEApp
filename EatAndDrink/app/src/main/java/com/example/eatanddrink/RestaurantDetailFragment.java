package com.example.eatanddrink;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.eatanddrink.adapter.ReviewAdapter;
import com.example.eatanddrink.databinding.FragmentRestaurantDetailBinding;
import com.example.eatanddrink.model.RestaurantDetail;
import com.example.eatanddrink.model.Review;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.api.Distribution;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RestaurantDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RestaurantDetailFragment
        extends Fragment
        implements OnMapReadyCallback, ReviewDialog.ReviewNoticeListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    private View rootView;
    private static final String PARCEL = "parcel";
    private FragmentRestaurantDetailBinding binding;

    EditText et_review;
    Button btn_open_dialog, btn_submit, btn_cancel;

    private HashMap<String, Integer> weekMap;

    private RecyclerView recyclerView;
    private ReviewAdapter reviewAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private Query mQuery;
    private FirebaseFirestore mFirestore;


    private String TAG = "Restaurant detail fragment";

    private MapView mv;
    private RestaurantDetail rest;

    public RestaurantDetailFragment() {
        // Required empty public constructor
        weekMap = new HashMap<>();
        weekMap.put("Mon", Integer.valueOf(0));
        weekMap.put("Tue", Integer.valueOf(1));
        weekMap.put("Wed", Integer.valueOf(2));
        weekMap.put("Thu", Integer.valueOf(3));
        weekMap.put("Fri", Integer.valueOf(4));
        weekMap.put("Sat", Integer.valueOf(5));
        weekMap.put("Sun", Integer.valueOf(6));

    }


    public static RestaurantDetailFragment newInstance(Bundle state) {
        RestaurantDetailFragment fragment = new RestaurantDetailFragment();
        fragment.setArguments(state);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FirebaseFirestore.setLoggingEnabled(true);
        mFirestore = FirebaseFirestore.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentRestaurantDetailBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        rootView = root;


        if( getArguments() != null ){
            rest = getArguments().getParcelable(PARCEL);

            mQuery = mFirestore.collection("love2eat").document(rest.getName()).collection("user_review");

            Glide.with(root).load(rest.getCover_url()).into(binding.restaurantDetailImage);
            binding.restaurantDetailRating.setRating(rest.getRating().floatValue());
            binding.restaurantDetailName.setText(rest.getName());
            binding.restaurantDetailAddress.setText(rest.getAddress());
            try {
                Date date = new Date();
                SimpleDateFormat ft = new SimpleDateFormat("E");

                Log.w("dfdf", "" + ft.format(date));
                int index = weekMap.get(ft.format(date)).intValue();
                binding.restaurantDetailOpeningHour.setText(rest.getOpening_hours_list().get(index));
            }catch(java.lang.IndexOutOfBoundsException err){
                binding.restaurantDetailOpeningHour.setText("No Opening hour list");
            }
            binding.restaurantDetailAvgPrice.setText(String.valueOf(rest.getAvg_price().doubleValue()));

            mv = binding.restaurantDetailMapView;
            binding.restaurantDetailMapView.onCreate(savedInstanceState);
            binding.restaurantDetailMapView.getMapAsync(this);

        }

        btn_open_dialog = root.findViewById(R.id.review_btn);
        btn_open_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReviewDialog dialog = new ReviewDialog((ReviewDialog.ReviewNoticeListener)RestaurantDetailFragment.this);
                dialog.show(getParentFragmentManager().beginTransaction(),"dialogreviews");
            }
        });

        reviewAdapter = new ReviewAdapter(mQuery) {
            @Override
            protected void onDataChanged(QuerySnapshot documentSnapshot) {
                Log.i(TAG, "Data changed");
                if(getItemCount() == 0){
                    binding.recyclerReview.setVisibility(View.GONE);
                }else{
                    binding.recyclerReview.setVisibility(View.VISIBLE);
                }
            }
        };
        recyclerView = binding.recyclerReview;
        recyclerView.setLayoutManager(new LinearLayoutManager(rootView.getContext()));
        recyclerView.setAdapter(reviewAdapter);
        reviewAdapter.startListening();
        return root;
    }


    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null) {
            // Restore some state that needs to happen after our own views have had
            // their state restored
            // DON'T try to restore ListViews here because their scroll position will
            // not be restored properly
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng restPosition = new LatLng(rest.getLat() , rest.getLng());
        googleMap.addMarker(new MarkerOptions().position(restPosition));
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(restPosition, 18));
//        googleMap.animateCamera(CameraUpdateFactory.zoomTo(18));
    }

    /**
     * onSaveInstanceState will be call when the system want to
     * destory the activity which it has been attached (add to
     * the transaction).
     */
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        // TODO : Store the simple data in here
        super.onSaveInstanceState(outState);
    }

    public void onResume(){
        super.onResume();
        mv.onResume();
    }

    public void onPause(){
        super.onPause();
        mv.onPause();
    }

    public void onDestroy(){
        super.onDestroy();
        mv.onDestroy();
    }

    private void OpenInformDialog() {
        InformDialog informDialog = new InformDialog();
        informDialog.show(getParentFragmentManager(), "Comfirm dialog");
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        ReviewDialog r = (ReviewDialog) dialog;
        if( r.getBinding().reviewer.getText().toString().equals("匿名") ) {
            mFirestore
                    .collection("love2eat")
                    .document(rest.getName())
                    .collection("user_review")
                    .add(r.getmMap())
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            OpenInformDialog();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w(TAG, "Error writing document");
                        }
                    });
        }else {
            mFirestore
                    .collection("love2eat").document(rest.getName()).collection("user_review")
                    .document(r.getBinding().reviewer.getText().toString())
                    .set(r.getmMap())
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            OpenInformDialog();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w(TAG, "Error writing document", e);
                        }
                    });
        }
        // Open another dialog
        dialog.dismiss();
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        Log.w(TAG, "Canceled");
        dialog.dismiss();
    }
}
