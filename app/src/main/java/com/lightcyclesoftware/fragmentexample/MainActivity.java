package com.lightcyclesoftware.fragmentexample;

import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import de.greenrobot.event.EventBus;


public class MainActivity extends ActionBarActivity implements BlueFragment.OnBlueFragmentInteractionListener, RedFragment.OnRedFragmentInteractionListener {

    private ViewPager mViewPager;
    private MyAdapter mAdapter;
    private static final int NUM_ITEMS = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    @Override
    public void onResume() {
        super.onResume();
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
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    private void saveState() {

    }

    private void restoreStte() {

    }

    @Override
    public void onBlueFragmentInteraction(Uri uri) {

    }

    @Override
    public void onRedFragmentInteraction(Uri uri) {

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
    }
}
