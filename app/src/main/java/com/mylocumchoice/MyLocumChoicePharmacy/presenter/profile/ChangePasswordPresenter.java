package com.mylocumchoice.MyLocumChoicePharmacy.presenter.profile;

import android.app.Activity;
import android.content.Context;

import com.google.gson.JsonObject;
import com.mylocumchoice.MyLocumChoicePharmacy.R;
import com.mylocumchoice.MyLocumChoicePharmacy.model.profile.ChangePwdRes;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.SharedPrefManager;
import com.mylocumchoice.MyLocumChoicePharmacy.view.base.BaseApplication;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePasswordPresenter implements ChangePasswordVP.Presenter{

    private ChangePasswordVP.View mView;
    private Activity activity;

    public ChangePasswordPresenter(ChangePasswordVP.View view, Activity activity) {
        this.mView = view;
        this.activity = activity;
    }

    @Override
    public void updateAPI(Context context, JsonObject jsonObject) {
        BaseApplication.getRestClient().getService().changePassword(
                SharedPrefManager.getInstance(context).getEmail()
                ,SharedPrefManager.getInstance(context).getAuthToken()
                ,jsonObject).enqueue(new Callback<ChangePwdRes>() {
            @Override
            public void onResponse(Call<ChangePwdRes> call, Response<ChangePwdRes> response) {
                try{
                    mView.onSuccess(response);
                }catch (Exception e){
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<ChangePwdRes> call, Throwable t) {
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
