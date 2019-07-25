package com.fpt.model;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(tableName = "account")
public class Account implements Serializable {
    @SerializedName("id")
    @PrimaryKey(autoGenerate = true)
    int id;
    /*  "avatar": "string",
  "balance": 0,
  "email": "string",
  "garage": null,
  "id": 0,
  "phone": "string",
  "username": "string"*/
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("phone")
    @Expose
    private String phone;

    @SerializedName("balance")
    @Expose
    private double balance;

    @SerializedName("avatar")
    @Expose
    String avatar;

    @SerializedName("garage")
    @Expose
    String garage;


}