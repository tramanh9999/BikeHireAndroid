package com.fpt.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class BaseResponse implements Serializable {

    @SerializedName("inserted")
    boolean msg;


    public boolean isMsg() {
        return msg;
    }

    public void setMsg(boolean msg) {
        this.msg = msg;
    }

    public BaseResponse(boolean msg) {
        this.msg = msg;
    }


}
