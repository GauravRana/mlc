package com.mylocumchoice.MyLocumChoicePharmacy.presenter.profile;

import android.app.Activity;
import android.content.Context;

import com.google.gson.JsonObject;
import com.mylocumchoice.MyLocumChoicePharmacy.R;
import com.mylocumchoice.MyLocumChoicePharmacy.model.profile.ComplianceRes;
import com.mylocumchoice.MyLocumChoicePharmacy.model.profile.ContactUsRes;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.SharedPrefManager;
import com.mylocumchoice.MyLocumChoicePharmacy.view.base.BaseApplication;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContactUsPresenter implements ContactUsVP.Presenter{
    ContactUsVP.View mView;
    Activity activity;

    public ContactUsPresenter(ContactUsVP.View view, Activity activity) {
        this.mView = view;
        this.activity = activity;
    }

    @Override
    public void getContactUs(Context context, JsonObject jsonObject) {
        mView.showProgress();
        BaseApplication.getRestClient().getService().contactUs(
                SharedPrefManager.getInstance(context).getEmail()
                ,SharedPrefManager.getInstance(context).getAuthToken(), jsonObject)
                .enqueue(new Callback<ContactUsRes>() {
                    @Override
                    public void onResponse(Call<ContactUsRes> call, Response<ContactUsRes> response) {
                        try{
                            mView.hideProgress();
                            mView.onContactResponse(context,response);
                        }catch (Exception e){
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onFailure(Call<ContactUsRes> call, Throwable t) {
                        try{
                            mView.hideProgress();
                            mView.showWarning(activity, "",activity.getResources().getString(R.string.errorTimeOut), "error");
                        }catch (Exception e){
                            e.printStackTrace();
                        }

                    }
                });
    }
}
