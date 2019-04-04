package com.mylocumchoice.MyLocumChoicePharmacy.presenter.signup;

import android.app.Activity;

import com.mylocumchoice.MyLocumChoicePharmacy.R;
import com.mylocumchoice.MyLocumChoicePharmacy.view.base.BaseApplication;
import com.mylocumchoice.MyLocumChoicePharmacy.model.signup.RegisterReq;
import com.mylocumchoice.MyLocumChoicePharmacy.model.signup.RegisterRes;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupPresenter implements SignupVP.Presenter{


    private SignupVP.View mView;
    private Activity activity;

    public SignupPresenter(SignupVP.View mView, Activity activity) {
        this.mView=mView;
        this.activity=activity;
    }

    @Override
    public void onSignupRequest(RegisterReq request) {

        mView.showProgress();
        BaseApplication.getRestClient().getService().register(request).enqueue(new Callback<RegisterRes>() {
            @Override
            public void onResponse(Call<RegisterRes> call, Response<RegisterRes> response) {
                try{
                    mView.hideProgress();
                    mView.onSignupSuccess(response);
                }catch (Exception e){
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<RegisterRes> call, Throwable t) {
                try {
                    mView.hideProgress();
                    mView.showWarning(activity, "",activity.getResources().getString(R.string.errorTimeOut), "error");
                }catch (Exception e){

                }


            }
        });
    }
}
