package com.fpt.bkv2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.fpt.model.Account;
import com.fpt.model.Garage;
import com.fpt.retrofit.APIUtil;
import com.fpt.service.AccountService;
import com.fpt.service.BikeService;
import com.fpt.model.Bike;

import com.fpt.sqllite.dao.AccountDAO;
import com.fpt.sqllite.database.AppDatabase;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.rtchagas.pingplacepicker.PingPlacePicker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Activity_Home extends AppCompatActivity implements NavigationHost {


    BikeService bikeService = APIUtil.getBikeService();
    AccountService accountService = APIUtil.getAccountService();
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    NavigationView navigationView;
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    int navigationPosition = 0;


    Account account;
    Bundle accountInfoBundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

//set up leftnavigation and top bar
        navigationView = findViewById(R.id.navigationView);
        toolbar = findViewById(R.id.toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        initView();
        accountInfoBundle = getIntent().getBundleExtra("accountBundle");
        account = (Account) accountInfoBundle.getSerializable("account");
        saveUser(account);
//        saveUser(userInfo);
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
//
//        navigationView.setCheckedItem(R.id.home);
//        toolbar.setTitle(getString(R.string.home));
//        fragmentTransaction.replace(R.id.fragment_home, new Fragment_Home()).commit();

//        chooseLocation(this.getCurrentFocus());
        PingPlacePicker.IntentBuilder builder = new PingPlacePicker.IntentBuilder();
        builder.setAndroidApiKey("13:B4:6B:07:56:A1:50:75:12:09:05:65:A9:68:E1:5E:F0:93:DC:16")
                .setMapsApiKey(String.valueOf(R.string.google_api_key));

        // If you want to set a initial location rather then the current device location.
        // NOTE: enable_nearby_search MUST be true.
        // builder.setLatLng(new LatLng(37.4219999, -122.0862462))

        try {
            Intent placeIntent = builder.build(this);
            startActivityForResult(placeIntent, PLACE_PICKER_REQUEST);
        } catch (Exception ex) {
            // Google Play services is not available...
        }
    }

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;


    AppDatabase appDatabase;


    private void saveUser(Account account) {
        appDatabase = AppDatabase.getInMemoryDatabase(this.getApplicationContext());
        AccountDAO accountDAO = appDatabase.accountDAO();
        //account.setEmail(userInfo.get("email").toString());
        accountDAO.insert(account);
        try {
            accountService.insert(account);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void initView() {
        setUpDrawLayout();
        toolbar.setTitle(R.string.home);
        navigationView.setNavigationItemSelectedListener(new NavigationListener(this));
        navigationView.setCheckedItem(navigationPosition);
    }


    int PLACE_PICKER_REQUEST = 1001;


    class NavigationListener implements NavigationView.OnNavigationItemSelectedListener {
        Activity acc;

        public NavigationListener(Activity activity) {
            this.acc = activity;
        }


        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.home: {
                    navigationView.setCheckedItem(R.id.home);
                    toolbar.setTitle(getString(R.string.home));
                    fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_home, new Fragment_Home()).commit();
                    break;
                }
                case R.id.account: {
                    toolbar.setTitle(getString(R.string.account));
                    navigationView.setCheckedItem(R.id.account);
                    fragmentTransaction = fragmentManager.beginTransaction();

                    fragmentTransaction.replace(R.id.fragment_home, new Fragment_Account()).commit();
                    break;

                }
                case R.id.garage: {
                    toolbar.setTitle(getString(R.string.garage));
                    navigationView.setCheckedItem(R.id.garage);
                    fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_home, new Fragment_Garage()).commit();
                    break;
                }
            }

            //create new activity
            if (item.getItemId() == R.id.logout) {
                signout();
                startActivity(new Intent(acc.getBaseContext(), Activity_Main.class));
            }
            item.setChecked(true);
            drawerLayout.closeDrawers();
            return true;

        }
    }

    public void chooseLocation(View view) {
        PingPlacePicker.IntentBuilder builder = new PingPlacePicker.IntentBuilder();
        builder.setAndroidApiKey("AIzaSyDLq39gCPXTrm2mXXd0Il7jkizT-lfXnRg")
                .setMapsApiKey("AIzaSyDRiM-Uq7PduDhgnjK9XmPsTI1atkUyEjQ");

        // If you want to set a initial location rather then the current device location.
        // NOTE: enable_nearby_search MUST be true.
        // builder.setLatLng(new LatLng(37.4219999, -122.0862462))

        try {
            Intent placeIntent = builder.build(this);
            startActivityForResult(placeIntent, PLACE_PICKER_REQUEST);
        } catch (Exception ex) {
            // Google Play services is not available...

            ex.printStackTrace();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {


        if ((requestCode == PLACE_PICKER_REQUEST) && (resultCode == RESULT_OK)) {
            Place place = PingPlacePicker.getPlace(data);
            if (place != null) {
                Toast.makeText(this, "You selected the place: " + place.getName(), Toast.LENGTH_SHORT).show();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);


    }


    private void signout() {
        FirebaseAuth.getInstance().signOut();
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
    @Override
    protected void onResume() {
        super.onResume();

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
