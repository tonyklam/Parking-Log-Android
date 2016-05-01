package com.a4dmacau.pakwai;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    Uri file;
    DBHandler db;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        db = new DBHandler(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.action_settings_1:
                String temp_string = getString(R.string.action_settings_1);
                Toast.makeText(getApplicationContext(), temp_string, Toast.LENGTH_LONG).show();
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
                    if (intent.resolveActivity(getPackageManager()) != null) {
                        startActivityForResult(intent, 100);
                    }
                }

                break;
            case R.id.action_settings_2:
                temp_string = getString(R.string.action_settings_2);
                Toast.makeText(getApplicationContext(), temp_string, Toast.LENGTH_LONG).show();
                ImageView bg_image = (ImageView)findViewById(R.id.mf_bg_image);
                bg_image.setImageResource(android.R.color.transparent);
                bg_image.setBackgroundResource(R.drawable.mf_background_0);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        private static final String ARG_SECTION_NUMBER = "section_number";

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            Bundle args;

            switch (position) {
                case 0:
                    MapFragment fragment1 = new MapFragment();
                    args = new Bundle();
                    args.putInt(ARG_SECTION_NUMBER, 1);
                    fragment1.setArguments(args);
                    return fragment1;
                case 1:
                    TextFragment fragment2 = new TextFragment();
                    args = new Bundle();
                    args.putInt(ARG_SECTION_NUMBER, 2);
                    fragment2.setArguments(args);
                    return fragment2;
            }
            return null;
        }

        @Override
        public int getCount() {
            // Show 2 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "SECTION 1";
                case 1:
                    return "SECTION 2";
            }
            return null;
        }
    }

    /********************
    * 1)Remove this onActivityResult will call same function in Fragment directly.
    * 2)If remain, this onActivityResult is called first, before the one in Fragment
    *********************/
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 100) {
            if (resultCode == RESULT_OK) {
                galleryAddPic();
                Set_Bg_Image();
            }
        }

        /* //onActivityResult in Fragment to be called
        * super.onActivityResult(requestCode, resultCode, data);
        */
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
        sendBroadcast(mediaScanIntent);
    }

    private void Set_Bg_Image()
    {
        Bitmap bitmap;
        //Get the dimensions of the bg_image View
        ImageView bg_image = (ImageView)findViewById(R.id.mf_bg_image);
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
}
