package com.fpt.bkv2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.fpt.data.APIUtil;
import com.fpt.data.BikeService;
import com.fpt.model.BaseResponse;
import com.fpt.model.Bike;
import com.fpt.model.BikeSlot;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HostGalleryActivity extends AppCompatActivity {

    Button bt;
    TextView bname;
    TextView bid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host_gala);
        bid = findViewById(R.id.txtbid);
        bname = findViewById(R.id.txtbname);
        bt=findViewById(R.id.btinsert);
       bikeService= APIUtil.getBikeService();
    }
BikeService bikeService;

    public void insertBike(View view) {
        Bike bk = new Bike();
        bk.setName(bname.getText().toString());
        int bidInt = Integer.parseInt(bid.getText().toString());
        bk.setBikeId(bidInt);
        bk.setSlotList(new ArrayList<BikeSlot>());
        Call<BaseResponse> baseResponseCall = bikeService.insert(bk);
       baseResponseCall.enqueue(new Callback<BaseResponse>() {
           @Override
           public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
               BaseResponse baseResponse=response.body();
               bt.setText("Inserted:"+ baseResponse.isMsg());
               Intent intent= new Intent(getBaseContext(),HomeActivity.class);
               startActivity(intent);
           }
           @Override
           public void onFailure(Call<BaseResponse> call, Throwable t) {

           }
       });
    }
}
