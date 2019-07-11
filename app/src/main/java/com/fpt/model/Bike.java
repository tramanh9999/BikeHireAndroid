package com.fpt.model;


import com.fpt.model.BikeSlot;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Bike implements Serializable {

    @SerializedName("bikeId")
    @Expose
    private Integer bikeId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("garage")
    @Expose
    private Garage garage;
    @SerializedName("display_location")
    @Expose
    private String displayLocation;
    @SerializedName("latitude")
    @Expose
    private Integer latitude;
    @SerializedName("longitude")
    @Expose
    private Integer longitude;
    @SerializedName("slotList")
    @Expose
    private List<BikeSlot> slotList;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("noPlate")
    @Expose
    private String noPlate;
    @SerializedName("capacity")
    @Expose
    private String capacity;
    @SerializedName("cityId")
    @Expose
    private String cityId;










}
