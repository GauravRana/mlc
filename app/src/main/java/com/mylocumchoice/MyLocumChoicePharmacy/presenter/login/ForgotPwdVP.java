package com.mylocumchoice.MyLocumChoicePharmacy.presenter.login;

import com.google.gson.JsonObject;
import com.mylocumchoice.MyLocumChoicePharmacy.model.login.ForgotPwdRes;
import com.mylocumchoice.MyLocumChoicePharmacy.view.base.BaseView;

import retrofit2.Response;

public interface ForgotPwdVP {

    public interface Presenter {
        void onForgetPwd(JsonObject jsonObject);
    }

    public interface View extends BaseView {
        void onForgetPwdSuccess(Response<ForgotPwdRes> response);
        void onForgetPwdfailed();
    }
}
