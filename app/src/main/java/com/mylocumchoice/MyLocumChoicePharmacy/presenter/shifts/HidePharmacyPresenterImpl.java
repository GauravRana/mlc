package com.mylocumchoice.MyLocumChoicePharmacy.presenter.shifts;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.WindowManager;

import com.mylocumchoice.MyLocumChoicePharmacy.R;
import com.mylocumchoice.MyLocumChoicePharmacy.model.profile.ComplianceDetailsRes;
import com.mylocumchoice.MyLocumChoicePharmacy.model.shifts.HidePharmacyResponse;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.SharedPrefManager;
import com.mylocumchoice.MyLocumChoicePharmacy.view.base.BaseApplication;
import com.mylocumchoice.MyLocumChoicePharmacy.view.shifts.viewinterface.HidePharmacyView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HidePharmacyPresenterImpl implements HidePharmacyPresenter{

    HidePharmacyView hidePharmacyView;
    Activity mActivity;
    ProgressDialog dialog;


    public HidePharmacyPresenterImpl(HidePharmacyView hidePharmacyView,Activity mActivity) {
        this.hidePharmacyView=hidePharmacyView;
        this.mActivity=mActivity;
    }

    @Override
    public void onHidePharmacy(int id) {
        try {
            if (dialog.isShowing()) {
                dialog.cancel();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        try{
            dialog = createProgressDialog(mActivity);
        }catch (Exception e){
            e.printStackTrace();
        }
        BaseApplication.getRestClient().getService().hideFromPharmacy(SharedPrefManager.getInstance(mActivity).getEmail()
                ,SharedPrefManager.getInstance(mActivity).getAuthToken(),id).enqueue(new Callback<HidePharmacyResponse>() {
            @Override
            public void onResponse(Call<HidePharmacyResponse> call, Response<HidePharmacyResponse> response) {
                try{
                    dialog.cancel();
                    //mView.onSuccessUpload(response);
                }catch (Exception e){
                    e.printStackTrace();
                }
                if(response.code()==201) {
                    hidePharmacyView.onSuccessfulHidden(response.body());
                }else{
                    hidePharmacyView.onHideFailure();
                }
            }

            @Override
            public void onFailure(Call<HidePharmacyResponse> call, Throwable t) {
                hidePharmacyView.hideProgress();
                hidePharmacyView.onHideFailure();
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
