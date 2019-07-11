package com.fpt.bkv2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.fpt.model.Account;
import com.fpt.retrofit.APIUtil;
import com.fpt.service.AccountService;
import com.fpt.service.BikeService;
import com.fpt.model.Bike;
import com.fpt.sqllite.dao.AccountDAO;
import com.fpt.sqllite.database.AppDatabase;
import com.fpt.sqllite.database.AppDatabase_Impl;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Activity_Home extends AppCompatActivity implements NavigationHost {


    BikeService bikeService = APIUtil.getBikeService();
    AccountService accountService= APIUtil.getAccountService();
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    NavigationView navigationView;
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    int navigationPosition = 0;

    TextView username;
    TextView email;
    Bundle userInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home);

        recyclerView = findViewById(R.id.recycle_view);
        recyclerView.setHasFixedSize(true);
        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        // specify an adapter
        mAdapter = new Adapter_Bike(new ArrayList<Bike>());
        recyclerView.setAdapter(mAdapter);
//set up leftnavigation and top bar
        navigationView = findViewById(R.id.navigationView);
        toolbar = findViewById(R.id.toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        initView();
        userInfo = getIntent().getBundleExtra("userInfo");
        load();
        saveUser(userInfo);
    }

    AppDatabase appDatabase;

    private void saveUser(Bundle userInfo) {
        appDatabase = AppDatabase.getInMemoryDatabase(this.getBaseContext());
        AccountDAO accountDAO = appDatabase.accountDAO();
        Account account = new Account();
        if(userInfo!=null){
            account.setEmail(userInfo.get("email").toString());
            accountDAO.insert(account);
            accountService.insert(account);

        }


    }

    @Override
    protected void onResume() {
        super.onResume();


    }

    private void initView() {
//        setSupportActionBar(toolbar);
        setUpDrawLayout();
        toolbar.setTitle(R.string.home);
        navigationView.setNavigationItemSelectedListener(new NavigationListener(this));
        navigationView.setCheckedItem(navigationPosition);

    }

    public void reload(View view) {
        load();
    }

    class NavigationListener implements NavigationView.OnNavigationItemSelectedListener {

        Activity acc;

        public NavigationListener(Activity activity) {
            this.acc = activity;
        }

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            if (item.getItemId() == R.id.home) {
                navigationView.setCheckedItem(R.id.home);
                toolbar.setTitle(getString(R.string.home));
                startActivity(new Intent(acc, Activity_Home.class));
            }
            if (item.getItemId() == R.id.account) {
                toolbar.setTitle(getString(R.string.account));
                navigationView.setCheckedItem(R.id.account);
                ((NavigationHost) acc).navigateTo(new Fragment_Account(), true);

            }
            if (item.getItemId() == R.id.garage) {
                toolbar.setTitle(getString(R.string.garage));
                navigationView.setCheckedItem(R.id.garage);
                ((NavigationHost) acc).navigateTo(new Fragment_Garage(), true);

            }
            item.setChecked(true);
            drawerLayout.closeDrawers();
            return true;
        }

    }

    //connect drawlayout with toolbar
    private void setUpDrawLayout() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawopen, R.string.drawclose);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.top_app_bar, menu);
//        return true;
//
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        load();
//    }

    public void load() {

        Call<Bike[]> bikeCall = bikeService.all();
        System.out.println(System.currentTimeMillis());
        //call api for bikes
        bikeCall.enqueue(new Callback<Bike[]>() {
            @Override
            public void onResponse(Call<Bike[]> call, Response<Bike[]> response) {
                Bike[] arrayBikeList = response.body();
                List<Bike> arrayList = Arrays.asList(arrayBikeList);
                mAdapter = new Adapter_Bike(arrayList);
                recyclerView.setAdapter(mAdapter);

            }

            @Override
            public void onFailure(Call<Bike[]> call, Throwable t) {
                System.out.println("Network failed");
            }
        });

    }

    @Override
    public void navigateTo(Fragment fragment, boolean addToBackstack) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction().replace(R.id.new_fragment, fragment);
        if (addToBackstack) {
            transaction.addToBackStack(null);
        }
        transaction.commit();
    }
}
