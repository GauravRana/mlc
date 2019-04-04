package com.mylocumchoice.MyLocumChoicePharmacy.presenter.profile;

import android.content.Context;

import com.mylocumchoice.MyLocumChoicePharmacy.model.profile.PrefernceRes;
import com.mylocumchoice.MyLocumChoicePharmacy.view.base.BaseView;

import okhttp3.MultipartBody;
import retrofit2.Response;

public interface UploadCvVP {

    public interface Presenter {
        void uploadCV(Context context,  MultipartBody.Part doc);
    }

    public interface View extends BaseView {
        void onResponse(Context context, Response<Void> response);
    }
}
