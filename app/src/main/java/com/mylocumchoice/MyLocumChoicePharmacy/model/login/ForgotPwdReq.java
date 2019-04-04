package com.mylocumchoice.MyLocumChoicePharmacy.model.login;

import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;

public class ForgotPwdReq {
    public JsonObject add(String email) {
        JsonObject request = new JsonObject();
        JsonObject user = new JsonObject();
        try {
            user.addProperty("email", email);
            request.add("locum", user);
        } catch (JsonIOException e) {
            e.printStackTrace();
        }
        return request;
    }

}
