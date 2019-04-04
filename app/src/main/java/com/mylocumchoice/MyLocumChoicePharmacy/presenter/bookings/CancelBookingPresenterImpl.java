package com.mylocumchoice.MyLocumChoicePharmacy.presenter.bookings;

import android.app.Activity;

import com.google.gson.JsonObject;
import com.mylocumchoice.MyLocumChoicePharmacy.model.bookings.CancelBookingResponse;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.SharedPrefManager;
import com.mylocumchoice.MyLocumChoicePharmacy.view.base.BaseApplication;
import com.mylocumchoice.MyLocumChoicePharmacy.view.bookings.viewinterface.CancelBookingView;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CancelBookingPresenterImpl implements CancelBookingPresenter {

    CancelBookingView cancelBookingView;
    Activity mActivity;

    public CancelBookingPresenterImpl(CancelBookingView cancelBookingView,Activity mActivity) {
        this.cancelBookingView=cancelBookingView;
        this.mActivity=mActivity;
    }

    @Override
    public void onBookingCancel(int id, JsonObject jsonObject) {
        cancelBookingView.showProgress();
        BaseApplication.getRestClient().getService().onCancelBooking(SharedPrefManager.getInstance(mActivity).getEmail()
                ,SharedPrefManager.getInstance(mActivity).getAuthToken(),id,jsonObject).enqueue(new Callback<CancelBookingResponse>() {
            @Override
            public void onResponse(Call<CancelBookingResponse> call, Response<CancelBookingResponse> response) {
                cancelBookingView.hideProgress();
                if(response.code()==200) {
                    cancelBookingView.onCancelledSuccess(response.body());
                }else if (response.code() == 422) {
                    try {
                        String errorString;
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        errorString = jObjError.get("error").toString();
                        cancelBookingView.showWarning(mActivity, "", errorString, "error");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }else{
                    cancelBookingView.onCancelledFailed();
                }
            }

            @Override
            public void onFailure(Call<CancelBookingResponse> call, Throwable t) {
                cancelBookingView.hideProgress();
                cancelBookingView.onCancelledFailed();
            }
        });
    }
}
