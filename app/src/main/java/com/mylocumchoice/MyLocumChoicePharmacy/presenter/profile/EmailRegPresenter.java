package com.mylocumchoice.MyLocumChoicePharmacy.presenter.profile;

import android.app.Activity;
import android.content.Context;

import com.google.gson.JsonObject;
import com.mylocumchoice.MyLocumChoicePharmacy.R;
import com.mylocumchoice.MyLocumChoicePharmacy.model.profile.EmailRegRes;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.SharedPrefManager;
import com.mylocumchoice.MyLocumChoicePharmacy.view.base.BaseApplication;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EmailRegPresenter implements EmailRegVP.Presenter {
    private EmailRegVP.View mView;
    private Activity activity;

    public EmailRegPresenter(EmailRegVP.View view, Activity activity) {
        this.mView = view;
        this.activity = activity;
    }

    @Override
    public void getEmailReg(Context context, JsonObject jsonObject) {
        mView.showProgress();
        BaseApplication.getRestClient().getService().emailReg(
                SharedPrefManager.getInstance(context).getEmail()
                ,SharedPrefManager.getInstance(context).getAuthToken()
                ,jsonObject).enqueue(new Callback<EmailRegRes>() {
            @Override
            public void onResponse(Call<EmailRegRes> call, Response<EmailRegRes> response) {
                try{
                    mView.hideProgress();
                    mView.onEmailRegResponse(context,response);
                }catch (Exception e){
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<EmailRegRes> call, Throwable t) {
                try{
                    mView.hideProgress();
                    mView.showWarning(activity, "",activity.getResources().getString(R.string.errorTimeOut), "error");
                }catch (Exception e){

                }

            }
        });
    }
}
