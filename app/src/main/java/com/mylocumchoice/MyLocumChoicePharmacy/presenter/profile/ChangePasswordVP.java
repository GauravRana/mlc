package com.mylocumchoice.MyLocumChoicePharmacy.presenter.profile;

import android.content.Context;

import com.google.gson.JsonObject;
import com.mylocumchoice.MyLocumChoicePharmacy.model.profile.ChangePwdReq;
import com.mylocumchoice.MyLocumChoicePharmacy.model.profile.ChangePwdRes;
import com.mylocumchoice.MyLocumChoicePharmacy.model.profile.PharmacySysUpdateReq;
import com.mylocumchoice.MyLocumChoicePharmacy.view.base.BaseView;

import retrofit2.Response;

public interface ChangePasswordVP {

    public interface Presenter {
        void updateAPI(Context context, JsonObject jsonObject);
    }

    public interface View extends BaseView {
        void onSuccess(Response<ChangePwdRes> response);
    }
}
