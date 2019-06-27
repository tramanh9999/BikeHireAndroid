package com.fpt.model;


import com.fpt.model.BikeSlot;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Bike implements Serializable {

    @SerializedName(value = "bikeId")
    int bikeId;

    @SerializedName(value = "name")
    String name;

    @SerializedName(value = "brand")
    String brand;

    @SerializedName(value = "noPlate")
    String noPlate;

    @SerializedName(value = "capacity")

    String capacity;
    @SerializedName(value = "ownerId")
    String ownerId;
    @SerializedName(value = "cityId")

    String cityId;
    @SerializedName(value = "slotList")
    List<BikeSlot> slotList;

    public int getBikeId() {
        return bikeId;
    }

    public void setBikeId(int bikeId) {
        this.bikeId = bikeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getNoPlate() {
        return noPlate;
    }

    public void setNoPlate(String noPlate) {
        this.noPlate = noPlate;
    }

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public List getSlotList() {
        return slotList;
    }

    public void setSlotList(List<BikeSlot> slotList) {
        this.slotList = slotList;
    }


}
