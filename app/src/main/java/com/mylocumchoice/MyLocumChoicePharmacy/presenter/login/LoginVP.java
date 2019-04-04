package com.mylocumchoice.MyLocumChoicePharmacy.presenter.login;

import com.google.gson.JsonObject;
import com.mylocumchoice.MyLocumChoicePharmacy.model.login.SignInRes;
import com.mylocumchoice.MyLocumChoicePharmacy.view.base.BaseView;

import retrofit2.Response;

public interface LoginVP {

    public interface Presenter {
        void onUserLoggedIn(JsonObject jsonObject);
    }

    public interface View extends BaseView {
        void onLoginSuccess(Response<SignInRes> response);
        void onLoginfailed();

    }

}
