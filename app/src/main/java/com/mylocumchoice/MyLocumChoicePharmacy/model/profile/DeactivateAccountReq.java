package com.mylocumchoice.MyLocumChoicePharmacy.model.profile;

import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;

public class DeactivateAccountReq {
    public JsonObject add(String pwd
            , String reason) {
        JsonObject request = new JsonObject();
        JsonObject user = new JsonObject();
        try {
            user.addProperty("password", pwd);
            user.addProperty("deactivation_reason", reason);
            request.add("locum", user);
        } catch (JsonIOException e) {
            e.printStackTrace();
        }
        return request;
    }
}
