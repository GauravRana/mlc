package com.mylocumchoice.MyLocumChoicePharmacy.model.profile;

import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;

public class ChangePwdReq {
    public JsonObject add(String current_pwd
            , String pwd
            , String confim_pwd) {
        JsonObject request = new JsonObject();
        JsonObject user = new JsonObject();
        try {
            user.addProperty("current_password", current_pwd);
            user.addProperty("password", pwd);
            user.addProperty("password_confirmation", confim_pwd);
            request.add("locum", user);
        } catch (JsonIOException e) {
            e.printStackTrace();
        }
        return request;
    }
}
