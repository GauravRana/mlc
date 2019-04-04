package com.mylocumchoice.MyLocumChoicePharmacy.model.bookings;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class CompletedBookingRequest {

    public JsonObject add(CompletedBooking completedBooking) {
        JsonObject request = new JsonObject();
        JsonObject user=new JsonObject();
        try {
            user.addProperty("start_date",completedBooking.getStartDate());
            user.addProperty("end_date",completedBooking.getEndDate());
            user.addProperty("paid",completedBooking.getPaid());

            try {
                JsonArray mJSONArray = new JsonArray();
                for (int value : completedBooking.getClientIds()) {
                    mJSONArray.add(value);
                }
                for (int i = 0; i <= completedBooking.getClientIds().size(); i++) {
                    user.add("client_ids", mJSONArray);
                }
            }catch (NullPointerException ne){
                ne.printStackTrace();
            }

            if(completedBooking.getSortBy()==null||completedBooking.getSortBy().equals("")){
                request.addProperty("sort_by","");
            }else {
                request.addProperty("sort_by", completedBooking.getSortBy());
            }
            request.addProperty("next_page",completedBooking.getNextPage());
            request.add("search",user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return request;
    }
}
