package com.mylocumchoice.MyLocumChoicePharmacy.model.profile;

import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;

public class AddReferenceReq {
    public JsonObject add(String name
            , String email
            , String job_title
            , String company
            , String doc) {
        JsonObject user = new JsonObject();
        try {
            user.addProperty("locum_reference[name]", name);
            user.addProperty("locum_reference[email]", email);
            user.addProperty("locum_reference[job_title]", job_title);
            user.addProperty("locum_reference[company]", company);
            user.addProperty("locum_reference[document]", doc);
        } catch (JsonIOException e) {
            e.printStackTrace();
        }
        return user;
    }
}
