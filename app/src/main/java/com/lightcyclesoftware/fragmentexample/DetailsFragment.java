package com.lightcyclesoftware.fragmentexample;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ToggleButton;

import de.greenrobot.event.EventBus;

/**
 * Created by ewilliams on 2/27/15.
 */
public class DetailsFragment extends Fragment{

    TextView detailsTextView;

    public DetailsFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View wView = inflater.inflate(R.layout.fragment_details, container, false);
        detailsTextView = (TextView) wView.findViewById(R.id.detailsTextView);
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

    public void onEvent(Message message) {
        detailsTextView.setText((Html.fromHtml(message.getDescription())));
    }
}
