package group2.tcss450.uw.edu.official_tacotwosdays.start;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import group2.tcss450.uw.edu.official_tacotwosdays.R;

public class StartFragment extends Fragment implements View.OnClickListener {

    public static int SELECT_LOGIN = -1;
    public static int SELECT_REGISTER = -2;

    private OnFragmentInteractionListener mListener;

    public StartFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_start, container, false);
        Button b = (Button) v.findViewById(R.id.button_login);
        b.setOnClickListener(this);
        b = (Button) v.findViewById(R.id.button_register);
        b.setOnClickListener(this);

        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View view) {
        if (mListener != null) {
            switch (view.getId()) {
                case R.id.button_login:
                    mListener.onStartFragmentInteraction(SELECT_LOGIN);
                    break;
                case R.id.button_register:
                    mListener.onStartFragmentInteraction(SELECT_REGISTER);
            }
        }
    }

    public interface OnFragmentInteractionListener {
        void onStartFragmentInteraction(int selection);
    }
}

