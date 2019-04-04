package com.mylocumchoice.MyLocumChoicePharmacy.model.login;

import java.io.Serializable;

public class SignInRes implements Serializable
{
    private Locum locum;

    public Locum getLocum() { return this.locum; }

    public void setLocum(Locum locum) { this.locum = locum;

}


public class Locum
{
    private int id;

    private boolean guided_tour;

    public int getId() { return this.id; }

    public void setId(int id) { this.id = id; }

    private String authentication_token;

    public String getAuthenticationToken() { return this.authentication_token; }

    public void setAuthenticationToken(String authentication_token) { this.authentication_token = authentication_token; }

    private int status_id;

    public int getStatusId() { return this.status_id; }

    public void setStatusId(int status_id) { this.status_id = status_id; }

    private String email;

    public String getEmail() { return this.email; }

    public void setEmail(String email) { this.email = email; }

    private Object address_text;

    public Object getAddress_text() {
        return address_text;
    }

    public void setAddress_text(Object address_text) {
        this.address_text = address_text;
    }

    private Object address_latitude;

    public Object getAddress_latitude() {
        return address_latitude;
    }

    public void setAddress_latitude(Object address_latitude) {
        this.address_latitude = address_latitude;
    }

    private Object address_longitude;

    public Object getAddress_longitude() {
        return address_longitude;
    }

    public void setAddress_longitude(Object address_longitude) {
        this.address_longitude = address_longitude;
    }

    public boolean isGuided_tour() {
        return guided_tour;
    }

    public void setGuided_tour(boolean guided_tour) {
        this.guided_tour = guided_tour;
    }
}

}
