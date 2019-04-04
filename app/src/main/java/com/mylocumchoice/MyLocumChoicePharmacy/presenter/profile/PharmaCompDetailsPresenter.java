package com.mylocumchoice.MyLocumChoicePharmacy.presenter.profile;

import android.app.Activity;
import android.content.Context;

import com.mylocumchoice.MyLocumChoicePharmacy.R;
import com.mylocumchoice.MyLocumChoicePharmacy.model.profile.ComplianceDetailsRes;
import com.mylocumchoice.MyLocumChoicePharmacy.model.profile.ComplianceEmailRes;
import com.mylocumchoice.MyLocumChoicePharmacy.model.profile.ComplianceRes;
import com.mylocumchoice.MyLocumChoicePharmacy.model.profile.PharmaComplianceVerificationRes;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.SharedPrefManager;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.Utils;
import com.mylocumchoice.MyLocumChoicePharmacy.view.base.BaseApplication;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PharmaCompDetailsPresenter implements PharmaComplianceDetailsVP.Presenter{
    PharmaComplianceDetailsVP.View mView;
    Activity activity;
    Utils mUtils;

    public PharmaCompDetailsPresenter(PharmaComplianceDetailsVP.View view, Activity activity) {
        this.mView = view;
        this.activity = activity;
    }

    @Override
    public void getComplianceDetails(Context context, int id) {
        mView.showProgress();
        BaseApplication.getRestClient().getService().getComplianceDetails(
                SharedPrefManager.getInstance(context).getEmail()
                ,SharedPrefManager.getInstance(context).getAuthToken(), id)
                .enqueue(new Callback<ComplianceDetailsRes>() {
                    @Override
                    public void onResponse(Call<ComplianceDetailsRes> call, Response<ComplianceDetailsRes> response) {
                        try {
                            mView.hideProgress();
                            if (response.code() == 200) {
                                mView.onGetDetailsResponse(context, response);
                            } else if (response.code() == 422) {
                                try {
                                    String errorString;
                                    JSONObject jObjError = new JSONObject(response.errorBody().string());
                                    errorString = jObjError.get("error").toString();
                                    mView.showWarning(activity, "", errorString, "error");
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            } else if (response.code() == 401) {
                                try {
                                    mUtils = new Utils();
                                    mUtils.showDialog(activity, "", activity.getResources().getString(R.string.text_sign_out));
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            } else {
                                try {
                                    mView.showWarning(activity, "", activity.getResources().getString(R.string.handle_strange_error), "error");
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onFailure(Call<ComplianceDetailsRes> call, Throwable t) {
                        try{
                            mView.hideProgress();
                            mView.showWarning(activity, "",activity.getResources().getString(R.string.errorTimeOut), "error");
                        }catch (Exception e){
                            e.printStackTrace();
                        }

                    }
         });
    }

    @Override
    public void getComplianceDetailsNotification(Context context, int id, int notification_id) {
        mView.showProgress();
        BaseApplication.getRestClient().getService().getComplianceDetailsNotification(SharedPrefManager.getInstance(context).getEmail()
                ,SharedPrefManager.getInstance(context).getAuthToken(), id,notification_id).enqueue(new Callback<ComplianceDetailsRes>() {
            @Override
            public void onResponse(Call<ComplianceDetailsRes> call, Response<ComplianceDetailsRes> response) {
                try {
                    mView.hideProgress();
                    if (response.code() == 200) {
                        mView.onGetDetailsResponse(context, response);
                    } else if (response.code() == 422) {
                        try {
                            String errorString;
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            errorString = jObjError.get("error").toString();
                            mView.showWarning(activity, "", errorString, "error");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else if (response.code() == 401) {
                        try {
                            mUtils = new Utils();
                            mUtils.showDialog(activity, "", activity.getResources().getString(R.string.text_sign_out));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        try {
                            mView.showWarning(activity, "", activity.getResources().getString(R.string.handle_strange_error), "error");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ComplianceDetailsRes> call, Throwable t) {
                try{
                    mView.hideProgress();
                    mView.showWarning(activity, "",activity.getResources().getString(R.string.errorTimeOut), "error");
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void emailCompliance(Context context, int id) {
        mView.showProgress();
        BaseApplication.getRestClient().getService().getEmailCompliance(
                SharedPrefManager.getInstance(context).getEmail()
                ,SharedPrefManager.getInstance(context).getAuthToken(), id)
                .enqueue(new Callback<ComplianceEmailRes>() {
                    @Override
                    public void onResponse(Call<ComplianceEmailRes> call, Response<ComplianceEmailRes> response) {
                        try{
                            mView.hideProgress();
                            mView.onComplianceEmailResponse(context,response);
                        }catch (Exception e){
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onFailure(Call<ComplianceEmailRes> call, Throwable t) {
                        try{
                            mView.hideProgress();
                            mView.showWarning(activity, "",activity.getResources().getString(R.string.errorTimeOut), "error");
                        }catch (Exception e){
                            e.printStackTrace();
                        }

                    }
                });
    }

    @Override
    public void postRequestVerify(Context context, int id) {
        mView.showProgress();
        BaseApplication.getRestClient().getService().postRequestVerifyCompliance(
                SharedPrefManager.getInstance(context).getEmail()
                ,SharedPrefManager.getInstance(context).getAuthToken(), id)
                .enqueue(new Callback<PharmaComplianceVerificationRes>() {
                    @Override
                    public void onResponse(Call<PharmaComplianceVerificationRes> call, Response<PharmaComplianceVerificationRes> response) {
                        try{
                            mView.hideProgress();
                            mView.postRequestVerifyCompliance(context,response);
                        }catch (Exception e){
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onFailure(Call<PharmaComplianceVerificationRes> call, Throwable t) {
                        try{
                            mView.hideProgress();
                            mView.showWarning(activity, "",activity.getResources().getString(R.string.errorTimeOut), "error");
                        }catch (Exception e){
                            e.printStackTrace();
                        }

                    }
                });
        }

}
