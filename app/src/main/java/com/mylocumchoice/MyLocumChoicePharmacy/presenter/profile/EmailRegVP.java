package com.mylocumchoice.MyLocumChoicePharmacy.presenter.profile;

import android.content.Context;

import com.google.gson.JsonObject;
import com.mylocumchoice.MyLocumChoicePharmacy.model.profile.ContactUsRes;
import com.mylocumchoice.MyLocumChoicePharmacy.model.profile.EmailRegRes;
import com.mylocumchoice.MyLocumChoicePharmacy.view.base.BaseView;

import retrofit2.Response;

public interface EmailRegVP {

    public interface Presenter {
        void getEmailReg(Context context, JsonObject jsonObject);
    }


    public interface View extends BaseView {
        void onEmailRegResponse(Context context, Response<EmailRegRes> response);
    }
}
