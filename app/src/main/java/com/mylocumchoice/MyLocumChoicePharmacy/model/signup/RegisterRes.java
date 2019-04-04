package com.mylocumchoice.MyLocumChoicePharmacy.model.signup;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class RegisterRes implements Serializable
{

    @SerializedName("gphc_no")
    @Expose
    private Integer gphcNo;
    @SerializedName("name")
    @Expose
    private String name;


    @SerializedName("gphc_expiry_date")
    @Expose
    private String gphc_expiry_date;

    public Integer getGphcNo() {
        return gphcNo;
    }

    public void setGphcNo(Integer gphcNo) {
        this.gphcNo = gphcNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGphc_expiry_date() {
        return gphc_expiry_date;
    }

    public void setGphc_expiry_date(String gphc_expiry_date) {
        this.gphc_expiry_date = gphc_expiry_date;
    }

}
