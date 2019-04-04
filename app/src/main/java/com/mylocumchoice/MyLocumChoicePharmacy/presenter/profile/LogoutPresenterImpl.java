package com.mylocumchoice.MyLocumChoicePharmacy.presenter.profile;

import android.content.Context;

import com.google.gson.JsonObject;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.SharedPrefManager;
import com.mylocumchoice.MyLocumChoicePharmacy.view.base.BaseApplication;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LogoutPresenterImpl implements LogoutVP.LogoutPresenter {


    LogoutVP.LogoutView mView;
    Context mContext;

    public LogoutPresenterImpl(LogoutVP.LogoutView mView,Context mContext) {
        this.mView=mView;
        this.mContext=mContext;
    }

    @Override
    public void onLogout(JsonObject jsonObject) {
        mView.showProgress();
        BaseApplication.getRestClient().getService().logout(SharedPrefManager.getInstance(mContext).getEmail()
                ,SharedPrefManager.getInstance(mContext).getAuthToken(),jsonObject).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                try{
                    mView.hideProgress();
                    mView.onLogoutSuccess(response);
                }catch (Exception e){
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                try{
                    mView.hideProgress();
                    mView.onLogoutFailure();
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        });
    }
}
