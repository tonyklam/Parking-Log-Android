package com.com_4dmacau.parkinglog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

/**
 * Created by tonylam on 26/04/2016.
 */
public class Dialog_Area extends DialogFragment {

    final CharSequence[] Area_Items = {"A","B","C"};
    RadioGroup d_area_rg;
    Button btn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().setTitle(getString(R.string.dialog_area_title));
        View view = inflater.inflate(R.layout.dialog_area, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        d_area_rg = (RadioGroup)view.findViewById(R.id.dialog_area_radioGroup);

        btn = (Button)view.findViewById(R.id.dialog_area_button);

        btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {

                View parent_v = (View)v.getParent();

                String selection = "NONE";

                int rb_id = d_area_rg.getCheckedRadioButtonId();

                RadioButton rb = (RadioButton)parent_v.findViewById(rb_id);

                Toast.makeText(getActivity().getApplicationContext(), rb.getText(), Toast.LENGTH_SHORT).show();

                dismiss();
            }
        });

    }
}