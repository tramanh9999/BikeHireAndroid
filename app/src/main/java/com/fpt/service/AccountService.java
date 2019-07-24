package com.fpt.service;

import com.fpt.model.Account;
import com.fpt.model.Bike;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface AccountService {

    @GET("/accounts")
    Call<List<Account>> getAccounts();
    @GET(value = "accounts")
    Call<Account> getAccountByEmail( @Query(value = "email") String email);
    @POST("accounts")
    Call<Account> insert(@Body Account account);
    @POST("accounts")
    Call<List<Account>> getAccounts(@Body Bike bike);







}
