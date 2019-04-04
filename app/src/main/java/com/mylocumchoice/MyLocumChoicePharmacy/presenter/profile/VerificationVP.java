package com.mylocumchoice.MyLocumChoicePharmacy.presenter.profile;

import android.content.Context;

import com.mylocumchoice.MyLocumChoicePharmacy.model.profile.AccountVerifyRes;
import com.mylocumchoice.MyLocumChoicePharmacy.model.profile.PrefernceRes;
import com.mylocumchoice.MyLocumChoicePharmacy.model.profile.VerificationRes;
import com.mylocumchoice.MyLocumChoicePharmacy.view.base.BaseView;

import retrofit2.Response;

public interface VerificationVP {

    public interface Presenter {
        void getVerification(Context context);
        void getVerificationNotification(Context context,int notification_id);
        void getAccountVerification(Context context);
    }


    public interface View extends BaseView {
        void onVerificationResponse(Context context, Response<VerificationRes> response);
        void onAccountVerificationResponse(Context context, Response<AccountVerifyRes> response);
    }
}
