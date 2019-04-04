package com.mylocumchoice.MyLocumChoicePharmacy.presenter.profile;

import android.content.Context;

import com.mylocumchoice.MyLocumChoicePharmacy.model.profile.EmailReferRes;
import com.mylocumchoice.MyLocumChoicePharmacy.model.profile.ReferenceRes;
import com.mylocumchoice.MyLocumChoicePharmacy.view.base.BaseView;

import retrofit2.Response;

public interface ReferencesVP {

    public interface Presenter {
        void emailRefer(Context context);
        void addReferences(Context context);
        void addReferencesNotification(Context context,int notification_id);
    }


    public interface View extends BaseView {
        void onEmailSuccess(Response<EmailReferRes> response);
        void addReferSuccess(Response<ReferenceRes> response);
    }
}
