package com.mylocumchoice.MyLocumChoicePharmacy.presenter.profile;

import com.google.gson.JsonObject;
import com.mylocumchoice.MyLocumChoicePharmacy.model.profile.LogoutRequest;
import com.mylocumchoice.MyLocumChoicePharmacy.view.base.BaseView;

import retrofit2.Response;

public interface LogoutVP {

    interface LogoutView extends BaseView{
         void onLogoutSuccess(Response<Void> response);
         void onLogoutFailure();
    }


    interface LogoutPresenter{
        void onLogout(JsonObject jsonObject);
    }
}
