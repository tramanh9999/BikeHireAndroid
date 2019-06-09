package com.fpt.bkv2;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.google.android.material.navigation.NavigationView;


/**
 * A simple {@link Fragment} subclass.
 */
public class Left_NavigationFragment extends Fragment {


    public Left_NavigationFragment() {
        // Required empty public constructor
    }

    NavigationView navigationView;
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    int navigationPosition = 0;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_left_nav, container, false);

        navigationView = view.findViewById(R.id.navigationView);
        toolbar = view.findViewById(R.id.toolbar);
        drawerLayout = view.findViewById(R.id.drawer_layout);
        initView();
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    private void initView() {
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        setUpDrawLayout();
        navigationView.setCheckedItem(navigationPosition);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.navItem3d) {
                    toolbar.setTitle(getString(R.string.three));

                    navigationView.setCheckedItem(R.id.navItem3d);
                    ((NavigationHost) getActivity()).navigateTo(new SearchFragment(), true);
                }
                if (item.getItemId() == R.id.navItemacc) {
                    toolbar.setTitle(getString(R.string.acc));
                    navigationView.setCheckedItem(R.id.navItemacc);
                }
                if (item.getItemId() == R.id.navItemmenu) {
                    toolbar.setTitle(getString(R.string.menu));
                    navigationView.setCheckedItem(R.id.navItemmenu);
                    ((NavigationHost) getActivity()).navigateTo(new BottomNavFragment(), true);
                }
                if (item.getItemId() == R.id.navItemsearch) {
                    toolbar.setTitle(getString(R.string.search));
                    navigationView.setCheckedItem(R.id.navItemsearch);
                }
                item.setChecked(true);
                drawerLayout.closeDrawers();
                return true;
            }
        });

    }


    //connect drawlayout with toolbar
    private void setUpDrawLayout() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this.getActivity(), drawerLayout, toolbar, R.string.drawopen, R.string.drawclose);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

}
