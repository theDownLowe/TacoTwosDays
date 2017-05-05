package group2.tcss450.uw.edu.official_tacotwosdays.home;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import group2.tcss450.uw.edu.official_tacotwosdays.R;

/**
 * This is the Default drawer tab from the HomeActivty drawer,
 *      it allows the user to search for a restaurant with several
 *      different criteria, displays a map that shows the users current
 *      location and near-by restaurants, and has two card displays of
 *      restaurants, one with favorites and the other with history.
 *
 *      @version 1.0
 *      @author Trevor N. Lowe
 */
public class SearchFragment extends Fragment {

    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

}
