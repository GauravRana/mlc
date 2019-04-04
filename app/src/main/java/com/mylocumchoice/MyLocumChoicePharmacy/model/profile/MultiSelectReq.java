package com.mylocumchoice.MyLocumChoicePharmacy.model.profile;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MultiSelectReq {
    public JsonObject add(int field_id, ArrayList<Integer> optionList, String otherText, int other_id) {
        JsonObject request = new JsonObject();
        JsonObject user = new JsonObject();
        try {
            user.addProperty("field_id", field_id);
            JsonArray jArray = new JsonArray();
            for(int i = 0; i < optionList.size(); i++){
                JsonObject jObj = new JsonObject();
                jObj.addProperty("option_id",optionList.get(i));
                if(other_id == optionList.get(i)){
                    jObj.addProperty("value",otherText);
                }else{
                    jObj.addProperty("value","");
                }

                jArray.add(jObj);
            }
            user.add("selected_options",jArray);
            request.add("response", user);

        } catch (JsonIOException e) {
            e.printStackTrace();
        }
        return request;

    }

}
