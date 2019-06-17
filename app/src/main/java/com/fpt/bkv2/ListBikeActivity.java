package com.fpt.bkv2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.fpt.data.APIUtil;
import com.fpt.data.BikeService;
import com.fpt.model.Bike;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ListBikeActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private BikeService mService;
    BikeAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_bike);
        mService = APIUtil.getAccountService();
        mRecyclerView = (RecyclerView) findViewById(R.id.recycle_view);
        mAdapter = new BikeAdapter(this, new ArrayList<Bike>(0), new BikeAdapter.PostItemListener() {

            @Override
            public void onPostClick(long id) {
                Toast.makeText(ListBikeActivity.this, "Post id is" + id, Toast.LENGTH_SHORT).show();
            }
        });

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        mRecyclerView.addItemDecoration(itemDecoration);

        loadBike();

    }

    private void loadBike() {

        mService.getBikes().enqueue(new Callback<List<Bike>>() {
            @Override
            public void onResponse(Call<List<Bike>> call, Response<List<Bike>> response) {

                        if (response.isSuccessful()) {
                    mAdapter.updateBikes(response.body());
                    Log.d("ListBikeActivity", "posts loaded from API");
                } else {
                    int statusCode = response.code();
                }
            }

            @Override
            public void onFailure(Call<List<Bike>> call, Throwable t) {
                Log.d("ListBikeActivity", "error loading from API");
            }


        }
        );


    }
}
