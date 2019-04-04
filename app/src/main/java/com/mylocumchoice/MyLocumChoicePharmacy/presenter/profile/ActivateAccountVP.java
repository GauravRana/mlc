package com.mylocumchoice.MyLocumChoicePharmacy.presenter.profile;

import android.content.Context;

import com.google.gson.JsonObject;
import com.mylocumchoice.MyLocumChoicePharmacy.view.base.BaseView;

import retrofit2.Response;

public interface ActivateAccountVP {

    public interface View extends BaseView {
        void onActivate(Response<Void> response);
    }

    public interface Presenter {
        void activateAccount(Context context);
    }
}
