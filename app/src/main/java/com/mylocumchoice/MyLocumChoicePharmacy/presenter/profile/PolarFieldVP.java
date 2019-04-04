package com.mylocumchoice.MyLocumChoicePharmacy.presenter.profile;

import android.content.Context;

import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.mylocumchoice.MyLocumChoicePharmacy.model.profile.PrefernceRes;
import com.mylocumchoice.MyLocumChoicePharmacy.view.base.BaseView;

import org.json.JSONObject;

import retrofit2.Response;

public interface PolarFieldVP {

    public interface Presenter {
        void postPolar(Context context, JsonObject jsonObject);
        void postMutiSelect(Context context, JsonObject jsonObject);
        void postString(Context context, JsonObject jsonObject);
    }


    public interface View extends BaseView {
        void onFieldResponse(Context context, Response<Void> response);
    }
}
