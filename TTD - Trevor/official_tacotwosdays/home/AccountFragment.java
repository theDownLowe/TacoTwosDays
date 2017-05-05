package group2.tcss450.uw.edu.official_tacotwosdays.home;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import group2.tcss450.uw.edu.official_tacotwosdays.R;

/**
 * This Fragment holds the users information that can be accessed from the
 *  drawer menu in the Home Activity. The user will have the option of changing
 *  their password, or certain preferences.
 *
 *  @version 1.0
 *  @author Trevor N. Lowe
 */
public class AccountFragment extends Fragment {

    public AccountFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_account, container, false);
    }

}
