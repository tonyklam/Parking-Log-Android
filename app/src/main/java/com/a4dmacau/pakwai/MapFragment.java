package com.a4dmacau.pakwai;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.method.Touch;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


/**
 * Created by tonylam on 15/04/2016.
 */
public class MapFragment extends Fragment implements View.OnTouchListener {

    //Max allowed duration for a "click", in milliseconds.
    private static final int MAX_CLICK_DURATION = 1000;

    //Max allowed distance to move during a "click", in DP.
    private static final int MAX_CLICK_DISTANCE = 15;

    private long pressStartTime;
    float Touch_X;
    float Touch_Y;

    int Pin_Width = 0;
    int Pin_Height = 0;
    int Pin_Id;
    float Pin_x;
    float Pin_y;
    private static final float PIN_NOT_INPUT = -99f;

    View view;
    RelativeLayout mf_relative_layout;
    ArrayList<ImageView> car_pins = new ArrayList();
    ToggleButton lift_btn;
    ToggleButton car_btn;
    ImageButton level_btn;
    ImageButton clear_btn;
    ImageButton save_btn;
    TextView time_text;
    ImageView bg_image;

    int floor;
    private static final int FLOOR_NOT_INPUT = -99;
    Uri file;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.map_fragment, container, false);
        mf_relative_layout = (RelativeLayout) view.findViewById(R.id.mf_relative_layout);
        mf_relative_layout.setOnTouchListener(this);

        clear_btn = (ImageButton)view.findViewById(R.id.mf_clear_btn);
        level_btn = (ImageButton)view.findViewById(R.id.mf_level_btn);
        floor = FLOOR_NOT_INPUT;
        save_btn = (ImageButton)view.findViewById(R.id.mf_save_btn);
        bg_image = (ImageView)view.findViewById(R.id.mf_bg_image);
        Pin_Id = R.drawable.mf_car_icon;
        Pin_x = PIN_NOT_INPUT;
        Pin_y = PIN_NOT_INPUT;

        time_text = (TextView)view.findViewById(R.id.mf_time_text);

        bg_image.setBackgroundResource(R.drawable.mf_background_0);


        /*
        //Taking Photo with an ImageButton named "photo_btn"

        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            photo_btn.setEnabled(false);
            ActivityCompat.requestPermissions(getActivity(), new String[] { Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE }, 0);
        }

        photo_btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                try{
                    file = Uri.fromFile(getOutputMediaFile());
                }
                catch (IOException ex){
                }

                if ( file != null )
                {
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, file);

                    //Check if the intent has an app that can handle
                    if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
                    startActivityForResult(intent, 100);
                    }
                }
            }
        });
        */

        /*
        //Old Car Toggle Button
        car_btn.setChecked(true);

        car_btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Pin_Id = R.drawable.mf_car_icon;
                car_btn.setChecked(true);
            }
        });

            <ToggleButton
            android:id="@+id/mf_car_btn"
            android:layout_width="40dp"
            android:layout_height="40dp"
            android:layout_marginTop="10dp"
            android:layout_marginLeft="20dp"
            android:background="@null"
            android:drawableTop="@drawable/mf_car_toggle"
            android:textOn=""
            android:textOff=""
            android:focusable="false"
            android:focusableInTouchMode="false" />
        */

        clear_btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                for ( int i = 0; i < car_pins.size(); i++ ){
                    mf_relative_layout.removeView(car_pins.remove(0));
                }
                Pin_x = PIN_NOT_INPUT;
                Pin_y = PIN_NOT_INPUT;
                floor = FLOOR_NOT_INPUT;

                level_btn.setImageResource(R.drawable.num_q_icon);
                time_text.setText("");
                time_text.setHint(getResources().getString(R.string.mf_time_text_hint));
            }
        });

        level_btn.setOnClickListener(new View.OnClickListener()
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
                        setFloor(np.getValue()-4);

                        d_np.dismiss();
                    }
                });

                d_np.show();
            }
        });

        save_btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Record_Map record_map = new Record_Map();
                String output_string;

                if ( Pin_x == PIN_NOT_INPUT && Pin_y == PIN_NOT_INPUT )
                {
                    output_string = getResources().getString(R.string.mf_record_null_hint);
                    Toast.makeText(getActivity().getApplicationContext(), output_string, Toast.LENGTH_LONG).show();
                    return;
                }
                else
                {
                    record_map.setTime(new Date());
                    record_map.setFloor(Integer.toString(floor));
                    record_map.setPin_x(Float.toString(Pin_x));
                    record_map.setPin_y(Float.toString(Pin_y));

                    String temp_string = "Recording X:" + Pin_x + ", Y:" + Pin_y;
                    Toast.makeText(getActivity().getApplicationContext(), temp_string, Toast.LENGTH_LONG).show();

                    ((MainActivity)getActivity()).db.clear_All_Map_Record();

                    ((MainActivity)getActivity()).db.add_Map_Record(record_map);
                }
            }
        });

        showAllRecords();

        return view;
    }

    public void setFloor(int target_floor)
    {
        switch (target_floor) {
            case -4:
                level_btn.setImageResource(R.drawable.num_n4_icon);
                break;
            case -3:
                level_btn.setImageResource(R.drawable.num_n3_icon);
                break;
            case -2:
                level_btn.setImageResource(R.drawable.num_n2_icon);
                break;
            case -1:
                level_btn.setImageResource(R.drawable.num_n1_icon);
                break;
            case 0:
                level_btn.setImageResource(R.drawable.num_0_icon);
                break;
            case 1:
                level_btn.setImageResource(R.drawable.num_1_icon);
                break;
            case 2:
                level_btn.setImageResource(R.drawable.num_2_icon);
                break;
            case 3:
                level_btn.setImageResource(R.drawable.num_3_icon);
                break;
            case 4:
                level_btn.setImageResource(R.drawable.num_4_icon);
                break;
            case 5:
                level_btn.setImageResource(R.drawable.num_5_icon);
                break;
            case 6:
                level_btn.setImageResource(R.drawable.num_6_icon);
                break;
        }
        floor = target_floor;
    }

    public void DeterminePinSize(int id)
    {
        BitmapDrawable bd = (BitmapDrawable) ContextCompat.getDrawable(getContext(), id);

        Pin_Height=bd.getBitmap().getHeight();
        Pin_Width=bd.getBitmap().getWidth();
    }

    public ImageView DrawPin(float x, float y, int id)
    {
        if (Pin_Height == 0){
            DeterminePinSize(id);
        }
        ImageView image = new ImageView(getActivity());
        image.setBackgroundResource(id);
        image.setX(x - Pin_Width/2);
        image.setY(y - Pin_Height/2);

        mf_relative_layout.addView(image);
        return image;
    }

    public void PutPin(float x, float y, int id)
    {
        ImageView new_pin = DrawPin(x, y, id);

        Pin_x = x;
        Pin_y = y;

        if ( Pin_Id == R.drawable.mf_car_icon )
        {
            for ( int i = 0; i < car_pins.size(); i++ ){
                mf_relative_layout.removeView(car_pins.remove(0));
            }
            car_pins.add(new_pin);
        }
    }


    public boolean onTouch(View v, MotionEvent e)
    {
        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                pressStartTime = System.currentTimeMillis();
                Touch_X = e.getX();
                Touch_Y = e.getY();
                break;
            }
            case MotionEvent.ACTION_UP: {
                long pressDuration = System.currentTimeMillis() - pressStartTime;
                if (pressDuration < MAX_CLICK_DURATION && distance(Touch_X, Touch_Y, e.getX(), e.getY()) < MAX_CLICK_DISTANCE) {
                    // Click event has occurred
                    PutPin(Touch_X, Touch_Y, Pin_Id);
                }
                break;
            }
            default:
                return v.onTouchEvent(e);
        }
        return true;
    }

    private float distance(float x1, float y1, float x2, float y2) {
        float dx = x1 - x2;
        float dy = y1 - y2;
        float distanceInPx = (float) Math.sqrt(dx * dx + dy * dy);
        return pxToDp(distanceInPx);
    }

    private float pxToDp(float px) {
        return px / getResources().getDisplayMetrics().density;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        if (requestCode == 0) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                //photo_btn.setEnabled(true);
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, final Intent data) {

        if (requestCode == 100) {
            if (resultCode == getActivity().RESULT_OK) {
                galleryAddPic();
                Set_Bg_Image();
            }
        }
    }

    private void Set_Bg_Image()
    {
        Bitmap bitmap;
        //Get the dimensions of the bg_image View
        int targetW = bg_image.getWidth();
        int targetH = bg_image.getHeight();

        //Get the dimesions of the photo
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        bitmap = BitmapFactory.decodeFile(file.getPath(), bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        /*
        String temp_string = "Target Width = " + Integer.toString(targetW) + ", Height = "+Integer.toString(targetH);
        Toast.makeText(getActivity().getApplicationContext(), temp_string, Toast.LENGTH_LONG).show();
        */

        Matrix matrix = new Matrix();
        matrix.postRotate(90);

        bitmap = BitmapFactory.decodeFile(file.getPath());

        bitmap = Bitmap.createBitmap(bitmap,0,0,bitmap.getWidth(),bitmap.getHeight(),matrix,true);

        //Remove original background
        bg_image.setBackgroundColor(Color.TRANSPARENT);

        bg_image.setScaleType(ImageView.ScaleType.FIT_CENTER);

        bg_image.setImageBitmap(bitmap);
    }

    private static File getOutputMediaFile() throws IOException{

        File mediaStorageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        return new File(mediaStorageDir.getPath() + File.separator +
                "IMG_"+ timeStamp + ".jpg");
    }

    private void galleryAddPic(){
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        mediaScanIntent.setData(file);
        getActivity().sendBroadcast(mediaScanIntent);
    }

    public void SetActionBarText(String text){

        ((MainActivity)getActivity()).getSupportActionBar().setTitle(text);
    }

    public void showAllRecords()
    {
        List<Record_Map> records = ((MainActivity)getActivity()).db.get_All_Map_Records();

        Record_Map current_map_record;
        for ( int i = 0; i < records.size(); i++ )
        {
            current_map_record = records.get(i);

            PutPin(current_map_record.getPin_x_as_Float(), current_map_record.getPin_y_as_Float(), Pin_Id);

            setFloor(current_map_record.getFloor_as_int());

            time_text.setText(current_map_record.getTime()+"停於下圖位置");

            //SetActionBarText(current_map_record.getTime()+"停於下圖位置");
        }
    }
}
