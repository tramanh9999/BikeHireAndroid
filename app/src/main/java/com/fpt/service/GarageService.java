package com.fpt.service;

import com.fpt.model.Account;
import com.fpt.model.Bike;
import com.fpt.model.Garage;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GarageService {
    @GET(value = "garage/{accountId}")
    Call<Garage> getGarageByAccountId(@Path(value = "accountId") String accountId);
    @POST(value = "garage/{accountId}")
    Call<Garage> insertGarage(@Path(value = "accountId") int accountId, @Body Garage garage);
}
