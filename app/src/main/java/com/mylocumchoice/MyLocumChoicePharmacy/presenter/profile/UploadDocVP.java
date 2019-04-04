package com.mylocumchoice.MyLocumChoicePharmacy.presenter.profile;

import android.content.Context;

import com.mylocumchoice.MyLocumChoicePharmacy.view.base.BaseView;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Response;

public class UploadDocVP {

    public interface Presenter {
        void uploadDoc(Context context, MultipartBody.Part doc, RequestBody field_id);
        void uploadComplianceDoc(Context context, MultipartBody.Part doc, RequestBody field_id);
    }

    public interface View extends BaseView {
        void onUploadResponse(Context context, Response<Void> response);
        void onUploadComplianceResponse(Context context, Response<Void> response);
    }
}
