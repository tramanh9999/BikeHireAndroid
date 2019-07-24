package com.fpt.service;

import com.fpt.model.Account;
import com.fpt.model.Bike;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ImageUpload {


    @POST
    Call<String> getAccounts(@Field("imageCode") String image);







}
