package com.mylocumchoice.MyLocumChoicePharmacy.model.bookings;

import com.google.gson.JsonObject;

public class CancelBookingRequest {

    public JsonObject add(int id,String reason){
        JsonObject request = new JsonObject();
        request.addProperty("reason_id",id);
        request.addProperty("reason_input",reason);
        return request;
    }
}
