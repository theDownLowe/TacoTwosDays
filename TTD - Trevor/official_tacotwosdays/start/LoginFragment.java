package group2.tcss450.uw.edu.official_tacotwosdays.start;


import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import group2.tcss450.uw.edu.official_tacotwosdays.R;

public class LoginFragment extends Fragment implements View.OnClickListener{

    private static final String URL
            = "http://cssgate.insttech.washington.edu/" +
            "~tnlral/TacoTwosDays/login";

    private OnFragmentInteractionListener mListener;
    private EditText mUsernameText;
    private EditText mPasswordText;

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_login, container, false);
        Button b = (Button) v.findViewById(R.id.button_login_user);
        b.setOnClickListener(this);
        mUsernameText = (EditText) v.findViewById(R.id.editText_username);
        mPasswordText = (EditText) v.findViewById(R.id.editText_password);

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
        AsyncTask<String, Void, String> task = null;
        String username;

        if (mListener != null) {
            if (!mUsernameText.getText().toString().equals("")
                    && !mPasswordText.getText().toString().equals("")) {
                username = mUsernameText.getText().toString();

                task = new PostWebServiceTask();
                task.execute(URL, username);

            } else {
                if (mUsernameText.getText().toString().equals(""))
                    mUsernameText.setError("Cannot be empty!");
                if (mPasswordText.getText().toString().equals(""))
                    mPasswordText.setError("Cannot be empty!");
            }
        }
    }


    public interface OnFragmentInteractionListener {
        void onLoginFragmentInteraction(String username);
    }

    private class PostWebServiceTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            if (strings.length != 2) {
                throw new IllegalArgumentException("Two String arguments required.");
            }
            String response = "";
            HttpURLConnection urlConnection = null;
            String url = strings[0];
            try {
                java.net.URL urlObject = new URL(URL);
                urlConnection = (HttpURLConnection) urlObject.openConnection();
                urlConnection.setRequestMethod("POST");
                urlConnection.setDoOutput(true);
                OutputStreamWriter wr = new OutputStreamWriter(urlConnection.getOutputStream());
                String data = URLEncoder.encode("my_username", "UTF-8")
                        + "=" + URLEncoder.encode(strings[1], "UTF-8");
                wr.write(data);
                wr.flush();
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
                Toast.makeText(getActivity(), result, Toast.LENGTH_LONG)
                        .show();
                return;
            } else if (result.equals("") || result.equals(null)){
                mUsernameText.setError("Incorrect Username!");
            } else {
                String password = "";
                boolean found = false;
                for (int i = 0; i < result.length() && !found; i++) {
                    if (result.substring(i, i + 6).equals(",\"pwd\"")) {
                        int index = i+8;
                        while (result.charAt(index) != '"') {
                            password += result.charAt(index);
                            index++;
                        }

                        found = true;
                    }
                }

                if (password.equals(mPasswordText.getText().toString())) {
                    mListener.onLoginFragmentInteraction(mUsernameText.getText().toString());
                } else {
                    mPasswordText.setError("Incorrect Password!");
                }
            }
        }
    }
}

