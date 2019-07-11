package com.fpt.service;

import com.fpt.model.Account;
import com.fpt.model.Bike;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface AccountService {

    @GET("/accounts")
    Call<List<Account>> getAccounts();


    @POST("/accounts")
    Call<Account> getAccount(@Body String email);

    @POST("/accounts")
    Call<Account> insert(@Body Account account);

    @POST("/accounts")
    Call<List<Account>> getAccounts(@Body Bike bike);



}
