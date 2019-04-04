package com.mylocumchoice.MyLocumChoicePharmacy.presenter.profile;

import android.app.Activity;
import android.content.Context;

import com.mylocumchoice.MyLocumChoicePharmacy.R;
import com.mylocumchoice.MyLocumChoicePharmacy.model.profile.EmailReferRes;
import com.mylocumchoice.MyLocumChoicePharmacy.model.profile.ReferenceRes;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.SharedPrefManager;
import com.mylocumchoice.MyLocumChoicePharmacy.view.base.BaseApplication;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReferencesPresenter implements ReferencesVP.Presenter {
    ReferencesVP.View mView;
    Activity activity;

    public ReferencesPresenter(ReferencesVP.View view, Activity activity) {
        this.mView = view;
        this.activity = activity;
    }


    @Override
    public void emailRefer(Context context) {
        mView.showProgress();
        BaseApplication.getRestClient().getService().emailReference(
                SharedPrefManager.getInstance(context).getEmail()
                ,SharedPrefManager.getInstance(context).getAuthToken()).enqueue(new Callback<EmailReferRes>() {
            @Override
            public void onResponse(Call<EmailReferRes> call, Response<EmailReferRes> response) {
                try{
                    mView.onEmailSuccess(response);
                    mView.hideProgress();
                }catch (Exception e){
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<EmailReferRes> call, Throwable t) {
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
    public void addReferences(Context context) {
        mView.showProgress();
        BaseApplication.getRestClient().getService().getLocumReference(
                SharedPrefManager.getInstance(context).getEmail()
                ,SharedPrefManager.getInstance(context).getAuthToken()).enqueue(new Callback<ReferenceRes>() {
            @Override
            public void onResponse(Call<ReferenceRes> call, Response<ReferenceRes> response) {
                try{
                    mView.addReferSuccess(response);
                    mView.hideProgress();
                }catch (Exception e){

                }

            }

            @Override
            public void onFailure(Call<ReferenceRes> call, Throwable t) {
                try {
                    mView.hideProgress();
                    mView.showWarning(activity, "",activity.getResources().getString(R.string.errorTimeOut), "error");
                }catch (Exception e){

                }


            }
        });
    }

    @Override
    public void addReferencesNotification(Context context, int notification_id) {
        mView.showProgress();
        BaseApplication.getRestClient().getService().getLocumReferenceNotification(SharedPrefManager.getInstance(context).getEmail()
                ,SharedPrefManager.getInstance(context).getAuthToken(),notification_id).enqueue(new Callback<ReferenceRes>() {
            @Override
            public void onResponse(Call<ReferenceRes> call, Response<ReferenceRes> response) {
                try{
                    mView.addReferSuccess(response);
                    mView.hideProgress();
                }catch (Exception e){

                }
            }

            @Override
            public void onFailure(Call<ReferenceRes> call, Throwable t) {
                try {
                    mView.hideProgress();
                    mView.showWarning(activity, "",activity.getResources().getString(R.string.errorTimeOut), "error");
                }catch (Exception e){

                }
            }
        });
    }
}
