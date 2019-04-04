package com.mylocumchoice.MyLocumChoicePharmacy.presenter.login;

import com.google.gson.JsonObject;
import com.mylocumchoice.MyLocumChoicePharmacy.model.login.SignInRes;
import com.mylocumchoice.MyLocumChoicePharmacy.view.base.BaseApplication;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginPresenter implements LoginVP.Presenter{

    private LoginVP.View mView;
    public LoginPresenter(LoginVP.View mView) {
        this.mView=mView;
    }

    @Override
    public void onUserLoggedIn(JsonObject jsonObject) {
        mView.showProgress();
        BaseApplication.getRestClient().getService().signIn(jsonObject).enqueue(new Callback<SignInRes>() {
            @Override
            public void onResponse(Call<SignInRes> call, Response<SignInRes> response) {
                try {
                    mView.hideProgress();
                    mView.onLoginSuccess(response);
                }catch (Exception e){
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(Call<SignInRes> call, Throwable t) {
                try{
                    mView.hideProgress();
                    mView.onLoginfailed();
                }catch (Exception e){

                }

            }
        });
    }

}
