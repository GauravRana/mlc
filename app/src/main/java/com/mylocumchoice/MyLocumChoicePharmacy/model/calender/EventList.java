package com.mylocumchoice.MyLocumChoicePharmacy.model.calender;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EventList {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("name")
    @Expose
    private Object name;
    @SerializedName("brief_desc")
    @Expose
    private String briefDesc;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("registration_link")
    @Expose
    private String registrationLink;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("time")
    @Expose
    private String time;
    @SerializedName("town")
    @Expose
    private String town;
    @SerializedName("full_address")
    @Expose
    private String fullAddress;

    public Object getName() {
        return name;
    }

    public void setName(Object name) {
        this.name = name;
    }

    public String getBriefDesc() {
        return briefDesc;
    }

    public void setBriefDesc(String briefDesc) {
        this.briefDesc = briefDesc;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getRegistrationLink() {
        return registrationLink;
    }

    public void setRegistrationLink(String registrationLink) {
        this.registrationLink = registrationLink;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getFullAddress() {
        return fullAddress;
    }

    public void setFullAddress(String fullAddress) {
        this.fullAddress = fullAddress;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
