package com.mylocumchoice.MyLocumChoicePharmacy.model.profile;

import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;

public class UpdateReq {

    public JsonObject add(String gphcNo
            ,String email
            ,String name
            ,String mobile
            ,String address_line_1
            ,String address_line_2
            ,String address_line_3
            ,String county_id
            ,String town
            ,String postcode) {
        JsonObject request = new JsonObject();
        JsonObject user = new JsonObject();
        try {
            user.addProperty("gphc_no", gphcNo);
            user.addProperty("name", name);
            user.addProperty("email", email);
            user.addProperty("mobile", mobile);
            user.addProperty("address_line_1", address_line_1);
            user.addProperty("address_line_2", address_line_2);
            user.addProperty("address_line_3", address_line_3);
            user.addProperty("county_id", county_id);
            user.addProperty("town", town);
            user.addProperty("postcode", postcode);
            request.add("locum", user);
        } catch (JsonIOException e) {
            e.printStackTrace();
        }
        return request;
    }
}
