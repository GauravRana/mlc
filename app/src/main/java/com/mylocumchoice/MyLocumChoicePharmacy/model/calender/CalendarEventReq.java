package com.mylocumchoice.MyLocumChoicePharmacy.model.calender;

import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;

public class CalendarEventReq {

    public JsonObject add(String date
            , String start_time, String end_time, int recurring_type_id) {
        JsonObject request = new JsonObject();
        JsonObject user = new JsonObject();
        try {
            user.addProperty("date", date);
            user.addProperty("start_time", start_time);
            user.addProperty("end_time", end_time);
            user.addProperty("recurring_type_id", recurring_type_id);
            request.add("calendar_event", user);
        } catch (JsonIOException e) {
            e.printStackTrace();
        }
        return request;
    }
}
