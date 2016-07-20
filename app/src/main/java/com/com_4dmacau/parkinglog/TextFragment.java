package com.com_4dmacau.parkinglog;

import android.app.Dialog;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
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
    ImageButton Record_Btn;
    ImageButton Clear_Btn;
    ImageButton Floor_Text_Clear_Btn;
    ImageButton Space_Text_Clear_Btn;
    ImageButton Area_Text_Clear_Btn;
    ImageButton Direction_Text_Clear_Btn;
    List<Record_Text> record_text_list;
    ListView List_View;
    ArrayAdapter<Record_Text> list_view_adapter;

    String current_floor = "";
    String current_space = "";
    String current_area = "";
    String current_direction = "";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.text_fragment, container, false);

        Floor_Text = (EditText)view.findViewById(R.id.tf_floor_text);

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
                np.setMaxValue(15);
                np.setDisplayedValues( new String[] { "-6","-5","-4","-3","-2","-1","0","1","2","3","4","5","6","7","8","9" } );
                np.setValue(6);
                np.setWrapSelectorWheel(false);

                btn.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v) {
                        Floor_Text.setText(String.valueOf(np.getValue()-6));
                        d_np.dismiss();
                    }
                });
                d_np.show();
            }
        });

        Space_Text = (EditText)view.findViewById(R.id.tf_space_text);

        Area_Text = (EditText)view.findViewById(R.id.tf_area_text);


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
                final String[] d_area_np_strings = new String[] { "A", "B", "C", "D", "E", "F", "G", "H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z" };
                d_area_np.setMinValue(0);
                d_area_np.setMaxValue(25);
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

        Direction_Text = (EditText)view.findViewById(R.id.tf_direction_text);

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
                        if ( picked_btn != null )
                        {
                            String[] text_array = getResources().getStringArray(R.array.record_text_direction_from_lift_string_array);
                            Direction_Text.setText(text_array[Integer.parseInt(picked_btn.getTag().toString())]);
                            current_direction = picked_btn.getTag().toString();
                        }
                        d_direction.dismiss();
                    }
                });


                d_direction.show();
            }
        });

        Record_Btn = (ImageButton)view.findViewById(R.id.tf_record_btn);

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
                    record_txt.setDirection_from_lift(current_direction);

                    /*
                    String temp_string = "They're not all empty";
                    Toast.makeText(getActivity().getApplicationContext(), temp_string, Toast.LENGTH_LONG).show();
                    */

                    ((MainActivity)getActivity()).db.add_Text_Record(record_txt);
                }

                showAllRecords();
            }
        });

        Clear_Btn = (ImageButton)view.findViewById(R.id.tf_clear_btn);

        Clear_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).db.clear_All_Text_Record();
                /* //Clear with individual clear btn instead
                Floor_Text.setText("");
                Floor_Text.setHint(getResources().getString(R.string.tf_floor_text_hint));
                Space_Text.setText("");
                Space_Text.setHint(getResources().getString(R.string.tf_space_text_hint));
                Area_Text.setText("");
                Area_Text.setHint(getResources().getString(R.string.tf_area_text_hint));
                Direction_Text.setText("");
                Direction_Text.setHint(getResources().getString(R.string.tf_direction_text_hint));
                 */
                showAllRecords();
            }
        });

        Floor_Text_Clear_Btn = (ImageButton)view.findViewById(R.id.tf_floor_text_clear_btn);

        Floor_Text_Clear_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Floor_Text.setText("");
                Floor_Text.setHint(getResources().getString(R.string.tf_floor_text_hint));
                current_floor = "";
            }
        });

        Space_Text_Clear_Btn = (ImageButton)view.findViewById(R.id.tf_space_text_clear_btn);

        Space_Text_Clear_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Space_Text.setText("");
                Space_Text.setHint(getResources().getString(R.string.tf_space_text_hint));
                current_space = "";
            }
        });

        Area_Text_Clear_Btn = (ImageButton)view.findViewById(R.id.tf_area_text_clear_btn);

        Area_Text_Clear_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Area_Text.setText("");
                Area_Text.setHint(getResources().getString(R.string.tf_area_text_hint));
                current_area = "";
            }
        });

        Direction_Text_Clear_Btn = (ImageButton)view.findViewById(R.id.tf_direction_text_clear_btn);

        Direction_Text_Clear_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Direction_Text.setText("");
                Direction_Text.setHint(getResources().getString(R.string.tf_direction_text_hint));
                current_direction = "";
            }
        });

        showAllRecords();

        return view;
    }

    public void showAllRecords()
    {
        List<Record_Text> records = ((MainActivity)getActivity()).db.get_All_Text_Records();

        record_text_list = new ArrayList<Record_Text>(records);
        list_view_adapter = new Record_Text_Adapter();
        List_View = (ListView)view.findViewById(R.id.tf_listView);
        List_View.setAdapter(list_view_adapter);

        /*
        //To display all in TextView
        Record_TextView = (TextView)view.findViewById(R.id.tf_Record_Text);

        String temp_string = "";

        for ( int i = 0; i < records.size(); i++ )
        {
            temp_string += records.get(i).toString();
        }
        Record_TextView.setGravity(Gravity.LEFT);
        Record_TextView.setText(temp_string);
        */
    }

    private class Record_Text_Adapter extends ArrayAdapter<Record_Text> {
        public Record_Text_Adapter() {
            super(view.getContext(),R.layout.text_list_view_item, record_text_list);
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View itemView = convertView;

            // Not reusing convertView since views in different positions are different . Directly inflate a View
            //if ( itemView == null ) {
                itemView = getActivity().getLayoutInflater().inflate(R.layout.text_list_view_item, parent, false);
            //}

            final Record_Text current_record = record_text_list.get(position);

            LinearLayout info_linear_layout = (LinearLayout)itemView.findViewById(R.id.lv_info_linear_layout);

            TextView time_text_view = (TextView)itemView.findViewById(R.id.lv_time_textView);
            time_text_view.setText(current_record.getTime());

            ImageView floor_image_view = (ImageView)itemView.findViewById(R.id.lv_floor_imageView);
            int current_floor_icon_id = current_record.getFloorIconId();
            if ( floor_image_view != null && current_floor_icon_id != 0 )
            {
                floor_image_view.setImageResource(current_floor_icon_id);
            }
            else if ( floor_image_view != null )
            {
                info_linear_layout.removeView(floor_image_view);
            }

            TextView area_text_view = (TextView)itemView.findViewById(R.id.lv_area_textView);
            if ( area_text_view != null && !current_record.getArea().equals("") )
            {
                area_text_view.setText(current_record.getArea());
                Typeface customFont = Typeface.createFromAsset(getActivity().getAssets(), "fonts/PressStart2P.ttf");
                area_text_view.setTypeface(customFont);
            }
            else if ( area_text_view != null )
            {
                info_linear_layout.removeView(area_text_view);
            }

            TextView space_num_text_view = (TextView)itemView.findViewById(R.id.lv_space_num_textView);
            String current_space_num = current_record.getSpace_num();
            if ( space_num_text_view != null && current_space_num != null && current_space_num.length() != 0 )
            {
                Typeface customFont = Typeface.createFromAsset(getActivity().getAssets(), "fonts/PressStart2P.ttf");
                space_num_text_view.setTypeface(customFont);

                final float scale = getResources().getDisplayMetrics().density;

                if ( current_space_num.length() > 4 )
                {
                    space_num_text_view.setTextSize(16);
                    space_num_text_view.setPadding(0,(int)(24*scale + 0.5f),0,0);

                }
                else if ( current_space_num.length() > 3 )
                {
                    space_num_text_view.setTextSize(20);
                    space_num_text_view.setPadding(0,(int)(20*scale + 0.5f),0,0);
                }
                else
                {
                    space_num_text_view.setTextSize(28);
                    space_num_text_view.setPadding(0,(int)(14*scale + 0.5f),0,0);
                }

                space_num_text_view.setText(current_space_num);
            }
            else if ( space_num_text_view != null )
            {
                info_linear_layout.removeView(space_num_text_view);
            }

            ImageView direction_image_view = (ImageView)itemView.findViewById(R.id.lv_direction_from_lift_imageView);
            int current_direction_icon_id = current_record.getDirectionIconId();
            if ( direction_image_view != null && current_direction_icon_id != 0 )
            {
                direction_image_view.setImageResource(current_record.getDirectionIconId());
            }
            else if ( direction_image_view != null )
            {
                info_linear_layout.removeView(direction_image_view);
            }

            TextView description_text_view = (TextView)itemView.findViewById(R.id.lv_description_textView);
            description_text_view.setText(getRecordTextDescription(current_record));

            ImageButton clear_btn = (ImageButton)itemView.findViewById(R.id.lv_clear_btn);
            clear_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((MainActivity)getActivity()).db.delete_Text_Record(current_record.getId());

                    list_view_adapter.remove(record_text_list.get(position));

                    //This will not recreate proper layout
                    //list_view_adapter.notifyDataSetChanged();

                    List_View.setAdapter(list_view_adapter);
                }
            });

            return itemView;
        }

        public String getRecordTextDescription(Record_Text target_record_text)
        {
            String output_txt = "";

            String floor = target_record_text.getFloor();
            String space_num = target_record_text.getSpace_num();
            String area = target_record_text.getArea();
            String direction_from_lift = target_record_text.getDirection_from_lift();


            if ( !floor.equals("") || !area.equals("") || !space_num.equals("") )
            {
                output_txt += getString(R.string.record_text_description_1);
            }
            if ( !floor.equals("") )
            {
                output_txt += getString(R.string.record_text_description_2, floor);
            }
            if ( !area.equals("") )
            {
                output_txt += getString(R.string.record_text_description_3, area);
            }
            if ( !space_num.equals("") )
            {
                output_txt += getString(R.string.record_text_description_4, space_num);
            }
            if ( !direction_from_lift.equals("") )
            {
                if ( !floor.equals("") || !area.equals("") || !space_num.equals("") ) {
                    output_txt += getString(R.string.comma) + getString(R.string.record_text_description_5, getResources().getStringArray(R.array.record_text_direction_from_lift_string_array)[Integer.parseInt(direction_from_lift)]);
                }
                else
                {
                    output_txt += getString(R.string.record_text_description_5, getResources().getStringArray(R.array.record_text_direction_from_lift_string_array)[Integer.parseInt(direction_from_lift)]);
                }
            }

            return output_txt + getString(R.string.period) + "\n";
        }
    }
}
