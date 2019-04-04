package com.mylocumchoice.MyLocumChoicePharmacy.presenter.profile;

import android.content.Context;

import com.mylocumchoice.MyLocumChoicePharmacy.model.profile.PrefernceRes;
import com.mylocumchoice.MyLocumChoicePharmacy.view.base.BaseView;

import retrofit2.Response;

public interface AccreditationsVP {

    public interface Presenter {
        void getAccrediataion(Context context);
        void getAccrediataionNotification(Context context,int notification_id);
    }


    public interface View extends BaseView {
        void onGetResponse(Context context, Response<PrefernceRes> response);
    }
}
