package com.mylocumchoice.MyLocumChoicePharmacy.presenter.bookings;

import android.app.Activity;

import com.mylocumchoice.MyLocumChoicePharmacy.R;
import com.mylocumchoice.MyLocumChoicePharmacy.model.bookings.BookingDetailResponse;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.SharedPrefManager;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.Utils;
import com.mylocumchoice.MyLocumChoicePharmacy.view.base.BaseApplication;
import com.mylocumchoice.MyLocumChoicePharmacy.view.bookings.viewinterface.BookingDetailView;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookingDetailPresenterImpl implements BookingDetailPresenter {

    Utils mUtils;
    BookingDetailView mView;
    Activity mActivity;
    public BookingDetailPresenterImpl(BookingDetailView mView, Activity mActivity) {
        this.mActivity=mActivity;
        this.mView=mView;
    }

    @Override
    public void onGetBookingDetails(int id) {
        mView.showProgress();
        BaseApplication.getRestClient().getService().getBookingDetails(SharedPrefManager.getInstance(mActivity).getEmail()
                ,SharedPrefManager.getInstance(mActivity).getAuthToken(),id).enqueue(new Callback<BookingDetailResponse>() {
            @Override
            public void onResponse(Call<BookingDetailResponse> call, Response<BookingDetailResponse> response) {
                try{
                    mView.hideProgress();
                    if(response.code()==200) {
                        mView.onDetailsFetched(response.body());
                    }else if(response.code() == 401){
                        try{
                            mUtils = new Utils();
                            mUtils.showDialog(mActivity,"", mActivity.getResources().getString(R.string.text_sign_out));
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }else if(response.code() == 422){
                        try {
                            String errorString;
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            errorString = jObjError.get("error").toString();
                            mView.showWarning(mActivity, "", errorString, "error");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }else{
                        try{
                            mView.showWarning(mActivity, "", mActivity.getResources().getString(R.string.handle_strange_error), "error");
                        }catch (Exception e){
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
                    mView.hideProgress();
                    mView.onGettingError();
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        });
    }

    @Override
    public void onGetBookingDetailsNotification(int id, int notification_id) {
        mView.showProgress();
        BaseApplication.getRestClient().getService().getBookingDetailsNotification(SharedPrefManager.getInstance(mActivity).getEmail()
                ,SharedPrefManager.getInstance(mActivity).getAuthToken(),id,notification_id).enqueue(new Callback<BookingDetailResponse>() {
            @Override
            public void onResponse(Call<BookingDetailResponse> call, Response<BookingDetailResponse> response) {
                try{
                    mView.hideProgress();
                    if(response.code()==200) {
                        mView.onDetailsFetched(response.body());
                    }else if(response.code() == 401){
                        try{
                            mUtils = new Utils();
                            mUtils.showDialog(mActivity,"", mActivity.getResources().getString(R.string.text_sign_out));
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }else if(response.code() == 422){
                        try {
                            String errorString;
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            errorString = jObjError.get("error").toString();
                            mView.showWarning(mActivity, "", errorString, "error");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }else{
                        try{
                            mView.showWarning(mActivity, "", mActivity.getResources().getString(R.string.handle_strange_error), "error");
                        }catch (Exception e){
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
                    mView.hideProgress();
                    mView.onGettingError();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }
}
