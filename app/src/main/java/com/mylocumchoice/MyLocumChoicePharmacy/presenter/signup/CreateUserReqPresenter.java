package com.mylocumchoice.MyLocumChoicePharmacy.presenter.signup;

import android.app.Activity;

import com.google.gson.JsonObject;
import com.mylocumchoice.MyLocumChoicePharmacy.R;
import com.mylocumchoice.MyLocumChoicePharmacy.model.signup.AccountDetRes;
import com.mylocumchoice.MyLocumChoicePharmacy.view.base.BaseApplication;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateUserReqPresenter implements CreateUserVP.Presenter{

    private CreateUserVP.View mView;
    private Activity activity;

    public CreateUserReqPresenter(CreateUserVP.View view, Activity activity) {
        this.mView = view;
        this.activity=activity;
    }

    @Override
    public void onCreateUserReq(JsonObject accountDetReq) {
        mView.showProgress();
        BaseApplication.getRestClient().getService().createUser(accountDetReq).enqueue(new Callback<AccountDetRes>() {
            @Override
            public void onResponse(Call<AccountDetRes> call, Response<AccountDetRes> response) {
                try {
                    mView.hideProgress();
                    mView.onUserCreated(response);
                }catch (Exception e){

                }
            }

            @Override
            public void onFailure(Call<AccountDetRes> call, Throwable t) {
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
