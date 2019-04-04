package com.mylocumchoice.MyLocumChoicePharmacy.presenter.profile;

import android.content.Context;

import com.mylocumchoice.MyLocumChoicePharmacy.model.profile.PharmaSysRes;
import com.mylocumchoice.MyLocumChoicePharmacy.model.profile.PharmacySysUpdateReq;
import com.mylocumchoice.MyLocumChoicePharmacy.view.base.BaseView;

import retrofit2.Response;

public interface PharmacySysVP {

    public interface Presenter {
        void callAPI(Context context);
        void updateAPI(String email, String token, PharmacySysUpdateReq request);
    }


    public interface View extends BaseView
    {
        void onSuccess(Response<PharmaSysRes> response);
        void onSuccessUpdate(Response<PharmaSysRes> response);
    }
}
