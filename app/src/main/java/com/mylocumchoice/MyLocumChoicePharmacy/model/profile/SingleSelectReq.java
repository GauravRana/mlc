package com.mylocumchoice.MyLocumChoicePharmacy.model.profile;

import com.google.gson.JsonArray;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;

import java.util.ArrayList;

public class SingleSelectReq {
    public JsonObject add(int field_id, int option_id, String value) {
        JsonObject request = new JsonObject();
        JsonObject user = new JsonObject();
        try {
            user.addProperty("field_id", field_id);
                JsonObject jObj = new JsonObject();
                jObj.addProperty("option_id",option_id);
                jObj.addProperty("value",value);
                user.add("selected_option",jObj);
            request.add("response", user);

        } catch (JsonIOException e) {
            e.printStackTrace();
        }
        return request;

    }

}

