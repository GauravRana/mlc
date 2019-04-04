package com.mylocumchoice.MyLocumChoicePharmacy.model.shifts;

import com.google.gson.JsonArray;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;


import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class OpenShiftReq {

    public JsonObject add(OpenShift openShift) {
        JsonObject request = new JsonObject();
        JsonObject user=new JsonObject();
        try {
            user.addProperty("start_date",openShift.getStartDate());
            user.addProperty("end_date",openShift.getEndDate());
            user.addProperty("longitude",openShift.getLongitude());
            user.addProperty("latitude",openShift.getLatitude());
            user.addProperty("distance_id",openShift.getDistanceId());
            user.addProperty("emergency",openShift.getEmergency());
            user.addProperty("rate",openShift.getRate());
            user.addProperty("best_match",openShift.getBest_match());

            try {
                    JsonArray mJSONArray = new JsonArray();
                    for (int value : openShift.getCountyIds()) {
                        mJSONArray.add(value);
                    }
                    for (int i = 0; i <= openShift.getCountyIds().size(); i++) {
                        user.add("county_ids", mJSONArray);
                    }
            }catch (NullPointerException ne){
                ne.printStackTrace();
            }

            try{
                JsonArray mJSONArray = new JsonArray();
                for(int value : openShift.getPaceIds())
                {
                    mJSONArray.add(value);
                }
                for (int i = 0; i < openShift.getPaceIds().size(); i++) {
                    user.add("pace_ids", mJSONArray);
                }
            }catch (NullPointerException ne){
            ne.printStackTrace();
        }
            request.addProperty("next_page",openShift.getNextPage());
            request.addProperty("sort_by",openShift.getSortBy());
            request.add("search",user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return request;
    }


}
