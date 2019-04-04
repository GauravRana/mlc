package com.mylocumchoice.MyLocumChoicePharmacy.model.profile;

import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;

public class EmailRegReq {
    public JsonObject add(String text) {
        JsonObject request = new JsonObject();
        JsonObject user = new JsonObject();
        try {
            user.addProperty("email", text);
        } catch (JsonIOException e) {
            e.printStackTrace();
        }
        return user;
    }
}
