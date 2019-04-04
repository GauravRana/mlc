package com.mylocumchoice.MyLocumChoicePharmacy.model.bookings;

import com.google.gson.JsonObject;

public class RatingRequest {

    public JsonObject add(int id,float rating){
        JsonObject request = new JsonObject();
        request.addProperty("id",id);
        request.addProperty("rating",rating);
        return request;
    }
}
