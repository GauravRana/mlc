package com.mylocumchoice.MyLocumChoicePharmacy.presenter.shifts;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.google.gson.JsonObject;
import com.mylocumchoice.MyLocumChoicePharmacy.R;
import com.mylocumchoice.MyLocumChoicePharmacy.model.shifts.ShiftAcceptResponse;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.SharedPrefManager;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.Utils;
import com.mylocumchoice.MyLocumChoicePharmacy.view.base.BaseApplication;
import com.mylocumchoice.MyLocumChoicePharmacy.view.shifts.activities.LandingActivity;
import com.mylocumchoice.MyLocumChoicePharmacy.view.shifts.activities.MakeAnOfferActivity;
import com.mylocumchoice.MyLocumChoicePharmacy.view.shifts.viewinterface.ShiftOfferView;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MakeOfferPresenterImpl implements MakeOfferPresenter{

    Utils mUtils;
    ShiftOfferView mView;
    Activity mContext;
    public MakeOfferPresenterImpl(ShiftOfferView mView,Activity mContext) {
        this.mContext=mContext;
        this.mView=mView;
    }

    @Override
    public void onMakeShiftOffer(int id, JsonObject jsonObject) {
        mView.showProgress();
        BaseApplication.getRestClient().getService().makeShiftOffer(SharedPrefManager.getInstance(mContext).getEmail()
                ,SharedPrefManager.getInstance(mContext).getAuthToken(),id,jsonObject).enqueue(new Callback<ShiftAcceptResponse>() {
            @Override
            public void onResponse(Call<ShiftAcceptResponse> call, Response<ShiftAcceptResponse> response) {
                try{
                    mView.hideProgress();
                    if(response.code()==201) {
                        mView.onShiftOfferd(response.body());
                    }
                    else if (response.code() == 422) {
                        try {
                            String errorString;
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            errorString = jObjError.get("error").toString();
                            mView.showWarning(mContext, "", errorString, "error");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    else if(response.code() == 401){
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
                } catch (Exception e){
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<ShiftAcceptResponse> call, Throwable t) {
                try{
                    mView.hideProgress();
                    mView.onOfferError();
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        });
    }
}
