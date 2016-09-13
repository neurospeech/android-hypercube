package com.neurospeech.hypercubesample.fragments.form;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.neurospeech.hypercube.ui.HyperEditText;
import com.neurospeech.hypercube.validations.TextValidator;
import com.neurospeech.hypercubesample.R;

/**
 * Created by ackav on 13-08-2016.
 */
public class EditTextFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.form_edit_text_fragment,container,false);

        TextValidator tv = new TextValidator() {
            @Override
            public Object invalidError(String value) {
                if(value == null || value.isEmpty())
                    return "Field cannot be empty";
                return null;
            }
        };

        HyperEditText editText;
        editText = (HyperEditText)view.findViewById(R.id.edit_validate_on_lost_focus);
        editText.setValidator(true,false,tv);

        editText.setText("1000");



        editText = (HyperEditText)view.findViewById(R.id.edit_validate_on_typing);
        editText.setValidator(true,true,tv);


        editText = (HyperEditText)view.findViewById(R.id.edit_validate_no_auto);
        editText.setValidator(false,false,tv);

        view.findViewById(R.id.validate_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return view;
    }
}
