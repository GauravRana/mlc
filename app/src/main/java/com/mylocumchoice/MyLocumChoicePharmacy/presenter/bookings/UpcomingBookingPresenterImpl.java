package com.mylocumchoice.MyLocumChoicePharmacy.presenter.bookings;

import android.app.Activity;

import com.google.gson.JsonObject;
import com.mylocumchoice.MyLocumChoicePharmacy.R;
import com.mylocumchoice.MyLocumChoicePharmacy.model.bookings.UpcomingBookingResponse;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.SharedPrefManager;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.Utils;
import com.mylocumchoice.MyLocumChoicePharmacy.view.base.BaseApplication;
import com.mylocumchoice.MyLocumChoicePharmacy.view.bookings.viewinterface.UpcomingBookingView;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpcomingBookingPresenterImpl implements UpcomingBookingPresenter {

    UpcomingBookingView upcomingBookingView;
    Activity activity;
    Utils mUtils;
    public UpcomingBookingPresenterImpl(UpcomingBookingView upcomingBookingView, Activity activity) {
        this.upcomingBookingView=upcomingBookingView;
        this.activity=activity;
    }

    @Override
    public void getUpcomingBookings(JsonObject jsonObject,int pageNum) {
        if(pageNum==1){
            upcomingBookingView.showProgress();
        }

        BaseApplication.getRestClient().getService().getUpcomingBookings(SharedPrefManager.getInstance(activity).getEmail()
                ,SharedPrefManager.getInstance(activity).getAuthToken(),jsonObject).enqueue(new Callback<UpcomingBookingResponse>() {
            @Override
            public void onResponse(Call<UpcomingBookingResponse> call, Response<UpcomingBookingResponse> response) {
                try {
                    upcomingBookingView.hideProgress();
                }catch (Exception e){
                    e.printStackTrace();
                }
                if(response.code() == 200){
                    try {
                        upcomingBookingView.onBookingListFetch(response.body());
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }else if(response.code() == 422){
                    try {
                        String errorString;
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        errorString = jObjError.get("error").toString();
                        upcomingBookingView.showWarning(activity, "", errorString, "error");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }else if(response.code() == 401){
                    try{
                        mUtils = new Utils();
                        mUtils.showDialog(activity,"", activity.getResources().getString(R.string.text_sign_out));
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }else {
                    try{
                        upcomingBookingView.showWarning(activity, "", activity.getResources().getString(R.string.handle_strange_error), "error");
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }

            }

            @Override
            public void onFailure(Call<UpcomingBookingResponse> call, Throwable t) {
                try {
                    upcomingBookingView.hideProgress();
                }catch (Exception e){
                    e.printStackTrace();
                }
                upcomingBookingView.onBookingListError();
            }
        });

    }
}
