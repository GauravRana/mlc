package com.mylocumchoice.MyLocumChoicePharmacy.presenter.profile;

import android.content.Context;

import com.mylocumchoice.MyLocumChoicePharmacy.model.profile.EmailReferRes;
import com.mylocumchoice.MyLocumChoicePharmacy.model.profile.PrefernceRes;
import com.mylocumchoice.MyLocumChoicePharmacy.model.profile.ReferenceRes;
import com.mylocumchoice.MyLocumChoicePharmacy.model.profile.RtoWResponse;
import com.mylocumchoice.MyLocumChoicePharmacy.view.base.BaseView;

import retrofit2.Response;

public class Right2WorkVP {

    public interface Presenter {
        void getRight2Work(Context context);
        void getRight2WorkNotification(Context context,int notification_id);
    }


    public interface View extends BaseView {
        void onGetResponse(Context context, Response<PrefernceRes> response);
    }
}
