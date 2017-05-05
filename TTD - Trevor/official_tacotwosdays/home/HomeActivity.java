package group2.tcss450.uw.edu.official_tacotwosdays.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import group2.tcss450.uw.edu.official_tacotwosdays.R;
import group2.tcss450.uw.edu.official_tacotwosdays.start.MainActivity;

/**
 * The main activity that is used after the user has logged in. It contains
 *      a drawer with several different functionalities including: searching,
 *      updating account information, accessing favorites, accessing history,
 *      and logging out.
 *
 *      @version 1.0
 *      @author Trevor N. Lowe
 */
public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Creates the navigation drawer
        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                drawer.bringToFront();
                drawer.requestLayout();
            }
        };
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        // Sets navigation view
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // Sets starting fragment to search fragment
        if (savedInstanceState == null) {
            if (findViewById(R.id.homeFragmentContainer) != null) {
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.homeFragmentContainer, new SearchFragment())
                        .commit();
            }
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Swaps the current fragment in HomeActivity with the
     *      fragment passed to it.
     *
     * @param newFrag the new fragment replace current fragment
     */
    private void swapFragments(Fragment newFrag) {
        if (findViewById(R.id.homeFragmentContainer) != null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.homeFragmentContainer, newFrag)
                    .commit();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (!item.isChecked()) {
            item.setChecked(true);

            if (id == R.id.nav_home) {
                // Navigates to Search fragment
                swapFragments(new SearchFragment());
            } else if (id == R.id.nav_favorites) {
                // Navigates to Favorites fragment
                swapFragments(new FavoritesFragment());
            } else if (id == R.id.nav_history) {
                // Navigates to History fragment
                swapFragments(new HistoryFragment());
            } else if (id == R.id.nav_account) {
                // Navigates to Account fragment
                swapFragments(new AccountFragment());
            } else if (id == R.id.nav_logout) {
                // Returns to main activity
                finish();
                startActivity(new Intent(this, MainActivity.class));
            }
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
