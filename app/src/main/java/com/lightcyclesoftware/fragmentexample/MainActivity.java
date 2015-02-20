package com.lightcyclesoftware.fragmentexample;

import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

import java.util.List;

import de.greenrobot.event.EventBus;


public class MainActivity extends ActionBarActivity {

    private ViewPager mViewPager;
    private MyAdapter mAdapter;
    private String SENDER_ID = "193349301890";
    private static final int NUM_ITEMS = 2;
    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Check device for Play Services APK.
        if (checkPlayServices()) {
            // If this check succeeds, proceed with normal processing.
            // Otherwise, prompt user to get valid Play Services APK.
            init();
        }

        Log.d("app", "onCreate()");
    }

    @Override
    public void onResume() {
        super.onResume();
        checkPlayServices();
        Log.d("app", "onResume()");
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("app", "onPause()");
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
        Log.d("app", "onStart()");
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
        Log.d("app", "onStop()");
    }

    @Override public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        Log.d("app", "onSaveInstanceState()");
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.d("app", "onRestoreInstanceState()");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("app", "onDestroy()");
    }

    public static class MyAdapter extends FragmentStatePagerAdapter {

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        @Override
        public Fragment getItem(int position) {
            switch(position) {
                case 0:
                    return new BlueFragment();

                case 1:
                    return new RedFragment();

            }

            return null;
        }
    }

    public void onEventMainThread(Ball event) {
        Log.d("event", "ball thrown");
        Toast.makeText(this,event.getMessage(),Toast.LENGTH_SHORT).show();
    }

    private void init() {
        mViewPager = (ViewPager) findViewById(R.id.pager);
        //layout for phone form factors
        if (mViewPager != null) {
            mAdapter = new MyAdapter(getSupportFragmentManager());
            mViewPager.setAdapter(mAdapter);
        } else { //tablet form factor
            FragmentManager wManager = getSupportFragmentManager();
            wManager.beginTransaction().add(R.id.list,new BlueFragment(), "blue")
                    .add(R.id.viewer, new RedFragment(), "red").commit();
        }
        new GcmRegistrationAsyncTask(this).execute();
    }

    /**
     * Check the device to make sure it has the Google Play Services APK. If
     * it doesn't, display a dialog that allows users to download the APK from
     * the Google Play Store or enable it in the device's system settings.
     */
    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Log.i("event", "This device is not supported.");
                finish();
            }
            return false;
        }
        return true;
    }

}
