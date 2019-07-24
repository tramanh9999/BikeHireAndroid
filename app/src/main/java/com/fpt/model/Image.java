package com.fpt.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Image {

    @SerializedName("url")
    @Expose
    String url;

    @SerializedName("id")
    @Expose
    int id;

}
