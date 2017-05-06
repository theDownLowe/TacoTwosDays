package groupn.tcss450.uw.edu.officialtaco;

import android.os.AsyncTask;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import java.util.ArrayList;

import groupn.tcss450.uw.edu.officialtaco.model.Setlist;

/**
 * Created by John on 5/4/2017.
 */

public class MyRecyclerViewAdapter extends RecyclerView
        .Adapter<MyRecyclerViewAdapter
        .MyViewHolder> {
    private static final String API_URL_ID = "https://maps.googleapis.com/maps/api/place/details/json?";
    private static final String API_KEY = "AIzaSyB6uUO1NN88F35RPpaxfWTOiXsyXFpgIAg";
    private static String LOG_TAG = "MyRecyclerViewAdapter";
    private ArrayList<Setlist.RestaurantItem> mDataset;
    private DisplayListFragment.OnFragmentInteractionListener mListener;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public CardView mCardView;
        public TextView mTitle;
        public TextView mAddress;
        public TextView mRating;
        public TextView mType;
        public Button mButt;
        public String mId;

        public MyViewHolder(View v) {
            super(v);
            mCardView = (CardView) v.findViewById(R.id.card_view);
            mTitle = (TextView) v.findViewById(R.id.item_title);
            mAddress = (TextView) v.findViewById(R.id.item_address);
            mRating = (TextView) v.findViewById(R.id.item_rating);
            mType = (TextView) v.findViewById(R.id.item_type);
            mButt = (Button) v.findViewById(R.id.item_button);

        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyRecyclerViewAdapter(ArrayList<Setlist.RestaurantItem> myDataset,
                                 DisplayListFragment.OnFragmentInteractionListener listener) {
        mDataset = myDataset;
        mListener = listener;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_restaurant_item, parent, false);
        // set the view's size, margins, paddings and layout parameters
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.mTitle.setText(mDataset.get(position).mtitle);
        holder.mAddress.setText(mDataset.get(position).maddress);
        holder.mType.setText(mDataset.get(position).mtype);
        holder.mRating.setText(mDataset.get(position).mrating);
        holder.mButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AsyncTask<String, Void, String> task = new RetrieveFeedTask();
                task.execute(mDataset.get(position).id);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
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

                argId = strings[0];
                urlObject = new URL(API_URL_ID + "placeid=" + argId + "&key=" + API_KEY);

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

            JSONObject ob = null;
            JSONArray ar = null;
            try {

                ob = new JSONObject(result);

                ob = ob.getJSONObject("result");
                mListener.onDisplayInteraction(ob);

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

    }

}