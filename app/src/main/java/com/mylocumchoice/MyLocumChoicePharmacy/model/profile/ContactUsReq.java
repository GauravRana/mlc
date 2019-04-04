package com.mylocumchoice.MyLocumChoicePharmacy.model.profile;

import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;

public class ContactUsReq {
        public JsonObject add(String contact_query) {
            JsonObject request = new JsonObject();
            JsonObject user = new JsonObject();
            try {
                user.addProperty("contact_query", contact_query);
                request.add("locum", user);
            } catch (JsonIOException e) {
                e.printStackTrace();
            }
            return request;
        }
}
