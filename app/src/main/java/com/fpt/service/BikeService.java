package com.fpt.service;

import com.fpt.model.BaseResponse;
import com.fpt.model.Bike;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface BikeService {


    @GET("bikes/all/10")
    public Call<Bike[]> getAmountBikes();


    @GET("bikes/all")
    public Call<Bike[]> all();

    @POST("bikes")
    Call<BaseResponse> insert(@Body Bike bk);

    @PUT("bikes")
    Call<BaseResponse> update (@Body Bike bk);

    @DELETE("bikes/{id}")
    Call<BaseResponse> delete(@Path("id") int id);

}
