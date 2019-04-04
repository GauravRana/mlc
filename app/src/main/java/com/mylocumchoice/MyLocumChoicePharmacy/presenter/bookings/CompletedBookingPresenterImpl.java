package com.mylocumchoice.MyLocumChoicePharmacy.presenter.bookings;

import android.app.Activity;

import com.google.gson.JsonObject;
import com.mylocumchoice.MyLocumChoicePharmacy.model.bookings.CompletedBookingResponse;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.SharedPrefManager;
import com.mylocumchoice.MyLocumChoicePharmacy.view.base.BaseApplication;
import com.mylocumchoice.MyLocumChoicePharmacy.view.bookings.viewinterface.CompletedBookingView;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CompletedBookingPresenterImpl implements CompletedBookingPresenter{

    CompletedBookingView completedBookingView;
    Activity mActivity;
    public CompletedBookingPresenterImpl(CompletedBookingView completedBookingView, Activity mActivity) {
        this.completedBookingView=completedBookingView;
        this.mActivity=mActivity;
    }

    @Override
    public void onGetCompletedBookings(JsonObject jsonObject,int pageNum) {
        try {
            completedBookingView.hideProgress();
        }catch (Exception e){
            e.printStackTrace();
        }
        if(pageNum==1) {
            completedBookingView.showProgress();
        }
        BaseApplication.getRestClient().getService().getCompletedBookings(SharedPrefManager.getInstance(mActivity).getEmail()
                ,SharedPrefManager.getInstance(mActivity).getAuthToken(),jsonObject).enqueue(new Callback<CompletedBookingResponse>() {
            @Override
            public void onResponse(Call<CompletedBookingResponse> call, Response<CompletedBookingResponse> response) {
                try {
                    completedBookingView.hideProgress();
                }catch (Exception e){
                    e.printStackTrace();
                }
                if(response.code()==500||response.code()==401){
                    try {
                        String errorString;
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        errorString = jObjError.get("error").toString();
                        completedBookingView.showWarning(mActivity, "", errorString, "error");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }else{
                    completedBookingView.onCompletedBookingSuccess(response.body());
                }
            }

            @Override
            public void onFailure(Call<CompletedBookingResponse> call, Throwable t) {
                try {
                    completedBookingView.hideProgress();
                }catch (Exception e){
                    e.printStackTrace();
                }
                completedBookingView.onCompletedBookingFailure();
            }
        });
    }
}
