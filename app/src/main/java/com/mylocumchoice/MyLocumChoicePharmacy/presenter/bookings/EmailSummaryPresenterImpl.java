package com.mylocumchoice.MyLocumChoicePharmacy.presenter.bookings;

import android.app.Activity;

import com.google.gson.JsonObject;
import com.mylocumchoice.MyLocumChoicePharmacy.model.bookings.EmailSummaryResponse;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.SharedPrefManager;
import com.mylocumchoice.MyLocumChoicePharmacy.view.base.BaseApplication;
import com.mylocumchoice.MyLocumChoicePharmacy.view.bookings.viewinterface.PaymentSummaryView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EmailSummaryPresenterImpl implements EmailSummaryPresenter{

    PaymentSummaryView mView;
    Activity mActivity;
    public EmailSummaryPresenterImpl(PaymentSummaryView mView, Activity mActivity){
        this.mActivity=mActivity;
        this.mView=mView;
    }

    @Override
    public void onGetEmailSummary(JsonObject jsonObject) {
        mView.showProgress();
        BaseApplication.getRestClient().getService().getPaymentSummary(SharedPrefManager.getInstance(mActivity).getEmail()
                ,SharedPrefManager.getInstance(mActivity).getAuthToken(),jsonObject).enqueue(new Callback<EmailSummaryResponse>() {
            @Override
            public void onResponse(Call<EmailSummaryResponse> call, Response<EmailSummaryResponse> response) {
                mView.hideProgress();
                if(response.code()==200) {
                    mView.onSummarySuccess(response.body());
                }else{
                    mView.onSummaryFailure();
                }
            }

            @Override
            public void onFailure(Call<EmailSummaryResponse> call, Throwable t) {
                mView.hideProgress();
                mView.onSummaryFailure();
            }
        });
    }
}
