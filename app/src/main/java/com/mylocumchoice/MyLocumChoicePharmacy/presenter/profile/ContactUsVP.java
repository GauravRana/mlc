package com.mylocumchoice.MyLocumChoicePharmacy.presenter.profile;

import android.content.Context;

import com.google.gson.JsonObject;
import com.mylocumchoice.MyLocumChoicePharmacy.model.profile.ComplianceRes;
import com.mylocumchoice.MyLocumChoicePharmacy.model.profile.ContactUsRes;
import com.mylocumchoice.MyLocumChoicePharmacy.view.base.BaseView;

import retrofit2.Response;

public class ContactUsVP {

    public interface Presenter {
        void getContactUs(Context context, JsonObject jsonObject);
    }


    public interface View extends BaseView {
        void onContactResponse(Context context, Response<ContactUsRes> response);
    }

}
