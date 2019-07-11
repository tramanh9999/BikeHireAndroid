package com.fpt.retrofit;

import com.fpt.service.AccountService;
import com.fpt.service.BikeService;

public class APIUtil {

    public static final String BASE_URL = "http://dev-hirebike.herokuapp.com/bikehire/";

    public static BikeService getBikeService() {
        return RetrofitClient.getClient(BASE_URL).create(BikeService.class);
    }

    public static AccountService getAccountService() {
        return RetrofitClient.getClient(BASE_URL).create(AccountService.class);
    }
}
