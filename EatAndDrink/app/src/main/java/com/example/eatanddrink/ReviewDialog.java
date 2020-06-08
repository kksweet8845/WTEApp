package com.example.eatanddrink;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.eatanddrink.databinding.ReviewDialogBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.HashMap;
import java.util.Map;


public class ReviewDialog extends DialogFragment {
    private EditText etreview;
    private ReviewDialogBinding binding;

    private ReviewNoticeListener mListener;
    private Map<String, Object> mMap;

    static final private String TAG = "ReviewDialog";


    public ReviewDialog(ReviewNoticeListener listener)
    {
        this.mListener = listener;
    }

    public ReviewDialogBinding getBinding() {
        return binding;
    }

    public void setBinding(ReviewDialogBinding binding) {
        this.binding = binding;
    }

    public interface ReviewNoticeListener {
        public void onDialogPositiveClick(DialogFragment dialog);
        public void onDialogNegativeClick(DialogFragment dialog);
    }



    public Map<String, Object> getmMap() {
        return mMap;
    }

    public void setmMap(Map<String, Object> mMap) {
        this.mMap = mMap;
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = ReviewDialogBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

         getDialog().setTitle("tdo");

         binding.submitReview.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 mMap = new HashMap<>();
                 mMap.put("text", binding.reviewContent.getText().toString());
                 mListener.onDialogPositiveClick(ReviewDialog.this);
//                getDialog().dismiss();
             }
         });

         binding.cancelSubmit.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 mListener.onDialogNegativeClick(ReviewDialog.this);
//                 getDialog().dismiss();
             }
         });
         return view;
    }

//    private void OpenDialog() {
//        InformDialog informDialog = new InformDialog();
//        informDialog.show(mFragmentManager,"inform dialog");
//    }
//
//    private void updateDB(String id, String user_id, String text){
//
//        Map<String, Object>  review_i = new HashMap<>();
//        review_i.put("text", text);
//
//        mFirestore = FirebaseFirestore.getInstance();
//
//        mFirestore.collection("love2eat").document(id).collection("user_review")
//                .document(user_id)
//                .set(review_i)
//                .addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void aVoid) {
//                        OpenDialog();
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Log.w(TAG, "Error writing document", e);
//                    }
//                });
//
//    }

}
