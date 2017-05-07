package group2.tcss450.uw.edu.official_tacotwosdays.home;



import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

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
public class SearchFragment extends Fragment implements View.OnClickListener {
    private static final String API_URL_ID = "https://maps.googleapis.com/maps/api/place/details/json?";
    private static final String API_URL_TEXT = "https://maps.googleapis.com/maps/api/place/textsearch/json?";
    private static final String API_KEY = "AIzaSyB6uUO1NN88F35RPpaxfWTOiXsyXFpgIAg";
    private SearchFragment.OnFragmentInteractionListener mListener;

    private EditText mSearchId;
    private EditText mLocationId;
    private EditText mTypeId;

    private Boolean mSingleSearch;

    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_search, container, false);
        Button bb = (Button) v.findViewById(R.id.getRsearchButton);
        bb.setOnClickListener(this);
        Button b = (Button) v.findViewById(R.id.getRbutton2);
        b.setOnClickListener(this);
        mSearchId = (EditText) v.findViewById(R.id.getRsearchText);
        mLocationId = (EditText) v.findViewById(R.id.getRloctext);
        mTypeId = (EditText) v.findViewById(R.id.getRtypetext);
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
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onClick(View view) {
        AsyncTask<String, Void, String> task = new RetrieveFeedTask();
        if (mListener != null) {
            switch (view.getId()) {
                case R.id.getRsearchButton:
                    mSingleSearch = true;
                    task.execute(mSearchId.getText().toString());
                    break;
                case R.id.getRbutton2:
                    mSingleSearch = false;
                    task.execute(mLocationId.getText().toString(),mTypeId.getText().toString());
                    break;

            }
        }
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onDisplayInteraction(JSONObject obj);
        void onDisplayListInteraction(JSONArray ar);
    }


    class RetrieveFeedTask extends AsyncTask<String, Void, String> {

        Exception exception;

        @Override
        protected String doInBackground(String... strings) {
            String response = "";
            HttpURLConnection urlConnection = null;
            String argId = "";
            String argLoc = "";
            String argType = "";


            try {
                URL urlObject = null;

                if(mSingleSearch) {
                    argId = strings[0];
                    urlObject = new URL(API_URL_ID + "placeid=" + argId + "&key=" + API_KEY);
                } else {
                    argType = strings[1];
                    argLoc = strings[0];
                    urlObject = new URL(API_URL_TEXT + "query=" + argType + " restaurant in " + argLoc + "&key=" + API_KEY);
                }
                urlConnection = (HttpURLConnection) urlObject.openConnection();
                InputStream content = urlConnection.getInputStream();
                BufferedReader buffer = new BufferedReader(new InputStreamReader(content));
                String s = "";
                while ((s = buffer.readLine()) != null) {
                    response += s;
                }
            } catch (Exception e) {
                response = "Unable to connect, Reason: "
                        + e.getMessage();
            } finally {
                if (urlConnection != null)
                    urlConnection.disconnect();
            }
            return response;
        }

        @Override
        protected void onPostExecute(String result) {
            // Something wrong with the network or the URL.
            if (result.startsWith("Unable to")) {
                Toast.makeText(getActivity().getApplicationContext(), result, Toast.LENGTH_LONG)
                        .show();
                return;
            }

            JSONObject ob = null;
            JSONArray ar = null;
            try {

                ob = new JSONObject(result);
                if(mSingleSearch) {
                    ob = ob.getJSONObject("result");
                    mListener.onDisplayInteraction(ob);
                }else {
                    ar = ob.getJSONArray("results");
                    mListener.onDisplayListInteraction(ar);

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

    }
    
    
}
