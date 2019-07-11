package com.fpt.model;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

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
@Entity(tableName = "account")
public class Account {
    @PrimaryKey(autoGenerate = true)
    int id;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("balance")
    @Expose
    private long balance;
    @SerializedName("avatar")
    @Expose
    String avatar;
    @Ignore
    @SerializedName("updateDate")
    @Expose
    Date updateDate;

    


}