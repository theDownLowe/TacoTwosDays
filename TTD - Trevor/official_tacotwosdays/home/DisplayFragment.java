package group2.tcss450.uw.edu.official_tacotwosdays.home;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class DisplayFragment extends Fragment {


    public DisplayFragment() {
        // Required empty public constructor
    }

    @Override
    public void onStart() {
        super.onStart();
        if (getArguments() != null) {
            CharSequence title = getArguments().getCharSequence("sTitle");
            CharSequence url = getArguments().getCharSequence("sUrl");
            CharSequence website = getArguments().getCharSequence("sWebsite");
            CharSequence phone = getArguments().getCharSequence("sPhone");
            CharSequence address = getArguments().getCharSequence("sAddress");
            CharSequence rating = getArguments().getCharSequence("sRating");
            CharSequence hours = getArguments().getCharSequence("sHours");
            CharSequence types = getArguments().getCharSequence("sTypes");

            updateContent(title, url, website, phone,
                    address, rating, hours, types);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_display, container, false);
    }


    public void updateContent(CharSequence title, CharSequence url, CharSequence website,
                              CharSequence phone, CharSequence address, CharSequence rating,
                              CharSequence hours, CharSequence types) {
        TextView tv = (TextView) getActivity().findViewById(R.id.displayTitle);
        TextView tv1 = (TextView) getActivity().findViewById(R.id.displayUrl);
        TextView tv2 = (TextView) getActivity().findViewById(R.id.displayWebsite);
        TextView tv3 = (TextView) getActivity().findViewById(R.id.displayPhone);
        TextView tv4 = (TextView) getActivity().findViewById(R.id.displayAddress);
        TextView tv5 = (TextView) getActivity().findViewById(R.id.displayRating);
        TextView tv6 = (TextView) getActivity().findViewById(R.id.displayHours);
        TextView tv7 = (TextView) getActivity().findViewById(R.id.displayTypes);

        tv.setText(title);
        tv1.setText(url);
        tv2.setText(website);
        tv3.setText(phone);
        tv4.setText(address);
        tv5.setText(rating);
        tv6.setText(hours);
        tv7.setText(types);
    }

}
