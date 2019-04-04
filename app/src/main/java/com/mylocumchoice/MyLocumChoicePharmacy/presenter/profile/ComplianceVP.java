package com.mylocumchoice.MyLocumChoicePharmacy.presenter.profile;

import android.content.Context;

import com.mylocumchoice.MyLocumChoicePharmacy.model.profile.ComplianceRes;
import com.mylocumchoice.MyLocumChoicePharmacy.model.profile.PrefernceRes;
import com.mylocumchoice.MyLocumChoicePharmacy.view.base.BaseView;

import retrofit2.Response;

public interface ComplianceVP {

    public interface Presenter {
        void getCompliance(Context context);
    }


    public interface View extends BaseView {
        void onGetResponse(Context context, Response<ComplianceRes> response);
    }
}
