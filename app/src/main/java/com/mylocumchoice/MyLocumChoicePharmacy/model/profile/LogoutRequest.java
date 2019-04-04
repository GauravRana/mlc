package com.mylocumchoice.MyLocumChoicePharmacy.model.profile;

import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;

public class LogoutRequest {

    public static JsonObject add(String fcm_token){
        JsonObject request = new JsonObject();
        JsonObject user=new JsonObject();
        try {
            user.addProperty("fcm_token", fcm_token);
            request.add("locum", user);
        } catch (JsonIOException e) {
            e.printStackTrace();
        }
        return request;
    }
}
