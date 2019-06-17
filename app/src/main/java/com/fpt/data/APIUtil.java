package com.fpt.data;

import com.fpt.model.Bike;
import com.fpt.model.BikeSlot;

public class APIUtil {

    public static final String BASE_URL = "https://swd-bikehiring.herokuapp.com/bikehire/";

    public static BikeService getAccountService() {
        return RetrofitClient.getClient(BASE_URL).create(BikeService.class);
    }
}
