package com.mylocumchoice.MyLocumChoicePharmacy.presenter.profile;

import android.app.Activity;
import android.content.Context;

import com.mylocumchoice.MyLocumChoicePharmacy.R;
import com.mylocumchoice.MyLocumChoicePharmacy.view.base.BaseApplication;
import com.mylocumchoice.MyLocumChoicePharmacy.model.profile.PharmaSysRes;
import com.mylocumchoice.MyLocumChoicePharmacy.model.profile.PharmacySysUpdateReq;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.SharedPrefManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PharmacySysPresenter implements PharmacySysVP.Presenter {

    PharmacySysVP.View mView;
    Activity activity;

    public PharmacySysPresenter(PharmacySysVP.View view, Activity activity) {
        this.mView = view;
        this.activity = activity;
    }

    @Override
    public void callAPI(Context context) {
        mView.showProgress();
        BaseApplication.getRestClient().getService().getPharmaSys(SharedPrefManager.getInstance(context).getEmail(),SharedPrefManager.getInstance(context).getAuthToken()).enqueue(new Callback<PharmaSysRes>() {
            @Override
            public void onResponse(Call<PharmaSysRes> call, Response<PharmaSysRes> response) {
                try{
                    mView.hideProgress();
                    mView.onSuccess(response);
                }catch (Exception e){

                }

            }

            @Override
            public void onFailure(Call<PharmaSysRes> call, Throwable t) {
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
    public void updateAPI(String email, String token, PharmacySysUpdateReq request) {
        mView.showProgress();
        BaseApplication.getRestClient().getService().updatePharmaSys(
                 email
                ,token
                , request).enqueue(new Callback<PharmaSysRes>() {
            @Override
            public void onResponse(Call<PharmaSysRes> call, Response<PharmaSysRes> response) {
                try{
                    mView.hideProgress();
                    mView.onSuccessUpdate(response);
                }catch (Exception e){
                    e.printStackTrace();
                }


            }
            @Override
            public void onFailure(Call<PharmaSysRes> call, Throwable t) {
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
