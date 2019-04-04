package com.mylocumchoice.MyLocumChoicePharmacy.presenter.calender;

import android.app.Activity;
import android.content.Context;

import com.mylocumchoice.MyLocumChoicePharmacy.R;
import com.mylocumchoice.MyLocumChoicePharmacy.model.calender.EventListResponse;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.SharedPrefManager;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.Utils;
import com.mylocumchoice.MyLocumChoicePharmacy.view.base.BaseApplication;
import com.mylocumchoice.MyLocumChoicePharmacy.view.calender.viewinterface.EventListView;

import org.json.JSONObject;

import java.io.UTFDataFormatException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventListPresenterImpl implements EventListPresenter{

    Utils mUtils;
    EventListView mView;
    Activity mContext;
    public EventListPresenterImpl(EventListView mView,Activity mContext) {
        this.mView = mView;
        this.mContext=mContext;
    }

    @Override
    public void onGettingEventList() {
        mView.showProgress();
        BaseApplication.getRestClient().getService().getEventsList(SharedPrefManager.getInstance(mContext).getEmail()
                ,SharedPrefManager.getInstance(mContext).getAuthToken()).enqueue(new Callback<EventListResponse>() {
            @Override
            public void onResponse(Call<EventListResponse> call, Response<EventListResponse> response) {
                try{
                    mView.hideProgress();
                    if(response.code() == 200){
                        mView.onEventListFetched(response.body());
                    }else if (response.code() == 422) {
                        try {
                            String errorString;
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            errorString = jObjError.get("error").toString();
                            mView.showWarning(mContext, "", errorString, "error");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else if (response.code() == 401) {
                        try{
                            mUtils = new Utils();
                            mUtils.showDialog(mContext,"", mContext.getResources().getString(R.string.text_sign_out));
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }else {
                        try{
                            mView.showWarning(mContext, "", mContext.getResources().getString(R.string.handle_strange_error), "error");
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<EventListResponse> call, Throwable t) {
                try{
                    mView.hideProgress();
                    mView.onGettingError();
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        });
    }

    @Override
    public void onGettingEventListNotification(int notification_id) {
        mView.showProgress();
        BaseApplication.getRestClient().getService().getEventsListNotification(SharedPrefManager.getInstance(mContext).getEmail()
                ,SharedPrefManager.getInstance(mContext).getAuthToken(),notification_id).enqueue(new Callback<EventListResponse>() {
            @Override
            public void onResponse(Call<EventListResponse> call, Response<EventListResponse> response) {
                try{
                    mView.hideProgress();
                    if(response.code() == 200){
                        mView.onEventListFetched(response.body());
                    }else if (response.code() == 422) {
                        try {
                            String errorString;
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            errorString = jObjError.get("error").toString();
                            mView.showWarning(mContext, "", errorString, "error");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else if (response.code() == 401) {
                        try{
                            mUtils = new Utils();
                            mUtils.showDialog(mContext,"", mContext.getResources().getString(R.string.text_sign_out));
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }else {
                        try{
                            mView.showWarning(mContext, "", mContext.getResources().getString(R.string.handle_strange_error), "error");
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<EventListResponse> call, Throwable t) {
                try{
                    mView.hideProgress();
                    mView.onGettingError();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }
}
