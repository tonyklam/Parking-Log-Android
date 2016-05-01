package com.a4dmacau.pakwai;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by tonylam on 15/04/2016.
 */
public class TextFragment extends Fragment {

    //Spinner Spinner_Floor;
    View view;
    NumberPicker Floor_Number_Picker;
    TextView Record_TextView;
    List<ToggleButton> t_btns;
    ImageButton button_np;
    EditText Floor_Text;
    EditText Space_Text;
    EditText Area_Text;
    EditText Direction_Text;
    ToggleButton picked_btn;
    Button Record_Btn;
    Button Clear_Btn;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.text_fragment, container, false);

        Floor_Text = (EditText)view.findViewById(R.id.tf_editText0);

        Floor_Text.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                final Dialog d_np = new Dialog(getContext());
                d_np.setTitle(R.string.dialog_np_title);
                d_np.setContentView(R.layout.dialog_np);
                Button btn = (Button)d_np.findViewById(R.id.dialog_np_button);

                final NumberPicker np = (NumberPicker)d_np.findViewById(R.id.dialog_np_np);
                np.setMinValue(0);
                np.setMaxValue(10);
                np.setDisplayedValues( new String[] { "-4", "-3", "-2", "-1", "0", "1", "2", "3","4","5","6" } );
                np.setValue(4);
                np.setWrapSelectorWheel(false);

                btn.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v) {
                        Floor_Text.setText(String.valueOf(np.getValue()-4));
                        d_np.dismiss();
                    }
                });
                d_np.show();
            }
        });

        Space_Text = (EditText)view.findViewById(R.id.tf_editText1);

        Area_Text = (EditText)view.findViewById(R.id.tf_editText2);


        Area_Text.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //new Dialog_Area().show(getFragmentManager(), "Tag");

                final Dialog d_area = new Dialog(getContext());
                d_area.setTitle(R.string.dialog_area_title);

                d_area.setContentView(R.layout.dialog_np);
                Button btn = (Button)d_area.findViewById(R.id.dialog_np_button);

                final NumberPicker d_area_np = (NumberPicker)d_area.findViewById(R.id.dialog_np_np);
                final String[] d_area_np_strings = new String[] { "A", "B", "C", "D", "E", "F", "G", "H" };
                d_area_np.setMinValue(0);
                d_area_np.setMaxValue(7);
                d_area_np.setDisplayedValues( d_area_np_strings );
                d_area_np.setValue(0);
                d_area_np.setWrapSelectorWheel(false);

                btn.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v) {
                        Area_Text.setText(d_area_np_strings[d_area_np.getValue()]);
                        d_area.dismiss();
                    }
                });

                d_area.show();
            }
        });

        Direction_Text = (EditText)view.findViewById(R.id.tf_editText3);

        Direction_Text.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                final Dialog d_direction = new Dialog(getContext());
                d_direction.setTitle(R.string.dialog_direction_title);
                d_direction.setContentView(R.layout.dialog_3x3);

                t_btns = new ArrayList<ToggleButton>();

                ToggleButton t_btn = (ToggleButton)d_direction.findViewById(R.id.dialog_3x3_toggleButton_1);
                t_btns.add(t_btn);
                t_btn = (ToggleButton)d_direction.findViewById(R.id.dialog_3x3_toggleButton_2);
                t_btns.add(t_btn);
                t_btn = (ToggleButton)d_direction.findViewById(R.id.dialog_3x3_toggleButton_3);
                t_btns.add(t_btn);
                t_btn = (ToggleButton)d_direction.findViewById(R.id.dialog_3x3_toggleButton_4);
                t_btns.add(t_btn);
                t_btn = (ToggleButton)d_direction.findViewById(R.id.dialog_3x3_toggleButton_6);
                t_btns.add(t_btn);
                t_btn = (ToggleButton)d_direction.findViewById(R.id.dialog_3x3_toggleButton_7);
                t_btns.add(t_btn);
                t_btn = (ToggleButton)d_direction.findViewById(R.id.dialog_3x3_toggleButton_8);
                t_btns.add(t_btn);
                t_btn = (ToggleButton)d_direction.findViewById(R.id.dialog_3x3_toggleButton_9);
                t_btns.add(t_btn);

                CompoundButton.OnCheckedChangeListener on_check_changed = new CompoundButton.OnCheckedChangeListener() {
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            for ( int i = 0; i < t_btns.size(); i++){
                                ((ToggleButton)t_btns.get(i)).setChecked(false);
                            }
                            buttonView.setChecked(true);
                            picked_btn = (ToggleButton)buttonView;
                        } else {
                            // The toggle is disabled
                        }
                    }
                };

                for ( int i = 0; i < t_btns.size(); i++){
                    ((ToggleButton)t_btns.get(i)).setOnCheckedChangeListener(on_check_changed);
                }

                Button btn = (Button)d_direction.findViewById(R.id.dialog_3x3_button);

                btn.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v) {
                        Direction_Text.setText(picked_btn.getText());
                        d_direction.dismiss();
                    }
                });


                d_direction.show();
            }
        });

        Record_TextView = (TextView)view.findViewById(R.id.tf_Record_Text);

        Record_Btn = (Button)view.findViewById(R.id.tf_record_btn);

        Record_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Record_Text record_txt = new Record_Text();
                String output_string;

                if ( Floor_Text.getText().toString().matches("") && Space_Text.getText().toString().matches("") &&
                        Area_Text.getText().toString().matches("") && Direction_Text.getText().toString().matches(""))
                {
                    output_string = getResources().getString(R.string.tf_record_null_hint);
                    Toast.makeText(getActivity().getApplicationContext(), output_string, Toast.LENGTH_LONG).show();
                    return;
                }
                else
                {
                    record_txt.setTime(new Date());
                    record_txt.setFloor(Floor_Text.getText().toString());
                    record_txt.setSpace_num(Space_Text.getText().toString());
                    record_txt.setArea(Area_Text.getText().toString());
                    record_txt.setDirection_from_lift(Direction_Text.getText().toString());

                    /*
                    String temp_string = "They're not all empty";
                    Toast.makeText(getActivity().getApplicationContext(), temp_string, Toast.LENGTH_LONG).show();
                    */

                    ((MainActivity)getActivity()).db.add_Text_Record(record_txt);
                }

                showAllRecords();
            }
        });

        Clear_Btn = (Button)view.findViewById(R.id.tf_clear_btn);

        Clear_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).db.clear_All_Text_Record();
                Floor_Text.setText("");
                Floor_Text.setHint(getResources().getString(R.string.tf_editText0_hint));
                Space_Text.setText("");
                Space_Text.setHint(getResources().getString(R.string.tf_editText1_hint));
                Area_Text.setText("");
                Area_Text.setHint(getResources().getString(R.string.tf_editText2_hint));
                Direction_Text.setText("");
                Direction_Text.setHint(getResources().getString(R.string.tf_editText3_hint));
                Record_TextView.setText("");
                Record_TextView.setHint(getResources().getString(R.string.tf_record_text_hint));
            }
        });

        showAllRecords();

        return view;
    }

    public void showAllRecords()
    {
        Record_TextView = (TextView)view.findViewById(R.id.tf_Record_Text);

        List<Record_Text> records = ((MainActivity)getActivity()).db.get_All_Text_Records();

        String temp_string = "";
        for ( int i = 0; i < records.size(); i++ )
        {
            temp_string += records.get(i).toString();
        }

        Record_TextView.setText(temp_string);
    }
}
