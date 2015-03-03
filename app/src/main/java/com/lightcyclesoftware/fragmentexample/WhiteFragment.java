package com.lightcyclesoftware.fragmentexample;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;


public class WhiteFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<Message>> {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ListView mListView;
    private RssAdapter mAdapter;
    private Loader mLoader;
    private ToggleButton mToggleButton;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RedFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WhiteFragment newInstance(String param1, String param2) {
        WhiteFragment fragment = new WhiteFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public WhiteFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View wView = inflater.inflate(R.layout.fragment_white, container, false);
        mToggleButton = (ToggleButton) wView.findViewById(R.id.toggleButton);
        mToggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new Ball("red toggle: " + Boolean.toString(mToggleButton.isChecked())));
            }
        });

        mListView = (ListView) wView.findViewById(R.id.listView);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                EventBus.getDefault().post(mAdapter.getItem(position));
            }
        });

        return wView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("toggle", mToggleButton.isChecked());
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            // Restore last state for checked position.
            mToggleButton.setChecked(savedInstanceState.getBoolean("toggle", false));
        }
        mAdapter = new RssAdapter(getActivity(), 0, new ArrayList<Message>());
        mListView.setAdapter(mAdapter);
        mLoader =  getLoaderManager().initLoader(0, null, this);
    }

    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        return new SampleLoader(getActivity());
    }

    @Override
    public void onLoadFinished(Loader<List<Message>> loader, List<Message> data) {
        mAdapter.clear();
        mAdapter.addAll(data);
        mAdapter.notifyDataSetChanged();
        Toast.makeText(getActivity(), "Data Updated", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLoaderReset(Loader<List<Message>> loader) {

    }

    public void onEvent(Ball event) {

    }


}
