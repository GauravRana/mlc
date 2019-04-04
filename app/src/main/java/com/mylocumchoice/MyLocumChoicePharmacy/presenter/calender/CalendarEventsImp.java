package com.mylocumchoice.MyLocumChoicePharmacy.presenter.calender;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.WindowManager;

import com.mylocumchoice.MyLocumChoicePharmacy.R;
import com.mylocumchoice.MyLocumChoicePharmacy.model.calender.CalendarEventsRes;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.SharedPrefManager;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.Utils;
import com.mylocumchoice.MyLocumChoicePharmacy.view.base.BaseApplication;
import com.mylocumchoice.MyLocumChoicePharmacy.view.calender.viewinterface.CalendarEventsView;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CalendarEventsImp implements CalendarEventsPresenter{
    CalendarEventsView mView;
    Activity mContext;
    Utils mUtils;

    ProgressDialog dialog;

    public CalendarEventsImp(CalendarEventsView mView, Activity mContext) {
        this.mView = mView;
        this.mContext = mContext;
    }

    @Override
    public void onCalendarEventsList(Activity activity) {
        try{
            dialog = createProgressDialog(activity);
        }catch (Exception e){
            e.printStackTrace();
        }

        //mView.showProgress();
        BaseApplication.getRestClient().getService().getCalenderEvents(SharedPrefManager.getInstance(mContext).getEmail()
                ,SharedPrefManager.getInstance(mContext).getAuthToken()).enqueue(new Callback<CalendarEventsRes>() {
            @Override
            public void onResponse(Call<CalendarEventsRes> call, Response<CalendarEventsRes> response) {
                try{

                    dialog.cancel();
                    //mView.hideProgress();
                    if(response.code() == 200){
                        mView.onCalendarEvents(response.body());
                    } else if (response.code() == 422) {
                        try {
                            String errorString;
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            errorString = jObjError.get("error").toString();
                            mView.showWarning(mContext, "", errorString, "error");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else if(response.code() == 401){
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

                }

            }

            @Override
            public void onFailure(Call<CalendarEventsRes> call, Throwable t) {
                try{
                    dialog.cancel();
                    //mView.hideProgress();
                    mView.onCalendarEventsError();
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        });
    }


    public ProgressDialog createProgressDialog(Context context) {
        ProgressDialog dialog = new ProgressDialog(context);
        try {
            dialog.show();
        } catch (WindowManager.BadTokenException e) {

        }
        dialog.setCancelable(false);
        dialog.getWindow()
                .setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.custom_dialog);

        return dialog;
    }
}
