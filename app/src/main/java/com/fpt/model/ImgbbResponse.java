package com.fpt.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ImgbbResponse {


    @SerializedName("data.display_url")
    @Expose
    String url;

}
