package com.mylocumchoice.MyLocumChoicePharmacy.presenter.bookings;

import android.app.Activity;

import com.mylocumchoice.MyLocumChoicePharmacy.model.bookings.BookingDetailResponse;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.SharedPrefManager;
import com.mylocumchoice.MyLocumChoicePharmacy.view.base.BaseApplication;
import com.mylocumchoice.MyLocumChoicePharmacy.view.bookings.viewinterface.BookingDetailView;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CompBookingDetailPresenterImpl implements CompBookingDetailPresenter {

    BookingDetailView bookingDetailView;
    Activity mActivity;
    public CompBookingDetailPresenterImpl(BookingDetailView bookingDetailView,Activity mActivity) {
        this.bookingDetailView=bookingDetailView;
        this.mActivity=mActivity;
    }

    @Override
    public void onGetBookingDetails(int id) {
        bookingDetailView.showProgress();
        BaseApplication.getRestClient().getService().getCompletedBookingDetails(SharedPrefManager.getInstance(mActivity).getEmail()
                ,SharedPrefManager.getInstance(mActivity).getAuthToken(),id).enqueue(new Callback<BookingDetailResponse>() {
            @Override
            public void onResponse(Call<BookingDetailResponse> call, Response<BookingDetailResponse> response) {
                bookingDetailView.hideProgress();
                try{
                    if(response.code()==200) {
                        bookingDetailView.onDetailsFetched(response.body());
                    }else if (response.code() == 401) {
                        try {
                            String errorString;
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            errorString = jObjError.get("error").toString();
                            bookingDetailView.showWarning(mActivity, "", errorString, "error");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<BookingDetailResponse> call, Throwable t) {
                try {
                    bookingDetailView.hideProgress();
                    bookingDetailView.onGettingError();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onGetCancelledBookingDetails(int id) {
        bookingDetailView.showProgress();
        BaseApplication.getRestClient().getService().getCancelledBookingDetails(SharedPrefManager.getInstance(mActivity).getEmail()
                ,SharedPrefManager.getInstance(mActivity).getAuthToken(),id).enqueue(new Callback<BookingDetailResponse>() {
            @Override
            public void onResponse(Call<BookingDetailResponse> call, Response<BookingDetailResponse> response) {
                bookingDetailView.hideProgress();
                try{
                    if(response.code()==200) {
                        bookingDetailView.onDetailsFetched(response.body());
                    }else if (response.code() == 401) {
                        try {
                            String errorString;
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            errorString = jObjError.get("error").toString();
                            bookingDetailView.showWarning(mActivity, "", errorString, "error");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<BookingDetailResponse> call, Throwable t) {
                try {
                    bookingDetailView.hideProgress();
                    bookingDetailView.onGettingError();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }
}
