package group2.tcss450.uw.edu.official_tacotwosdays.home;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import group2.tcss450.uw.edu.official_tacotwosdays.R;

/**
 *  This fragment contains the history of restaurants that the user
 *      has selected. It is accessed through the history menu item
 *      from the drawer in HomeActivity.
 *
 *  @version 1.0
 *  @author Trevor N. Lowe
 */
public class HistoryFragment extends Fragment {


    public HistoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_history, container, false);
    }

}
