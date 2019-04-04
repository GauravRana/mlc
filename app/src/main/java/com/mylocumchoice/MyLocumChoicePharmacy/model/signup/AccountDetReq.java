package com.mylocumchoice.MyLocumChoicePharmacy.model.signup;

import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;

public class AccountDetReq {

    public JsonObject add(String name
                    ,String gphc_no
                    ,String email
                    ,String password
                    ,String mobile
                    /*,String gphc_enquiry*/
                    ,String device_token) {
        JsonObject request = new JsonObject();
        JsonObject user = new JsonObject();
        try {
            user.addProperty("name", name);
            user.addProperty("gphc_no", gphc_no);
            user.addProperty("email", email);
            user.addProperty("password", password);
            user.addProperty("mobile", mobile);
            //user.addProperty("gphc_expiry_date", gphc_enquiry);
            user.addProperty("fcm_token", device_token);
            request.add("locum", user);
        } catch (JsonIOException e) {
            e.printStackTrace();
        }
        return request;
    }
}
