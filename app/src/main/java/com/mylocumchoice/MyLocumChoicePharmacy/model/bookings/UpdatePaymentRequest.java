package com.mylocumchoice.MyLocumChoicePharmacy.model.bookings;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class UpdatePaymentRequest {

    public JsonObject add(Double unpaid,Double extra,Double other) {
        JsonObject request = new JsonObject();
        JsonObject user=new JsonObject();
        try {
            user.addProperty("unpaid_hours",unpaid);
            user.addProperty("extra_hours",extra);
            user.addProperty("other_payment",other);

            request.add("shift_booking",user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return request;
    }
}
