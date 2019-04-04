package com.mylocumchoice.MyLocumChoicePharmacy.presenter.profile;

import android.content.Context;

import com.mylocumchoice.MyLocumChoicePharmacy.model.profile.ComplianceDetailsRes;
import com.mylocumchoice.MyLocumChoicePharmacy.model.profile.ComplianceEmailRes;
import com.mylocumchoice.MyLocumChoicePharmacy.model.profile.ComplianceRes;
import com.mylocumchoice.MyLocumChoicePharmacy.model.profile.PharmaComplianceVerificationRes;
import com.mylocumchoice.MyLocumChoicePharmacy.view.base.BaseView;

import retrofit2.Response;

public interface PharmaComplianceDetailsVP {

    public interface Presenter {
        void getComplianceDetails(Context context, int id);
        void getComplianceDetailsNotification(Context context, int id,int notification_id);
        void emailCompliance(Context context, int id);
        void postRequestVerify(Context context, int id);

    }


    public interface View extends BaseView {
        void onGetDetailsResponse(Context context, Response<ComplianceDetailsRes> response);
        void onComplianceEmailResponse(Context context, Response<ComplianceEmailRes> response);
        void postRequestVerifyCompliance(Context context, Response<PharmaComplianceVerificationRes> response);
    }
}
