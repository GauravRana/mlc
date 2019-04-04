package com.mylocumchoice.MyLocumChoicePharmacy.model.shifts;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.Map;

public class ShiftDetailResDeserializer implements JsonDeserializer<ShiftDetailsRes> {
    @Override
    public ShiftDetailsRes deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        if (((JsonObject) json).get("responseMessage") instanceof JsonObject){
            return new Gson().fromJson(json, ShiftDetailResObj.class);
        } else {
            return new Gson().fromJson(json, ShiftDetailResString.class);
        }
    }

}
