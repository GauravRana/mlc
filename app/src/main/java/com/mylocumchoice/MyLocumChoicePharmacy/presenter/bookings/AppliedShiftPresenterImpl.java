package com.mylocumchoice.MyLocumChoicePharmacy.presenter.bookings;

import android.app.Activity;

import com.google.gson.JsonObject;
import com.mylocumchoice.MyLocumChoicePharmacy.R;
import com.mylocumchoice.MyLocumChoicePharmacy.model.bookings.AppliedShiftResponse;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.SharedPrefManager;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.Utils;
import com.mylocumchoice.MyLocumChoicePharmacy.view.base.BaseApplication;
import com.mylocumchoice.MyLocumChoicePharmacy.view.bookings.viewinterface.AppliedShiftView;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AppliedShiftPresenterImpl implements AppliedShiftsPresenter {

    Utils mUtils;
    AppliedShiftView mView;
    Activity activity;
    public AppliedShiftPresenterImpl(AppliedShiftView mView,Activity activity) {
        this.activity=activity;
        this.mView=mView;
    }

    @Override
    public void getPendingBookings(JsonObject jsonObject,int pageNum) {
        if(pageNum==1){
            mView.showProgress();
        }
        BaseApplication.getRestClient().getService().getPendingShifts(SharedPrefManager.getInstance(activity).getEmail()
                ,SharedPrefManager.getInstance(activity).getAuthToken(),jsonObject).enqueue(new Callback<AppliedShiftResponse>() {
            @Override
            public void onResponse(Call<AppliedShiftResponse> call, Response<AppliedShiftResponse> response) {
                try{
                    try {
                        mView.hideProgress();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    if(response.code() == 200){
                        try{
                            mView.onPendingListFetch(response.body());
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }else if(response.code() == 401){
                        try{
                            mUtils = new Utils();
                            mUtils.showDialog(activity,"", activity.getResources().getString(R.string.text_sign_out));
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }else if(response.code() == 422){
                        try {
                            String errorString;
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            errorString = jObjError.get("error").toString();
                            mView.showWarning(activity, "", errorString, "error");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }else{
                        try{
                            mView.showWarning(activity, "", activity.getResources().getString(R.string.handle_strange_error), "error");
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }catch (Exception e){

                }

            }

            @Override
            public void onFailure(Call<AppliedShiftResponse> call, Throwable t) {
                try{
                    try {
                        mView.hideProgress();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    mView.onPendingListError();
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        });

    }

    @Override
    public void getDeclinedBookings(JsonObject jsonObject,int pageNum) {
        if(pageNum==1){
            mView.showProgress();
        }
        BaseApplication.getRestClient().getService().getDeclinedShifts(SharedPrefManager.getInstance(activity).getEmail()
                ,SharedPrefManager.getInstance(activity).getAuthToken(),jsonObject).enqueue(new Callback<AppliedShiftResponse>() {
            @Override
            public void onResponse(Call<AppliedShiftResponse> call, Response<AppliedShiftResponse> response) {
                try{
                    try {
                        mView.hideProgress();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    if(response.code()==500||response.code()==401){
                        try {
                            String errorString;
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            errorString = jObjError.get("error").toString();
                            mView.showWarning(activity, "", errorString, "error");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }else {
                        mView.onDeclinedListFetch(response.body());
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(Call<AppliedShiftResponse> call, Throwable t) {
                try{
                    try {
                        mView.hideProgress();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    mView.onDeclinedListError();
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        });
    }
}
