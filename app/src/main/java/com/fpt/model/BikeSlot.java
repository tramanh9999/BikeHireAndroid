package com.fpt.model;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BikeSlot {


    @SerializedName("sid")
    @Expose
    int sid=0;
    @SerializedName("s_from")
    @Expose
    Date s_from;
    @SerializedName("s_to")
    @Expose
    Date s_to;
    @SerializedName("fee")
    @Expose
    long fee;
    Bike bike;
}
