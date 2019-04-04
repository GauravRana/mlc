package com.mylocumchoice.MyLocumChoicePharmacy.model.profile;

import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;

public class PolarFieldReq {

    public JsonObject add(int field_id
            , boolean value) {
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
