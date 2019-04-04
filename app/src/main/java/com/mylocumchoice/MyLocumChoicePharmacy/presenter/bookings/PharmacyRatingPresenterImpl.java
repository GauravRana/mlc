package com.mylocumchoice.MyLocumChoicePharmacy.presenter.bookings;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.WindowManager;
import android.os.Handler;

import com.google.gson.JsonObject;
import com.mylocumchoice.MyLocumChoicePharmacy.R;
import com.mylocumchoice.MyLocumChoicePharmacy.model.bookings.RatingResponse;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.SharedPrefManager;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.Utils;
import com.mylocumchoice.MyLocumChoicePharmacy.view.base.BaseApplication;
import com.mylocumchoice.MyLocumChoicePharmacy.view.bookings.viewinterface.PharmacyRatingView;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PharmacyRatingPresenterImpl implements PharmacyRatingPresenter{

    Utils mUtils;
    Activity mActivity;
    PharmacyRatingView pharmacyRatingView;
    ProgressDialog dialog;

    public PharmacyRatingPresenterImpl(Activity mActivity, PharmacyRatingView pharmacyRatingView) {
        this.mActivity=mActivity;
        this.pharmacyRatingView=pharmacyRatingView;
    }

    @Override
    public void onAddRating(JsonObject jsonObject) {
        //pharmacyRatingView.showProgress();

        try{
            dialog = createProgressDialog(mActivity);
        }catch (Exception e){
            e.printStackTrace();
        }
        BaseApplication.getRestClient().getService().onRatePharmacy(SharedPrefManager.getInstance(mActivity).getEmail()
                ,SharedPrefManager.getInstance(mActivity).getAuthToken(),jsonObject).enqueue(new Callback<RatingResponse>() {
            @Override
            public void onResponse(Call<RatingResponse> call, Response<RatingResponse> response) {
                pharmacyRatingView.hideProgress();
                dialog.cancel();
                if(response.code()==201){
                    try{
                        pharmacyRatingView.onRatingSuccess(response.body());
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }else if(response.code() == 401){
                    try{
                        mUtils = new Utils();
                        mUtils.showDialog(mActivity,"", mActivity.getResources().getString(R.string.text_sign_out));
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }else if (response.code() == 422) {
                    try {
                        String errorString;
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        errorString = jObjError.get("error").toString();
                        pharmacyRatingView.showWarning(mActivity, "", errorString, "error");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        pharmacyRatingView.hideProgress();
                        if(response.code()==201){
                            try{
                                pharmacyRatingView.onRatingSuccess(response.body());
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }else if(response.code() == 401){
                            try{
                                mUtils = new Utils();
                                mUtils.showDialog(mActivity,"", mActivity.getResources().getString(R.string.text_sign_out));
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }else if (response.code() == 422) {
                            try {
                                String errorString;
                                JSONObject jObjError = new JSONObject(response.errorBody().string());
                                errorString = jObjError.get("error").toString();
                                pharmacyRatingView.showWarning(mActivity, "", errorString, "error");
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }, 500);

            }

            @Override
            public void onFailure(Call<RatingResponse> call, Throwable t) {
                try{
                    dialog.cancel();
                    pharmacyRatingView.hideProgress();
                    pharmacyRatingView.onRatingFailure();
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
