package com.mylocumchoice.MyLocumChoicePharmacy.presenter.profile;

import android.content.Context;

import com.google.gson.JsonObject;
import com.mylocumchoice.MyLocumChoicePharmacy.view.base.BaseView;

import retrofit2.Response;

public interface DeactivateVP {

    public interface Presenter {
        void deactivateAccount(Context context, JsonObject jsonObject);
    }


    public interface View extends BaseView {
        void onDeactivate(Response<Void> response);
    }

}
