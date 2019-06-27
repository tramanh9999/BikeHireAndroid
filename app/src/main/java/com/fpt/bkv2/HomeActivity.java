package com.fpt.bkv2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.fpt.data.APIUtil;
import com.fpt.data.BikeService;
import com.fpt.model.Bike;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {


    BikeService bikeService = APIUtil.getBikeService();
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        recyclerView = findViewById(R.id.recycle_view);
// use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        // specify an adapter
        mAdapter= new BikeAdapter(new ArrayList<Bike>());
        recyclerView.setAdapter(mAdapter);

    }


    public void load(View view) {

        Call<Bike[]> bikeCall = bikeService.getAmountBikes();
        System.out.println(System.currentTimeMillis());
        bikeCall.enqueue(new Callback<Bike[]>() {
            @Override
            public void onResponse(Call<Bike[]> call, Response<Bike[]> response) {
              Bike[] arrayBikeList = response.body();
//                mAdapter = new BikeAdapter(arrayBikeList);
//                recyclerView.setAdapter(mAdapter);
                List<Bike> arrayList= Arrays.asList(arrayBikeList);
               mAdapter= new BikeAdapter(arrayList);
               recyclerView.setAdapter(mAdapter);
            }
            @Override
            public void onFailure(Call<Bike[]> call, Throwable t) {
                System.out.println("Network failed");
            }
    });

    }
}
