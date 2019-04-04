package com.mylocumchoice.MyLocumChoicePharmacy.presenter.bookings;

import android.app.Activity;

import com.google.gson.JsonObject;
import com.mylocumchoice.MyLocumChoicePharmacy.model.bookings.UpdatePaymentResponse;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.SharedPrefManager;
import com.mylocumchoice.MyLocumChoicePharmacy.view.base.BaseApplication;
import com.mylocumchoice.MyLocumChoicePharmacy.view.bookings.viewinterface.UpdatePaymentView;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdatePaymentPresenterImpl implements UpdatePaymentPresenter {

    UpdatePaymentView updatePaymentView;
    Activity mActivity;
    public UpdatePaymentPresenterImpl(UpdatePaymentView updatePaymentView,Activity mActivity) {
        this.mActivity=mActivity;
        this.updatePaymentView=updatePaymentView;
    }

    @Override
    public void onUpdatePayment(int id,JsonObject jsonObject) {
        updatePaymentView.showProgress();
        BaseApplication.getRestClient().getService().updateBookingPayment(SharedPrefManager.getInstance(mActivity).getEmail()
                ,SharedPrefManager.getInstance(mActivity).getAuthToken(),id,jsonObject).enqueue(new Callback<UpdatePaymentResponse>() {
            @Override
            public void onResponse(Call<UpdatePaymentResponse> call, Response<UpdatePaymentResponse> response) {
                updatePaymentView.hideProgress();
                if(response.code()==200){
                    updatePaymentView.onPaymentUpdated(response.body());
                }else if(response.code()==422){
                    try {
                        String errorString;
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        errorString = jObjError.get("error").toString();
                        updatePaymentView.showWarning(mActivity, "", errorString, "error");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }

            @Override
            public void onFailure(Call<UpdatePaymentResponse> call, Throwable t) {
                updatePaymentView.hideProgress();
                updatePaymentView.onUpdateFailure();
            }
        });
    }
}
