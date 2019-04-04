package com.mylocumchoice.MyLocumChoicePharmacy.presenter.calender;

import android.content.Context;

import com.google.gson.JsonObject;
import com.mylocumchoice.MyLocumChoicePharmacy.model.calender.CalendarEventsRes;
import com.mylocumchoice.MyLocumChoicePharmacy.model.calender.CreateCalendarRes;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.SharedPrefManager;
import com.mylocumchoice.MyLocumChoicePharmacy.view.base.BaseApplication;
import com.mylocumchoice.MyLocumChoicePharmacy.view.calender.viewinterface.CalendarEventsView;
import com.mylocumchoice.MyLocumChoicePharmacy.view.calender.viewinterface.CreateCalendarView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateCalendarImp implements CreateCalendarPresenter {
    CreateCalendarView mView;
    Context mContext;

    public CreateCalendarImp(CreateCalendarView mView, Context mContext) {
        this.mView = mView;
        this.mContext = mContext;
    }

    @Override
    public void oncreateCalendarList(JsonObject jsonObject) {
        mView.showProgress();
        BaseApplication.getRestClient().getService().postCalenderEvents(SharedPrefManager.getInstance(mContext).getEmail()
                ,SharedPrefManager.getInstance(mContext).getAuthToken(), jsonObject).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                try{
                    mView.hideProgress();
                    if(response.code()==500){

                    }else {
                        mView.onCreateCalendar(response);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                try{
                    mView.hideProgress();
                    mView.onCreateCalendarError();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }
}
