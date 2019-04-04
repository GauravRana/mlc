package com.mylocumchoice.MyLocumChoicePharmacy.presenter.profile;

import android.app.Activity;
import android.content.Context;

import com.mylocumchoice.MyLocumChoicePharmacy.R;
import com.mylocumchoice.MyLocumChoicePharmacy.model.profile.EmailReferRes;
import com.mylocumchoice.MyLocumChoicePharmacy.model.profile.PrefernceRes;
import com.mylocumchoice.MyLocumChoicePharmacy.model.profile.RtoWResponse;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.SharedPrefManager;
import com.mylocumchoice.MyLocumChoicePharmacy.view.base.BaseApplication;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Right2WorkPresenter implements Right2WorkVP.Presenter {

    Right2WorkVP.View mView;
    Activity activity;

    public Right2WorkPresenter(Right2WorkVP.View view, Activity activity) {
        this.mView = view;
        this.activity = activity;
    }

    @Override
    public void getRight2Work(Context context) {
        mView.showProgress();
        BaseApplication.getRestClient().getService().getRightTOWORK(SharedPrefManager.getInstance(context).getEmail()
                , SharedPrefManager.getInstance(context).getAuthToken()).enqueue(new Callback<PrefernceRes>() {
            @Override
            public void onResponse(Call<PrefernceRes> call, Response<PrefernceRes> response) {
                try{
                    mView.hideProgress();
                    mView.onGetResponse(context, response);
                }catch (Exception e){
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<PrefernceRes> call, Throwable t) {
                try{
                    mView.hideProgress();
                    mView.showWarning(activity, "", activity.getResources().getString(R.string.errorTimeOut), "error");
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void getRight2WorkNotification(Context context, int notification_id) {
        mView.showProgress();
        BaseApplication.getRestClient().getService().getRightTOWORKNotification(SharedPrefManager.getInstance(context).getEmail()
                , SharedPrefManager.getInstance(context).getAuthToken(),notification_id).enqueue(new Callback<PrefernceRes>() {
            @Override
            public void onResponse(Call<PrefernceRes> call, Response<PrefernceRes> response) {
                try{
                    mView.hideProgress();
                    mView.onGetResponse(context, response);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<PrefernceRes> call, Throwable t) {
                try{
                    mView.hideProgress();
                    mView.showWarning(activity, "", activity.getResources().getString(R.string.errorTimeOut), "error");
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }
}
