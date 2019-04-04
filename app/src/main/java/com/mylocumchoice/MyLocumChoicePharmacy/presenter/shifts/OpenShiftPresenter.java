package com.mylocumchoice.MyLocumChoicePharmacy.presenter.shifts;

import com.google.gson.JsonObject;
import com.mylocumchoice.MyLocumChoicePharmacy.model.shifts.OpenShiftReq;

import org.json.JSONObject;

public interface OpenShiftPresenter {

    void getOpenShiftData(JsonObject openShiftReq,int pageNum);
}
