package com.mylocumchoice.MyLocumChoicePharmacy.presenter.profile;

import android.content.Context;

import com.google.gson.JsonObject;
import com.mylocumchoice.MyLocumChoicePharmacy.model.profile.BasicDetailRes;
import com.mylocumchoice.MyLocumChoicePharmacy.model.profile.ChangePwdReq;
import com.mylocumchoice.MyLocumChoicePharmacy.model.profile.UpdateReq;
import com.mylocumchoice.MyLocumChoicePharmacy.model.profile.UpdateRes;
import com.mylocumchoice.MyLocumChoicePharmacy.view.base.BaseView;

import org.json.JSONObject;

import retrofit2.Response;

public interface BasicDetailsVP {

    public interface Presenter {
        void getBasicDetails(Context context);
        void getBasicDetailsNotification(Context context,int notification_id);
        void updateBasicDetails(Context context, JsonObject request);
    }

    public interface View extends BaseView {
        void getDetailsSuccess(Response<BasicDetailRes> response);
        void updateDetailsResponse(Response<UpdateRes> response);
    }
}
