package com.mylocumchoice.MyLocumChoicePharmacy.presenter.login;

import com.google.gson.JsonObject;
import com.mylocumchoice.MyLocumChoicePharmacy.model.login.ForgotPwdRes;
import com.mylocumchoice.MyLocumChoicePharmacy.view.base.BaseApplication;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPwdPresenter implements ForgotPwdVP.Presenter {

    private ForgotPwdVP.View mView;
    public ForgotPwdPresenter(ForgotPwdVP.View mView) {
        this.mView=mView;
    }

    @Override
    public void onForgetPwd(JsonObject jsonObject) {
        mView.showProgress();
        BaseApplication.getRestClient().getService().forgot(jsonObject).enqueue(new Callback<ForgotPwdRes>() {
            @Override
            public void onResponse(Call<ForgotPwdRes> call, Response<ForgotPwdRes> response) {
                try {
                    mView.hideProgress();
                    mView.onForgetPwdSuccess(response);
                }catch (Exception e){
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<ForgotPwdRes> call, Throwable t) {
                try{
                    mView.hideProgress();
                    mView.onForgetPwdfailed();
                }catch (Exception e){
                   e.printStackTrace();
                }
            }
        });
    }
}
