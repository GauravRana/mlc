package com.mylocumchoice.MyLocumChoicePharmacy.model.calender;

import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;

public class DeleteCalEventReq {
    public JsonObject add(String date) {
        JsonObject request = new JsonObject();
        JsonObject user = new JsonObject();
        try {
            user.addProperty("date", date);
            request.add("calendar_event", user);
        } catch (JsonIOException e) {
            e.printStackTrace();
        }
        return request;
    }
}
