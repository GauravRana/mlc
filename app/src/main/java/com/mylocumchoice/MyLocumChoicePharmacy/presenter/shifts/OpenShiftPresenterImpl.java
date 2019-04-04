package com.mylocumchoice.MyLocumChoicePharmacy.presenter.shifts;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.google.gson.JsonObject;
import com.mylocumchoice.MyLocumChoicePharmacy.R;
import com.mylocumchoice.MyLocumChoicePharmacy.model.shifts.OpenShiftReq;
import com.mylocumchoice.MyLocumChoicePharmacy.model.shifts.OpenShiftResponse;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.SharedPrefManager;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.SignOutAlert;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.Utils;
import com.mylocumchoice.MyLocumChoicePharmacy.view.base.BaseApplication;
import com.mylocumchoice.MyLocumChoicePharmacy.view.login.activities.LoginActivity;
import com.mylocumchoice.MyLocumChoicePharmacy.view.shifts.viewinterface.OpenShiftView;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OpenShiftPresenterImpl implements OpenShiftPresenter{

    Utils mUtils;
    OpenShiftView mView;
    Activity mContext;
    private SignOutAlert signOutAlert;
    public OpenShiftPresenterImpl(OpenShiftView mView,Activity mContext) {
        this.mView=mView;
        this.mContext=mContext;
    }

    @Override
    public void getOpenShiftData(JsonObject openShiftReq,int pageNum) {

        if(pageNum==1){
            mView.showProgress();
        }
        BaseApplication.getRestClient().getService().openshift(SharedPrefManager.getInstance(mContext).getEmail()
                ,SharedPrefManager.getInstance(mContext).getAuthToken(),openShiftReq).enqueue(new Callback<OpenShiftResponse>() {
            @Override
            public void onResponse(Call<OpenShiftResponse> call, Response<OpenShiftResponse> response) {
                try {
                    mView.hideProgress();
                }catch (Exception e){
                    e.printStackTrace();
                }
                Log.e("response code---",response.code()+"");
                if(response.code() == 200) {
                    mView.setOpenShiftData(response.body());
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
                    try{
                        mView.showWarning(mContext, "", mContext.getResources().getString(R.string.handle_strange_error), "error");
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }

                }

            @Override
            public void onFailure(Call<OpenShiftResponse> call, Throwable t) {
                try {
                    mView.hideProgress();
                }catch (Exception e){
                    e.printStackTrace();
                }
                mView.onResponseFailure();
            }
        });
    }
}
