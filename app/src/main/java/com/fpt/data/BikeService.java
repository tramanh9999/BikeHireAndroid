package com.fpt.data;

import com.fpt.model.Bike;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface BikeService {

    @GET("bikes")
    Call<List<Bike>> getBikes();


}
