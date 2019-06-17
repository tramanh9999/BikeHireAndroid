package com.fpt.data;

import com.fpt.model.Account;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface AccountService {

    @GET("/accounts")
    Call<List<Account>> getAccounts();

}
