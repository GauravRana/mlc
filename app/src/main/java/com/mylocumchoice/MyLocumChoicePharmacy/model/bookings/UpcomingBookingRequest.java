package com.mylocumchoice.MyLocumChoicePharmacy.model.bookings;

import com.google.gson.JsonObject;

public class UpcomingBookingRequest {
    public JsonObject add(int page,Boolean sort){
        JsonObject request = new JsonObject();
        request.addProperty("next_page",page);
        if (sort == null) {
            request.addProperty("sort_by","");
        } else if(sort){
            request.addProperty("sort_by","date_asc");
        }else if(!sort){
            request.addProperty("sort_by","date_desc");
        }
        return request;
    }
}
