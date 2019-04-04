package com.mylocumchoice.MyLocumChoicePharmacy.model.shifts;

import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;

public class MakeOfferRequest {

    public JsonObject add(String rate
            , String mileage_and_perks) {
        JsonObject request = new JsonObject();
        JsonObject user = new JsonObject();
        try {
            user.addProperty("rate", rate);
            user.addProperty("mileage_and_perks", mileage_and_perks);
            request.add("offer", user);
        } catch (JsonIOException e) {
            e.printStackTrace();
        }
        return request;
    }
}
