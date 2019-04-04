package com.mylocumchoice.MyLocumChoicePharmacy.presenter.profile;

import android.content.Context;

import com.google.gson.JsonObject;
import com.mylocumchoice.MyLocumChoicePharmacy.view.base.BaseView;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Response;

public interface AddReferenceVP {

    public interface Presenter {
        void addReference(Context context
                , RequestBody rName
                , RequestBody rTextEmail
                , RequestBody rJob
                , RequestBody rCompany
                , MultipartBody.Part doc);

        void addReferenceId(Context context
                , String referId
                , RequestBody rName
                , RequestBody rTextEmail
                , RequestBody rJob
                , RequestBody rCompany
                , MultipartBody.Part doc);

        void addReferenceWithoutDoc(Context context
                , String referId
                , RequestBody rName
                , RequestBody rTextEmail
                , RequestBody rJob
                , RequestBody rCompany);
    }



    public interface View extends BaseView {
        void addReferenceSuccess(Response<Void> response);
        void addReferenceIDSuccess(Response<Void> response);
        void addReferenceWithoutDocSuccess(Response<Void> response);
    }

}
