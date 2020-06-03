package com.example.eatanddrink;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class ReviewDialog extends DialogFragment {
    EditText etreview;
    Button btn_submit, btn_cancel;

    public ReviewDialog()
    {
    }
    @Nullable
    @Override

    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         View view = inflater.inflate(R.layout.review_dialog, container,false);
         getDialog().setTitle("tdo");
         btn_submit = view.findViewById(R.id.submit_review);
         btn_cancel = view.findViewById(R.id.cancel_submit);

         btn_submit.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 getDialog().dismiss();  //TODO
                 OpenDialog();
             }
         });

         btn_cancel.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 getDialog().dismiss();
             }
         });
         return view;
    }

    private void OpenDialog() {
        InformDialog informDialog = new InformDialog();
        informDialog.show(getParentFragmentManager(),"inform dialog");
    }
}
