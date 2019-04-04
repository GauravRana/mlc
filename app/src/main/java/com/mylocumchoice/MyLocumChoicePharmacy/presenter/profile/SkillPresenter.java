package com.mylocumchoice.MyLocumChoicePharmacy.presenter.profile;

import android.app.Activity;
import android.content.Context;

import com.mylocumchoice.MyLocumChoicePharmacy.R;
import com.mylocumchoice.MyLocumChoicePharmacy.model.profile.PreferenceReq;
import com.mylocumchoice.MyLocumChoicePharmacy.model.profile.PrefernceRes;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.SharedPrefManager;
import com.mylocumchoice.MyLocumChoicePharmacy.view.base.BaseApplication;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SkillPresenter implements SkillVP.Presenter{
    SkillVP.View mView;
    Activity activity;

    public SkillPresenter(SkillVP.View view, Activity activity) {
        this.mView = view;
        this.activity = activity;
    }

    @Override
    public void getSkills(Context context) {
        mView.showProgress();
        BaseApplication.getRestClient().getService().getSkills(
                SharedPrefManager.getInstance(context).getEmail()
                ,SharedPrefManager.getInstance(context).getAuthToken())
                .enqueue(new Callback<PrefernceRes>() {
                    @Override
                    public void onResponse(Call<PrefernceRes> call, Response<PrefernceRes> response) {
                        try{
                            mView.hideProgress();
                            mView.onGetSkills(context,response);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<PrefernceRes> call, Throwable t) {
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
    public void getSkillsNotification(Context context, int notification_id) {
        mView.showProgress();
        BaseApplication.getRestClient().getService().getSkillsNotification(SharedPrefManager.getInstance(context).getEmail()
                ,SharedPrefManager.getInstance(context).getAuthToken(),notification_id).enqueue(new Callback<PrefernceRes>() {
            @Override
            public void onResponse(Call<PrefernceRes> call, Response<PrefernceRes> response) {
                try{
                    mView.hideProgress();
                    mView.onGetSkills(context,response);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<PrefernceRes> call, Throwable t) {
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
