package com.mylocumchoice.MyLocumChoicePharmacy.presenter.shifts;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.mylocumchoice.MyLocumChoicePharmacy.R;
import com.mylocumchoice.MyLocumChoicePharmacy.model.shifts.ShiftAcceptResponse;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.SharedPrefManager;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.SignOutAlert;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.Utils;
import com.mylocumchoice.MyLocumChoicePharmacy.view.base.BaseApplication;
import com.mylocumchoice.MyLocumChoicePharmacy.view.shifts.activities.LandingActivity;
import com.mylocumchoice.MyLocumChoicePharmacy.view.shifts.activities.ShiftDetailActivity;
import com.mylocumchoice.MyLocumChoicePharmacy.view.shifts.viewinterface.ShiftAcceptView;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShiftAcceptPresenterImpl implements ShiftAcceptPresenter{

    ShiftAcceptView mView;
    Activity mContext;
    Utils mUtils;
    private SignOutAlert signOutAlert;
    public ShiftAcceptPresenterImpl(ShiftAcceptView mView, Activity mContext) {
        this.mContext=mContext;
        this.mView=mView;
    }

    @Override
    public void onShiftAccept(int id) {
        mView.showProgress();
        BaseApplication.getRestClient().getService().acceptShift(SharedPrefManager.getInstance(mContext).getEmail()
                ,SharedPrefManager.getInstance(mContext).getAuthToken(),id).enqueue(new Callback<ShiftAcceptResponse>() {
            @Override
            public void onResponse(Call<ShiftAcceptResponse> call, Response<ShiftAcceptResponse> response) {
                try{
                    mView.hideProgress();
                    if(response.code()==201) {
                        mView.onShiftAccepted(response.body());
                    }else if (response.code() == 422) {
                        try {
                            String errorString;
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            errorString = jObjError.get("error").toString();
                            mView.showWarning(mContext, "", errorString, "error");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }else if(response.code()==401){
                        try{
                            mUtils = new Utils();
                            mUtils.showDialog(mContext,"", mContext.getResources().getString(R.string.text_sign_out));
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                    else {
                        mView.showWarning(mContext, "", mContext.getResources().getString(R.string.errorTimeOut), "error");

                    }
                }catch (Exception e){
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<ShiftAcceptResponse> call, Throwable t) {
                try{
                    mView.hideProgress();
                    mView.onAcceptError();
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        });
    }

    @Override
    public void onShiftApply(int id) {
        mView.showProgress();
        BaseApplication.getRestClient().getService().applyShift(SharedPrefManager.getInstance(mContext).getEmail()
                ,SharedPrefManager.getInstance(mContext).getAuthToken(),id).enqueue(new Callback<ShiftAcceptResponse>() {
            @Override
            public void onResponse(Call<ShiftAcceptResponse> call, Response<ShiftAcceptResponse> response) {
                try {
                    mView.hideProgress();
                    if(response.code() == 201) {
                        mView.onShiftApplied(response.body());
                    }else if (response.code() == 422) {
                        try {
                            String errorString;
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            errorString = jObjError.get("error").toString();
                            mView.showWarning(mContext, "", errorString, "error");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }else if(response.code() == 401){
                        try{
                            mUtils = new Utils();
                            mUtils.showDialog(mContext,"", mContext.getResources().getString(R.string.text_sign_out));
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                    else {
                        mView.showWarning(mContext, "", mContext.getResources().getString(R.string.errorTimeOut), "error");
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<ShiftAcceptResponse> call, Throwable t) {
                try{
                    mView.hideProgress();
                    mView.onApplyError();
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        });
    }

    @Override
    public void onBookingCancel(int id) {
        mView.showProgress();
        BaseApplication.getRestClient().getService().cancelBooking(SharedPrefManager.getInstance(mContext).getEmail()
                ,SharedPrefManager.getInstance(mContext).getAuthToken(),id).enqueue(new Callback<ShiftAcceptResponse>() {
            @Override
            public void onResponse(Call<ShiftAcceptResponse> call, Response<ShiftAcceptResponse> response) {
                try{
                    mView.hideProgress();
                    if(response.code() == 200) {
                        mView.onShiftCanceled(response.body());
                    }else if (response.code() == 422) {
                        try {
                            String errorString;
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            errorString = jObjError.get("error").toString();
                            mView.showWarning(mContext, "", errorString, "error");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else if (response.code() == 401) {
                        try{
                            mUtils = new Utils();
                            mUtils.showDialog(mContext,"", mContext.getResources().getString(R.string.text_sign_out));
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }else{
                        mView.showWarning(mContext, "", mContext.getResources().getString(R.string.errorTimeOut), "error");
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<ShiftAcceptResponse> call, Throwable t) {
                try{
                    mView.hideProgress();
                    mView.onCancelError();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }


}
