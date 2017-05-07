package group2.tcss450.uw.edu.official_tacotwosdays.start;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import group2.tcss450.uw.edu.official_tacotwosdays.R;
import group2.tcss450.uw.edu.official_tacotwosdays.home.HomeActivity;

/**
 * The Starting activity for the application, provides login and
 *      registration functionality.
 *
 *  @version 1.0
 *  @version Trevor N. Lowe
 */
public class MainActivity extends AppCompatActivity implements
    StartFragment.OnFragmentInteractionListener,
    LoginFragment.OnFragmentInteractionListener,
    RegisterFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Sets the start fragment to StartFragment
        if (savedInstanceState == null) {
            if (findViewById(R.id.startFragmentContainer) != null) {
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.startFragmentContainer, new StartFragment())
                        .commit();
            }
        }
    }

    /**
     * Swaps the current fragment with the one passed in.
     *
     * @param newFrag The fragment to swap into the existing fragment
     */
    private void swapFragments(Fragment newFrag) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.startFragmentContainer, newFrag)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onStartFragmentInteraction(int selection) {
        if (selection == StartFragment.SELECT_LOGIN) {
            // Go to Login Fragment
            swapFragments(new LoginFragment());
        } else if (selection == StartFragment.SELECT_REGISTER) {
            // Go to register Fragment
            swapFragments(new RegisterFragment());
        } else {
            throw new IllegalArgumentException("Selection not availiable!");
        }
    }

    @Override
    public void onLoginFragmentInteraction(String username) {
        finish();
        startActivity(new Intent(this, HomeActivity.class));
    }

    @Override
    public void onRegisterFragmentInteraction() {
        Context context = getApplicationContext();
        CharSequence message = "Registration Successful!";
        int duration = Toast.LENGTH_LONG;
        Toast.makeText(context, message, duration).show();

        swapFragments(new LoginFragment());
    }

}
