package com.fpt.bkv2;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fpt.model.Bike;
import com.fpt.retrofit.APIUtil;
import com.fpt.service.BikeService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Fragment_Home extends Fragment {

    public Fragment_Home() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        load();
    }
    BikeService bikeService=APIUtil.getBikeService();


    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    public void load() {
        Call<Bike[]> bikeCall = bikeService.all();
        System.out.println(System.currentTimeMillis());
        //call api for bikes
        bikeCall.enqueue(new Callback<Bike[]>() {
            @Override
            public void onResponse(Call<Bike[]> call, Response<Bike[]> response) {
                Bike[] arrayBikeList = response.body();
                List<Bike> arrayList = Arrays.asList(arrayBikeList);
                mAdapter = new Adapter_Bike(arrayList, getContext());
                recyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onFailure(Call<Bike[]> call, Throwable t) {
                System.out.println("Network failed");
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fragment__home, container, false);

        bikeService= APIUtil.getBikeService();
        recyclerView = view.findViewById(R.id.recycle_view);
        recyclerView.setHasFixedSize(true);
        // use a linear layout manager
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        // specify an adapter
        mAdapter = new Adapter_Bike(new ArrayList<Bike>(), getContext());
        recyclerView.setAdapter(mAdapter);
        return view;
    }
}
