package com.mylocumchoice.MyLocumChoicePharmacy.model.login;

import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;

public class SignInReq {

    public JsonObject add(String email
            ,String password, String token) {
        JsonObject request = new JsonObject();
        JsonObject user = new JsonObject();
        try {
            user.addProperty("email", email);
            user.addProperty("password", password);
            user.addProperty("fcm_token", token);
            request.add("locum", user);
        } catch (JsonIOException e) {
            e.printStackTrace();
        }
        return request;
    }

}
