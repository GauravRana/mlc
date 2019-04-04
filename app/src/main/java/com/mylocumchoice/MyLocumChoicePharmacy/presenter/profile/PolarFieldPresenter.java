package com.mylocumchoice.MyLocumChoicePharmacy.presenter.profile;

import android.app.Activity;
import android.content.Context;

import com.google.gson.JsonObject;
import com.mylocumchoice.MyLocumChoicePharmacy.R;
import com.mylocumchoice.MyLocumChoicePharmacy.model.profile.PrefernceRes;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.SharedPrefManager;
import com.mylocumchoice.MyLocumChoicePharmacy.view.base.BaseApplication;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PolarFieldPresenter implements PolarFieldVP.Presenter {


    PolarFieldVP.View mView;
    Activity activity;

    public PolarFieldPresenter(PolarFieldVP.View view) {
        this.mView = view;
    }

    @Override
    public void postPolar(Context context, JsonObject jsonObject) {
        mView.showProgress();
        BaseApplication.getRestClient().getService().putToResponse(
                SharedPrefManager.getInstance(context).getEmail()
                ,SharedPrefManager.getInstance(context).getAuthToken()
                ,jsonObject)
                .enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        try{
                            mView.hideProgress();
                            mView.onFieldResponse(context,response);
                        }catch (Exception e){
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
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
    public void postMutiSelect(Context context, JsonObject jsonObject) {
        mView.showProgress();
        BaseApplication.getRestClient().getService().putToResponseOther(
                SharedPrefManager.getInstance(context).getEmail()
                ,SharedPrefManager.getInstance(context).getAuthToken()
                ,jsonObject)
                .enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        try{
                            mView.hideProgress();
                            mView.onFieldResponse(context,response);
                        }catch (Exception e){

                        }

                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
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
    public void postString(Context context, JsonObject jsonObject) {
        mView.showProgress();
        BaseApplication.getRestClient().getService().putToResponseOther(
                SharedPrefManager.getInstance(context).getEmail()
                ,SharedPrefManager.getInstance(context).getAuthToken()
                ,jsonObject)
                .enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        /*if(response.code()==204){

                        }else{
                            handleAPIErrors(this, response);
                        }*/
                        try{
                            mView.hideProgress();
                            mView.onFieldResponse(context,response);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        try {
                            mView.hideProgress();
                            mView.showWarning(activity, "",activity.getResources().getString(R.string.errorTimeOut), "error");
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                });
    }


}
