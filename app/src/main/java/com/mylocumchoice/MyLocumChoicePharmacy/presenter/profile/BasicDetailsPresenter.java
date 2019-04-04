package com.mylocumchoice.MyLocumChoicePharmacy.presenter.profile;

import android.app.Activity;
import android.content.Context;

import com.google.gson.JsonObject;
import com.mylocumchoice.MyLocumChoicePharmacy.R;
import com.mylocumchoice.MyLocumChoicePharmacy.model.profile.BasicDetailRes;
import com.mylocumchoice.MyLocumChoicePharmacy.model.profile.UpdateRes;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.SharedPrefManager;
import com.mylocumchoice.MyLocumChoicePharmacy.view.base.BaseApplication;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BasicDetailsPresenter implements BasicDetailsVP.Presenter {

    private BasicDetailsVP.View mView;
    private Activity activity;

    public BasicDetailsPresenter(BasicDetailsVP.View view, Activity activity) {
        this.mView = view;
        this.activity = activity;
    }

    @Override
    public void getBasicDetails(Context context) {
        mView.showProgress();
        BaseApplication.getRestClient().getService().getBasicDetails(
                SharedPrefManager.getInstance(context).getEmail()
                ,SharedPrefManager.getInstance(context).getAuthToken()).enqueue(new Callback<BasicDetailRes>() {
            @Override
            public void onResponse(Call<BasicDetailRes> call, Response<BasicDetailRes> response) {
                try {
                    mView.getDetailsSuccess(response);
                    mView.hideProgress();
                }catch (Exception e){
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<BasicDetailRes> call, Throwable t) {
                try {
                    mView.hideProgress();
                    mView.showWarning(activity, "",activity.getResources().getString(R.string.errorTimeOut), "error");
                }catch (Exception e){

                }
            }
        });
    }

    @Override
    public void getBasicDetailsNotification(Context context, int notification_id) {
        mView.showProgress();
        BaseApplication.getRestClient().getService().getBasicDetailsNotifications(SharedPrefManager.getInstance(context).getEmail()
                ,SharedPrefManager.getInstance(context).getAuthToken(),notification_id).enqueue(new Callback<BasicDetailRes>() {
            @Override
            public void onResponse(Call<BasicDetailRes> call, Response<BasicDetailRes> response) {
                try {
                    mView.getDetailsSuccess(response);
                    mView.hideProgress();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<BasicDetailRes> call, Throwable t) {
                try {
                    mView.hideProgress();
                    mView.showWarning(activity, "",activity.getResources().getString(R.string.errorTimeOut), "error");
                }catch (Exception e){

                }
            }
        });
    }


    @Override
    public void updateBasicDetails(Context context, JsonObject jsonObject) {
        mView.showProgress();
        BaseApplication.getRestClient().getService().updateDetails(
                SharedPrefManager.getInstance(context).getEmail()
                ,SharedPrefManager.getInstance(context).getAuthToken()
                ,jsonObject).enqueue(new Callback<UpdateRes>() {
            @Override
            public void onResponse(Call<UpdateRes> call, Response<UpdateRes> response) {
                try{
                    mView.updateDetailsResponse(response);
                    mView.hideProgress();
                }catch (Exception e){
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(Call<UpdateRes> call, Throwable t) {
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
