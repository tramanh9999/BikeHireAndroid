package com.fpt.retrofit;

import com.fpt.model.Garage;
import com.fpt.service.AccountService;
import com.fpt.service.BikeService;
import com.fpt.service.GarageService;
import com.fpt.service.ImageUpload;

public class APIUtil {

    public static final String BASE_URL = "http://dev-hirebike.herokuapp.com/bikehire/";
    public static final String ImageServer = "https://api.imgbb.com/1/upload?key=53d902f62768b188aaab4b5ec86e4f16";

    public static BikeService getBikeService() {
        return RetrofitClient.getClient(BASE_URL).create(BikeService.class);
    }

    public static AccountService getAccountService() {
        return RetrofitClient.getClient(BASE_URL).create(AccountService.class);
    }
    public static GarageService getGarageService() {
        return RetrofitClient.getClient(BASE_URL).create(GarageService.class);
    }
    public static ImageUpload getImageService() {
        return RetrofitClient.getClient(ImageServer).create(ImageUpload.class);
    }
}
