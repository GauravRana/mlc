package com.mylocumchoice.MyLocumChoicePharmacy.model.bookings;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Booking {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("logo")
    @Expose
    private Object logo;
    @SerializedName("rate")
    @Expose
    private String rate;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("time_range")
    @Expose
    private String timeRange;
    @SerializedName("town")
    @Expose
    private String town;
    @SerializedName("address_longitude")
    @Expose
    private Object addressLongitude;
    @SerializedName("address_latitude")
    @Expose
    private Object addressLatitude;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getLogo() {
        return logo;
    }

    public void setLogo(Object logo) {
        this.logo = logo;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTimeRange() {
        return timeRange;
    }

    public void setTimeRange(String timeRange) {
        this.timeRange = timeRange;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public Object getAddressLongitude() {
        return addressLongitude;
    }

    public void setAddressLongitude(Object addressLongitude) {
        this.addressLongitude = addressLongitude;
    }

    public Object getAddressLatitude() {
        return addressLatitude;
    }

    public void setAddressLatitude(Object addressLatitude) {
        this.addressLatitude = addressLatitude;
    }
}
