package com.neurospeech.hypercubesample;


import android.app.SearchManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;


import com.neurospeech.hypercubesample.adapters.NavigationListAdapter;
import com.neurospeech.hypercubesample.fragments.HomeFragment;
import com.neurospeech.hypercubesample.fragments.form.EditTextFragment;
import com.neurospeech.hypercubesample.fragments.recyclerviewsamples.HeaderFooterFragment;
import com.neurospeech.hypercubesample.fragments.recyclerviewsamples.ItemHeadersFragment;
import com.neurospeech.hypercubesample.model.MenuModel;

import java.util.ArrayList;

/**
 *  Home Activity
 */
public class MainActivity extends AppCompatActivity {
    TextView errorMessage;
    NavigationListAdapter navigationListAdapter;
    private DrawerLayout drawerLayout;
    private View navigationView;
    RecyclerView  leftDrawer;
    FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_main);
        /**
         * Initializing Drawer layout
         */
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        drawerLayout.setScrimColor(ContextCompat.getColor(getApplicationContext(), R.color.white_transparent));

        navigationListAdapter = new NavigationListAdapter(this);

        /**
         * Initializing NavigationView
         */

        navigationView = LayoutInflater.from(this).inflate(R.layout.navigation_drawer,drawerLayout,false);
        drawerLayout.addView(navigationView);
        leftDrawer=(RecyclerView) navigationView.findViewById(R.id.left_drawer);

        loadItems();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        leftDrawer.setLayoutManager(layoutManager);
        leftDrawer.setItemAnimator(new DefaultItemAnimator());
        leftDrawer.setAdapter(navigationListAdapter);

        frameLayout =(FrameLayout) findViewById(R.id.fragment_container);
        if (findViewById(R.id.fragment_container) != null) {
            // Create a new Fragment to be placed in the activity layout
            HomeFragment homeFragment = new HomeFragment();

            // In case this activity was started with special instructions from an
            // Intent, pass the Intent's extras to the fragment as arguments
            homeFragment.setArguments(getIntent().getExtras());
            fragmentTransaction(homeFragment);
        }


    }



    /**
     * getSupportFragmentManager() - Returns the SupportFragmentManager for interacting with fragments
     * associated with activity
     * FragmentTransaction is a way to add / replace / remove fragments
     * @param fragment - List of fragments associated with Activity
     */
    public void fragmentTransaction(Fragment fragment) {
        FragmentTransaction fragmentTransactions = getSupportFragmentManager().beginTransaction();
        fragmentTransactions.replace(R.id.fragment_container,fragment);
        fragmentTransactions.addToBackStack(null);
        fragmentTransactions.commit();
        drawerLayout.closeDrawer(navigationView);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        /**
         * Handle action bar item clicks here. The action bar will
         automatically handle clicks on the Home/Up button, so long
         as you specify a parent activity in AndroidManifest.xml.
         */
        return super.onOptionsItemSelected(item);
    }


    /**
     * Load the drawer layout items
     */
    public void loadItems(){
        Resources resources = getResources();
        ArrayList<MenuModel> navigationModels = new ArrayList<>();

        navigationModels.add(new MenuModel("Recycler View","Header/Footer", HeaderFooterFragment.class));
        navigationModels.add(new MenuModel("Recycler View","Item Headers", ItemHeadersFragment.class));
        navigationModels.add(new MenuModel("Form","Edit Text", EditTextFragment.class));

        navigationListAdapter.clear();
        navigationListAdapter.addAll(navigationModels);

    }


    /**
     * @param message - Error message to be populated on the screen, in case of any error resulting from API call
     */
    public void populateErrorMessage(String message) {
        if (message != null) {
            errorMessage.setText(message);
            errorMessage.setVisibility(View.VISIBLE);
        }
        else {
            errorMessage.setVisibility(View.GONE);
        }
    }
}
