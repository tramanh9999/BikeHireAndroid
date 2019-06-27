package com.fpt.data;

import com.fpt.model.BaseResponse;
import com.fpt.model.Bike;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface BikeService {

    @GET("bikes/all/10")
    public Call<Bike[]> getAmountBikes();

    @POST("bikes")
    Call<BaseResponse> insert(@Body Bike bk);

}
