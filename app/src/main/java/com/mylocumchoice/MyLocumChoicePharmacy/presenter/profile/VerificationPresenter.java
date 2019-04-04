package com.mylocumchoice.MyLocumChoicePharmacy.presenter.profile;

import android.app.Activity;
import android.content.Context;

import com.mylocumchoice.MyLocumChoicePharmacy.R;
import com.mylocumchoice.MyLocumChoicePharmacy.model.profile.AccountVerifyRes;
import com.mylocumchoice.MyLocumChoicePharmacy.model.profile.PrefernceRes;
import com.mylocumchoice.MyLocumChoicePharmacy.model.profile.VerificationRes;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.SharedPrefManager;
import com.mylocumchoice.MyLocumChoicePharmacy.view.base.BaseApplication;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class VerificationPresenter implements VerificationVP.Presenter{
    VerificationVP.View mView;
    Activity activity;

    public VerificationPresenter(VerificationVP.View view, Activity activity) {
        this.mView = view;
        this.activity = activity;
    }

    @Override
    public void getVerification(Context context) {
        mView.showProgress();
        BaseApplication.getRestClient().getService().getVerifySteps(
                SharedPrefManager.getInstance(context).getEmail()
                ,SharedPrefManager.getInstance(context).getAuthToken())
                .enqueue(new Callback<VerificationRes>() {
                    @Override
                    public void onResponse(Call<VerificationRes> call, Response<VerificationRes> response) {
                        try{
                            mView.hideProgress();
                            mView.onVerificationResponse(context,response);
                        }catch (Exception e){
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onFailure(Call<VerificationRes> call, Throwable t) {
                        try{
                            mView.hideProgress();
                            mView.showWarning(activity, "",activity.getResources().getString(R.string.errorTimeOut), "error");
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                });
    }

    @Override
    public void getVerificationNotification(Context context, int notification_id) {
        mView.showProgress();
        BaseApplication.getRestClient().getService().getVerifyStepsNotification(SharedPrefManager.getInstance(context).getEmail()
                ,SharedPrefManager.getInstance(context).getAuthToken(),notification_id).enqueue(new Callback<VerificationRes>() {
            @Override
            public void onResponse(Call<VerificationRes> call, Response<VerificationRes> response) {
                try{
                    mView.hideProgress();
                    mView.onVerificationResponse(context,response);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<VerificationRes> call, Throwable t) {
                try{
                    mView.hideProgress();
                    mView.showWarning(activity, "",activity.getResources().getString(R.string.errorTimeOut), "error");
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }


    @Override
    public void getAccountVerification(Context context) {
        mView.showProgress();
        BaseApplication.getRestClient().getService().getVerifyAccount(
                SharedPrefManager.getInstance(context).getEmail()
                ,SharedPrefManager.getInstance(context).getAuthToken())
                .enqueue(new Callback<AccountVerifyRes>() {
                    @Override
                    public void onResponse(Call<AccountVerifyRes> call, Response<AccountVerifyRes> response) {
                        try{
                            mView.hideProgress();
                            mView.onAccountVerificationResponse(context,response);
                        }catch (Exception e){
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onFailure(Call<AccountVerifyRes> call, Throwable t) {
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
