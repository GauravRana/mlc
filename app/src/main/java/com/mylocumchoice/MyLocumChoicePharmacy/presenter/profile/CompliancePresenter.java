package com.mylocumchoice.MyLocumChoicePharmacy.presenter.profile;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.WindowManager;

import com.mylocumchoice.MyLocumChoicePharmacy.R;
import com.mylocumchoice.MyLocumChoicePharmacy.model.profile.ComplianceRes;
import com.mylocumchoice.MyLocumChoicePharmacy.model.profile.PrefernceRes;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.SharedPrefManager;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.Utils;
import com.mylocumchoice.MyLocumChoicePharmacy.view.base.BaseApplication;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CompliancePresenter implements ComplianceVP.Presenter {
    ComplianceVP.View mView;
    Activity activity;
    Utils mUtils;
    ProgressDialog dialog;

    public CompliancePresenter(ComplianceVP.View view, Activity activity) {
        this.mView = view;
        this.activity = activity;
    }

    @Override
    public void getCompliance(Context context) {
        try{
            dialog = createProgressDialog(context);
        }catch (Exception e){
            e.printStackTrace();
        }
        BaseApplication.getRestClient().getService().getCompliance(
                SharedPrefManager.getInstance(context).getEmail()
                ,SharedPrefManager.getInstance(context).getAuthToken())
                .enqueue(new Callback<ComplianceRes>() {
                    @Override
                    public void onResponse(Call<ComplianceRes> call, Response<ComplianceRes> response) {
                        try{
                            dialog.cancel();
                            if(response.code() == 200) {
                                mView.onGetResponse(context, response);
                            }else if (response.code() == 422) {
                                try {
                                    String errorString;
                                    JSONObject jObjError = new JSONObject(response.errorBody().string());
                                    errorString = jObjError.get("error").toString();
                                    mView.showWarning(activity, "", errorString, "error");
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                            else if(response.code() == 401){
                                try{
                                    mUtils = new Utils();
                                    mUtils.showDialog(activity,"", activity.getResources().getString(R.string.text_sign_out));
                                }catch (Exception e){
                                    e.printStackTrace();
                                }
                            }
                            else {
                                try{
                                    mView.showWarning(activity, "", activity.getResources().getString(R.string.handle_strange_error), "error");
                                }catch (Exception e){
                                    e.printStackTrace();
                                }
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onFailure(Call<ComplianceRes> call, Throwable t) {
                        try{
                            dialog.cancel();
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
