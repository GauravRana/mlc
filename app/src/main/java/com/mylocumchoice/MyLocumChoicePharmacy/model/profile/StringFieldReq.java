package com.mylocumchoice.MyLocumChoicePharmacy.model.profile;

import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;

public class StringFieldReq {
    public JsonObject add(int field_id
            , String value) {
        JsonObject request = new JsonObject();
        JsonObject user = new JsonObject();
        try {
            user.addProperty("field_id", field_id);
            user.addProperty("value", value);
            request.add("response", user);
        } catch (JsonIOException e) {
            e.printStackTrace();
        }
        return request;
    }
}
