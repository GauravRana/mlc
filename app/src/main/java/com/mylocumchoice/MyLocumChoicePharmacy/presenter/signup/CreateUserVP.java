package com.mylocumchoice.MyLocumChoicePharmacy.presenter.signup;

import com.google.gson.JsonObject;
import com.mylocumchoice.MyLocumChoicePharmacy.model.signup.AccountDetReq;
import com.mylocumchoice.MyLocumChoicePharmacy.model.signup.AccountDetRes;
import com.mylocumchoice.MyLocumChoicePharmacy.view.base.BaseView;

import retrofit2.Response;

public interface CreateUserVP {

    public interface Presenter {
        void onCreateUserReq(JsonObject accountDetReq);
    }


    public interface View extends BaseView {
        void onUserCreated(Response<AccountDetRes> accountDetRes);
    }
}
