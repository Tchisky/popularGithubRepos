package com.ayoub.el.khatab.unitedremote_mobilecodingchallenge.UI.Activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.ayoub.el.khatab.unitedremote_mobilecodingchallenge.R;
import com.ayoub.el.khatab.unitedremote_mobilecodingchallenge.UI.Fragments.Settings.SettingsFragment;
import com.ayoub.el.khatab.unitedremote_mobilecodingchallenge.UI.Fragments.TrendingRepositories.TrendingReposFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class Main extends AppCompatActivity {

    public static final int FRAGMENT_PLACEHOLDER_ID = R.id.fragment_place_holder;

    // will be used to unbind views in onDestroy()
    private Unbinder unbinder;

    private FragmentManager manager;

    private TrendingReposFragment trendingReposFragment;
    private SettingsFragment settingsFragment;


    @BindView(R.id.bottom_navigation_view)
    BottomNavigationView bottomNavigation;

    @Override
    protected void onStart() {
        super.onStart();

        // disable default actionbar
        if (getSupportActionBar() != null)
            getSupportActionBar().hide();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        // bind views
        unbinder = ButterKnife.bind(this);

        manager = getSupportFragmentManager();

        // init fragments
        trendingReposFragment = new TrendingReposFragment();
        settingsFragment = new SettingsFragment();

        // display trending repos fragment
        displayFragmentAdd(manager, trendingReposFragment, FRAGMENT_PLACEHOLDER_ID);

    }


    @Override
    protected void onResume() {
        super.onResume();

        bottomNavigation.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener
            = menuItem -> {
        switch (menuItem.getItemId()) {

            // display trending fragment
            case R.id.trending:
                displayFragmentReplace(manager, trendingReposFragment, FRAGMENT_PLACEHOLDER_ID);
                return true;

            // display settings fragment
            case R.id.settings:
                displayFragmentReplace(manager, settingsFragment, FRAGMENT_PLACEHOLDER_ID);
                return true;

            default:
                return false;
        }
    };


    private void displayFragmentAdd(FragmentManager manager, Fragment fragment, int containerResId) {

        manager.beginTransaction()
                .add(containerResId, fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
                .commit();
    }

    private void displayFragmentReplace(FragmentManager manager, Fragment fragment, int containerResId)
    {
        manager.beginTransaction()
                .replace(containerResId, fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
                .commit();
    }


}
