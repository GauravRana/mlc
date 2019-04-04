package com.mylocumchoice.MyLocumChoicePharmacy.presenter.profile;

import android.content.Context;

import com.mylocumchoice.MyLocumChoicePharmacy.model.profile.ChangePwdRes;
import com.mylocumchoice.MyLocumChoicePharmacy.model.profile.ProfileRes;
import com.mylocumchoice.MyLocumChoicePharmacy.view.base.BaseView;

import okhttp3.MultipartBody;
import retrofit2.Response;
import retrofit2.http.Multipart;

public interface ProfileVP {

    public interface Presenter {
        void getAccount(Context contex);
        void getAccountNotification(Context contex,int notification_id);
        void uploadImage(Context context, MultipartBody.Part part);
        void activate(Context context);
    }

    public interface View extends BaseView{
        void getProfileSuccess(Response<ProfileRes> response);
        void onSuccessUpload(Response<Void> response);
        void activateSuccess(Response<Void> response);
    }
}
