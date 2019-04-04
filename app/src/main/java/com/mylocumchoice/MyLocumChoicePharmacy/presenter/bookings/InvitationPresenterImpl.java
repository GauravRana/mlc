package com.mylocumchoice.MyLocumChoicePharmacy.presenter.bookings;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.WindowManager;

import com.google.gson.JsonObject;
import com.mylocumchoice.MyLocumChoicePharmacy.R;
import com.mylocumchoice.MyLocumChoicePharmacy.model.shifts.OpenShiftResponse;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.SharedPrefManager;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.Utils;
import com.mylocumchoice.MyLocumChoicePharmacy.view.base.BaseApplication;
import com.mylocumchoice.MyLocumChoicePharmacy.view.bookings.viewinterface.InvitationView;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InvitationPresenterImpl implements InvitationPresenter{
    Utils mUtils;
    InvitationView mView;
    Activity mContext;
    ProgressDialog dialog;
    public InvitationPresenterImpl(InvitationView mView,Activity mContext) {
        this.mContext=mContext;
        this.mView=mView;
    }

    @Override
    public void onGetInvitations(JsonObject openShiftReq,int pageNum) {
        try {
            if (dialog.isShowing()) {
                dialog.cancel();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        if(pageNum==1){
            try{
                dialog = createProgressDialog(mContext);
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        BaseApplication.getRestClient().getService().getShiftInvitations(SharedPrefManager.getInstance(mContext).getEmail()
                ,SharedPrefManager.getInstance(mContext).getAuthToken(),openShiftReq).enqueue(new Callback<OpenShiftResponse>() {
            @Override
            public void onResponse(Call<OpenShiftResponse> call, Response<OpenShiftResponse> response) {
                try{
                    dialog.cancel();
                    //mView.onSuccessUpload(response);
                }catch (Exception e){
                    e.printStackTrace();
                }
                if(response.code() == 200) {
                    try{
                        mView.onInvitesFetch(response.body());
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }
                else if (response.code() == 422) {
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

            }

            @Override
            public void onFailure(Call<OpenShiftResponse> call, Throwable t) {
                try{
                    try{
                        dialog.cancel();
                        //mView.onSuccessUpload(response);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    mView.onInviteError();
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        });
    }

    @Override
    public void getInvitationsDetail(int id) {
        try {
            if (dialog.isShowing()) {
                dialog.cancel();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        try{
            dialog = createProgressDialog(mContext);
        }catch (Exception e){
            e.printStackTrace();
        }
        BaseApplication.getRestClient().getService().getShiftInvitationsDetail(SharedPrefManager.getInstance(mContext).getEmail()
                ,SharedPrefManager.getInstance(mContext).getAuthToken(),id).enqueue(new Callback<OpenShiftResponse>() {
            @Override
            public void onResponse(Call<OpenShiftResponse> call, Response<OpenShiftResponse> response) {
                try{
                    dialog.cancel();
                    //mView.onSuccessUpload(response);
                }catch (Exception e){
                    e.printStackTrace();
                }
                if(response.code() == 200) {
                    try{
                        mView.onInvitesDetailFetch(response.body());
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }
                else if (response.code() == 422) {
                    try {
                        String errorString;
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        errorString = jObjError.get("error").toString();
                        mView.showWarning(mContext, "", errorString, "error");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                else if (response.code() == 401) {
                    try {

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }else{
                    try{
                        mView.showWarning(mContext, "", mContext.getResources().getString(R.string.handle_strange_error), "error");
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<OpenShiftResponse> call, Throwable t) {
                try{
                    try{
                        dialog.cancel();
                        //mView.onSuccessUpload(response);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    mView.onInviteDetailError();
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
