package com.mylocumchoice.MyLocumChoicePharmacy.model.signup;

import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RegisterReq {
   /* public JsonObject add(String gphc_no, String gphc_expiry) {
        JsonObject request = new JsonObject();
        JsonObject user = new JsonObject();
        try {
            user.addProperty("gphc_no", gphc_no);
            user.addProperty("gphc_expiry_date" +
                    "", gphc_expiry);
        } catch (JsonIOException e) {
            e.printStackTrace();
        }
        return user;
    }*/


    @SerializedName("gphc_no")
    @Expose
    private String gphcNo;

    @SerializedName("gphc_expiry_date")
    @Expose
    private String gphc_expiry_date;



    public String getGphcNo() {
        return gphcNo;
    }

    public void setGphcNo(String gphcNo) {
        this.gphcNo = gphcNo;
    }

    public String getGphc_expiry_date() {
        return gphc_expiry_date;
    }

    public void setGphc_expiry_date(String gphc_expiry_date) {
        this.gphc_expiry_date = gphc_expiry_date;
    }
}
