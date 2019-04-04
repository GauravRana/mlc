package com.mylocumchoice.MyLocumChoicePharmacy.presenter.signup;

import com.mylocumchoice.MyLocumChoicePharmacy.model.signup.RegisterReq;
import com.mylocumchoice.MyLocumChoicePharmacy.model.signup.RegisterRes;
import com.mylocumchoice.MyLocumChoicePharmacy.view.base.BaseView;

import retrofit2.Response;

public interface SignupVP {

    public interface Presenter {
        void onSignupRequest(RegisterReq request);
    }

    public interface View extends BaseView {
        void onSignupSuccess(Response<RegisterRes> response);
    }
}
