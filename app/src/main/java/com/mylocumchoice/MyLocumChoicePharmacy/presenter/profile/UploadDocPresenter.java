package com.mylocumchoice.MyLocumChoicePharmacy.presenter.profile;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;

import com.mylocumchoice.MyLocumChoicePharmacy.R;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.SharedPrefManager;
import com.mylocumchoice.MyLocumChoicePharmacy.view.base.BaseApplication;

import java.io.IOException;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UploadDocPresenter implements UploadDocVP.Presenter {

    private UploadDocVP.View mView;
    private Activity activity;

    ProgressDialog dialog;

    public UploadDocPresenter(UploadDocVP.View view, Activity activity){
        this.mView = view;
        this.activity = activity;
    }

    @Override
    public void uploadDoc(Context context, MultipartBody.Part doc, RequestBody field_id) {
        try{
            dialog = createProgressDialog(context);
        }catch (Exception e){
            e.printStackTrace();
        }

        BaseApplication.getRestClient().getService().uploadDoc(
                SharedPrefManager.getInstance(context).getEmail()
                ,SharedPrefManager.getInstance(context).getAuthToken(), doc, field_id).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                try{
                    dialog.cancel();
                    mView.onUploadResponse(context ,response);
                    //mView.hideProgress();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                try {
                    dialog.cancel();
                    mView.hideProgress();
                    mView.showWarning(activity, "",activity.getResources().getString(R.string.errorTimeOut), "error");
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        });
    }

    @Override
    public void uploadComplianceDoc(Context context, MultipartBody.Part doc, RequestBody field_id) {
        try{
            dialog = createProgressDialog(context);
        }catch (Exception e){
            e.printStackTrace();
        }
        mView.showProgress();
        BaseApplication.getRestClient().getService().uploadComplianceDoc(
                SharedPrefManager.getInstance(context).getEmail()
                ,SharedPrefManager.getInstance(context).getAuthToken(), doc, field_id).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                try {
                    dialog.cancel();
                    mView.onUploadResponse(context ,response);
                    mView.hideProgress();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                try {
                    dialog.cancel();
                    mView.hideProgress();
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
