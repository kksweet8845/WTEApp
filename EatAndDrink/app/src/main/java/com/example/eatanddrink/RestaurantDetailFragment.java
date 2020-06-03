package com.example.eatanddrink;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.eatanddrink.databinding.FragmentRestaurantDetailBinding;
import com.example.eatanddrink.model.RestaurantDetail;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RestaurantDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RestaurantDetailFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    private View rootView;
    private static final String PARCEL = "parcel";
    private FragmentRestaurantDetailBinding binding;

    EditText et_review;
    Button btn_open_dialog, btn_submit, btn_cancel;

    public RestaurantDetailFragment() {
        // Required empty public constructor
    }


    public static RestaurantDetailFragment newInstance(Bundle state) {
        RestaurantDetailFragment fragment = new RestaurantDetailFragment();
        fragment.setArguments(state);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentRestaurantDetailBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        rootView = root;
        if( getArguments() != null ){
            RestaurantDetail rest = getArguments().getParcelable(PARCEL);

            Glide.with(root).load(rest.getCover_url()).into(binding.restaurantDetailImage);
            binding.restaurantDetailRating.setRating(rest.getRating().floatValue());
            binding.restaurantDetailName.setText(rest.getName());
            binding.restaurantDetailAddress.setText(rest.getAddress());
            binding.restaurantDetailOpeningHour.setText(rest.getOpening_hours_list().get(0));
            binding.restaurantDetailAvgPrice.setText(String.valueOf(rest.getAvg_price().doubleValue()));
        }

        btn_open_dialog = root.findViewById(R.id.review_btn);
        btn_open_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReviewDialog dialog = new ReviewDialog();
                dialog.show(getParentFragmentManager().beginTransaction(),"dialogreviews");
            }
        });
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
}
