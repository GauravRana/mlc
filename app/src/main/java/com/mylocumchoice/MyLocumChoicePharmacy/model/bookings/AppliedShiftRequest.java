package com.mylocumchoice.MyLocumChoicePharmacy.model.bookings;

import com.google.gson.JsonObject;

public class AppliedShiftRequest {
    public JsonObject add(int page){
        JsonObject request = new JsonObject();
        request.addProperty("next_page",page);
        return request;
    }
}
