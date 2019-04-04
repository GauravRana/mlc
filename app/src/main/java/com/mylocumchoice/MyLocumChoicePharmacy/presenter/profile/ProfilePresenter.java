package com.mylocumchoice.MyLocumChoicePharmacy.presenter.profile;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.WindowManager;

import com.mylocumchoice.MyLocumChoicePharmacy.R;
import com.mylocumchoice.MyLocumChoicePharmacy.model.profile.PrefernceRes;
import com.mylocumchoice.MyLocumChoicePharmacy.model.profile.ProfileRes;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.SharedPrefManager;
import com.mylocumchoice.MyLocumChoicePharmacy.view.base.BaseApplication;

import org.jsoup.Connection;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfilePresenter implements ProfileVP.Presenter {

    private ProfileVP.View mView;
    private Activity activity;

    ProgressDialog dialog;

    public ProfilePresenter(ProfileVP.View view, Activity activity){
        this.activity = activity;
        this.mView = view;
    }

    @Override
    public void getAccount(Context context) {

        try {
            if (dialog.isShowing()) {
                dialog.cancel();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        try{
            dialog = createProgressDialog(context);
        }catch (Exception e){
            e.printStackTrace();
        }
        BaseApplication.getRestClient().getService().getAccount(
                SharedPrefManager.getInstance(context).getEmail()
                ,SharedPrefManager.getInstance(context).getAuthToken()).enqueue(new Callback<ProfileRes>() {
            @Override
            public void onResponse(Call<ProfileRes> call, Response<ProfileRes> response) {
                try{
                    dialog.cancel();
                    //mView.onSuccessUpload(response);
                }catch (Exception e){
                    e.printStackTrace();
                }
                try {
                    mView.getProfileSuccess(response);
                }catch (Exception e){
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<ProfileRes> call, Throwable t) {
                try{
                    try{
                        dialog.cancel();
                        //mView.onSuccessUpload(response);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    mView.showWarning(activity, "",activity.getResources().getString(R.string.errorTimeOut), "error");
                }catch (Exception e){
                    e.printStackTrace();
                }


            }
        });
    }

    @Override
    public void getAccountNotification(Context contex, int notification_id) {
        try{
            dialog = createProgressDialog(contex);
        }catch (Exception e){
            e.printStackTrace();
        }
        BaseApplication.getRestClient().getService().getAccountNotification(SharedPrefManager.getInstance(contex).getEmail()
                ,SharedPrefManager.getInstance(contex).getAuthToken(),notification_id).enqueue(new Callback<ProfileRes>() {
            @Override
            public void onResponse(Call<ProfileRes> call, Response<ProfileRes> response) {
                try{
                    dialog.cancel();
                    //mView.onSuccessUpload(response);
                }catch (Exception e){
                    e.printStackTrace();
                }
                try {
                    mView.getProfileSuccess(response);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ProfileRes> call, Throwable t) {
                try{
                    try{
                        dialog.cancel();
                        //mView.onSuccessUpload(response);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    mView.showWarning(activity, "",activity.getResources().getString(R.string.errorTimeOut), "error");
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

    }


    @Override
    public void uploadImage(Context context, MultipartBody.Part part) {
        try {
            if (dialog.isShowing()) {
                dialog.cancel();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        try{
            dialog = createProgressDialog(context);
        }catch (Exception e){
            e.printStackTrace();
        }
        BaseApplication.getRestClient().getService().updateProfilePic(
                SharedPrefManager.getInstance(context).getEmail()
                ,SharedPrefManager.getInstance(context).getAuthToken()
                ,part).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                try{
                    dialog.cancel();
                    //mView.onSuccessUpload(response);
                }catch (Exception e){
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                try{
                    dialog.cancel();
                    //mView.hideProgress();
                    mView.showWarning(activity, "",activity.getResources().getString(R.string.errorTimeOut), "error");
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void activate(Context context) {
        try {
            if (dialog.isShowing()) {
                dialog.cancel();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        try{
            dialog = createProgressDialog(context);
        }catch (Exception e){
            e.printStackTrace();
        }
        BaseApplication.getRestClient().getService().activateAccount(
                SharedPrefManager.getInstance(context).getEmail()
                ,SharedPrefManager.getInstance(context).getAuthToken()
        ).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                try{
                    dialog.cancel();
                    //mView.onSuccessUpload(response);
                }catch (Exception e){
                    e.printStackTrace();
                }
                try{
                    mView.activateSuccess(response);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                try{
                    try{
                        dialog.cancel();
                        //mView.onSuccessUpload(response);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    mView.showWarning(activity, "",activity.getResources().getString(R.string.errorTimeOut), "error");
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
