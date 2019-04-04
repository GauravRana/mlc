package com.mylocumchoice.MyLocumChoicePharmacy.presenter.profile;

import android.content.Context;

import com.google.gson.JsonObject;
import com.mylocumchoice.MyLocumChoicePharmacy.model.profile.PrefernceRes;
import com.mylocumchoice.MyLocumChoicePharmacy.view.base.BaseView;

import retrofit2.Response;

public interface PreferenceVP {

    public interface Presenter {
        void getPreference(Context context);
        void getPreferenceNotification(Context context,int notification_id);
    }


    public interface View extends BaseView {
        void onGetPreference(Response<PrefernceRes> response);
    }
}
