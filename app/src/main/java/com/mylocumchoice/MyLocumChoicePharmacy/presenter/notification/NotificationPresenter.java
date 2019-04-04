package com.mylocumchoice.MyLocumChoicePharmacy.presenter.notification;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.WindowManager;

import com.google.gson.JsonObject;
import com.mylocumchoice.MyLocumChoicePharmacy.R;
import com.mylocumchoice.MyLocumChoicePharmacy.model.login.SignInRes;
import com.mylocumchoice.MyLocumChoicePharmacy.model.notification.NotificationRes;
import com.mylocumchoice.MyLocumChoicePharmacy.presenter.login.LoginVP;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.SharedPrefManager;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.Utils;
import com.mylocumchoice.MyLocumChoicePharmacy.view.base.BaseApplication;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationPresenter implements NotificationVP.Presenter {
    Utils mUtils;
    private NotificationVP.View mView;
    private Activity context;

    ProgressDialog dialog;

    public NotificationPresenter(Activity context, NotificationVP.View mView) {
        this.mView=mView;
        this.context = context;
    }

    @Override
    public void onNotification() {
            mView.showProgress();
            BaseApplication.getRestClient().getService().getNotifications(SharedPrefManager.getInstance(context).getEmail()
                    ,SharedPrefManager.getInstance(context).getAuthToken()).enqueue(new Callback<NotificationRes>() {
                @Override
                public void onResponse(Call<NotificationRes> call, Response<NotificationRes> response) {
                    try {
                        mView.hideProgress();
                        if(response.code() == 200){
                            mView.onNotificationSuccess(response);
                        } else if (response.code() == 422) {
                            try {
                                String errorString;
                                JSONObject jObjError = new JSONObject(response.errorBody().string());
                                errorString = jObjError.get("error").toString();
                                mView.showWarning(context, "", errorString, "error");
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else if(response.code() == 401){
                            try{
                                mUtils = new Utils();
                                mUtils.showDialog(context,"", context.getResources().getString(R.string.text_sign_out));
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        } else {
                            try{
                                mView.showWarning(context, "", context.getResources().getString(R.string.handle_strange_error), "error");
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }

                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }

                @Override
                public void onFailure(Call<NotificationRes> call, Throwable t) {
                    try{
                        mView.hideProgress();
                        mView.onNotificationfailed();
                    }catch (Exception e){

                    }

                }
            });
        }



    @Override
    public void readNotification(int id) {
        mView.showProgress();
        BaseApplication.getRestClient().getService().readNotification(SharedPrefManager.getInstance(context).getEmail()
                ,SharedPrefManager.getInstance(context).getAuthToken(), id).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                try {
                    mView.hideProgress();
                    mView.onReadNotification(response);
                }catch (Exception e){
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                try{
                    mView.hideProgress();
                    mView.onNotificationfailed();
                }catch (Exception e){

                }

            }
        });
    }

    @Override
    public void deleteNotification() {
        mView.showProgress();
        BaseApplication.getRestClient().getService().deleteNotification(SharedPrefManager.getInstance(context).getEmail()
                ,SharedPrefManager.getInstance(context).getAuthToken()).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                try {
                    mView.hideProgress();
                    mView.onDeleteNotification(response);
                }catch (Exception e){
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                try{
                    mView.hideProgress();
                    mView.onNotificationfailed();
                }catch (Exception e){

                }

            }
        });
    }

    @Override
    public void deleteSingleNotification(Context context, int id) {
        try{
            dialog = createProgressDialog(context);
        }catch (Exception e){
            e.printStackTrace();
        }
        BaseApplication.getRestClient().getService().onSingleNotifyDelete(SharedPrefManager.getInstance(context).getEmail()
                ,SharedPrefManager.getInstance(context).getAuthToken(), id).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                try {
                    dialog.cancel();
                    mView.onDeleteSingleNotification(response);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                try{
                    dialog.cancel();
                    mView.onNotificationfailed();
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
