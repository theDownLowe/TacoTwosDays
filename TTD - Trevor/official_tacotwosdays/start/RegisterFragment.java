package group2.tcss450.uw.edu.official_tacotwosdays.start;


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

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import group2.tcss450.uw.edu.official_tacotwosdays.R;

public class RegisterFragment extends Fragment implements View.OnClickListener {

    private static final String URL
            = "http://cssgate.insttech.washington.edu/" +
            "~tnlral/TacoTwosDays/register";

    private OnFragmentInteractionListener mListener;
    private EditText mUsernameText;
    private EditText mPasswordText;
    private EditText mRePasswordText;

    public RegisterFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof RegisterFragment.OnFragmentInteractionListener) {
            mListener = (RegisterFragment.OnFragmentInteractionListener) context;
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_register, container, false);
        Button b = (Button) v.findViewById(R.id.button_registerUser);
        b.setOnClickListener(this);
        mUsernameText = (EditText) v.findViewById(R.id.editText_newUsername);
        mPasswordText = (EditText) v.findViewById(R.id.editText_newPassword);
        mRePasswordText = (EditText) v.findViewById(R.id.editText_retypePassword);

        return v;
    }

    @Override
    public void onClick(View view) {
        if (mListener != null) {
            String usernameText = mUsernameText.getText().toString();
            String passwordText = mPasswordText.getText().toString();
            String rePasswordText = mRePasswordText.getText().toString();
            if (usernameText.equals("") || passwordText.equals("") || rePasswordText.equals("") ||
                    !passwordText.equals(rePasswordText)) {
                if (usernameText.equals(""))
                    mUsernameText.setError("Cannot be empty!");
                if (passwordText.equals(""))
                    mPasswordText.setError("Cannot be empty!");
                if (rePasswordText.equals(""))
                    mRePasswordText.setError("Cannot be empty!");
                if (!passwordText.equals("") && !rePasswordText.equals(""))
                    mRePasswordText.setError("Passwords do not match!");
            } else if (usernameText.contains(",") || (usernameText.contains("\""))) {
                mUsernameText.setError("Cannot contain , or \"!");
            } else {
                AsyncTask<String, Void, String> task = new newUserWebServiceTask();
                task.execute(URL, usernameText, passwordText);
            }
        }


    }

    public interface OnFragmentInteractionListener {
        void onRegisterFragmentInteraction();
    }

    private class newUserWebServiceTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            if (strings.length != 3) {
                throw new IllegalArgumentException("Three String arguments required.");
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
                data += '&';
                data += URLEncoder.encode("my_password", "UTF-8")
                        + "=" + URLEncoder.encode(strings[2], "UTF-8");
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
            Log.e("Register", "result: " + result);

            if (result.contains("Fatal error")) {
                mUsernameText.setError("Username Already Exists!");
            } else {
                mListener.onRegisterFragmentInteraction();
            }
        }
    }
}

