package com.mylocumchoice.MyLocumChoicePharmacy.presenter.shifts;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mylocumchoice.MyLocumChoicePharmacy.R;
import com.mylocumchoice.MyLocumChoicePharmacy.model.shifts.OpenShiftResponse;
import com.mylocumchoice.MyLocumChoicePharmacy.model.shifts.ShiftDetailResDeserializer;
import com.mylocumchoice.MyLocumChoicePharmacy.model.shifts.ShiftDetailResponse;
import com.mylocumchoice.MyLocumChoicePharmacy.model.shifts.ShiftDetailsRes;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.SharedPrefManager;
import com.mylocumchoice.MyLocumChoicePharmacy.view.base.BaseApplication;
import com.mylocumchoice.MyLocumChoicePharmacy.view.shifts.viewinterface.ShiftDetailView;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ShiftDetailsPresenterImpl implements ShiftDetailsPresenter{


    ShiftDetailView shiftDetailView;
    Activity mContext;

    public ShiftDetailsPresenterImpl(ShiftDetailView shiftDetailView,Activity mContext) {
        this.shiftDetailView=shiftDetailView;
        this.mContext=mContext;
    }

    @Override
    public void onGetShiftDetails(int id) {
        shiftDetailView.showProgress();
        BaseApplication.getRestClient().getService().getShiftDetails(SharedPrefManager.getInstance(mContext).getEmail()
                ,SharedPrefManager.getInstance(mContext).getAuthToken(),id).enqueue(new Callback<ShiftDetailsRes>() {
            @Override
            public void onResponse(Call<ShiftDetailsRes> call, Response<ShiftDetailsRes> response) {
                   shiftDetailView.hideProgress();
                   if(response.code()==200) {
                       shiftDetailView.onDetailsFetched(response.body());
                   }else if (response.code() == 401) {
                       try {
                           String errorString;
                           JSONObject jObjError = new JSONObject(response.errorBody().string());
                           errorString = jObjError.get("error").toString();
                           shiftDetailView.showWarning(mContext, "", errorString, "error");
                       } catch (Exception e) {
                           e.printStackTrace();
                       }
                   }else if (response.code() == 422) {
                       try {
                           String errorString;
                           JSONObject jObjError = new JSONObject(response.errorBody().string());
                           errorString = jObjError.get("error").toString();
                           shiftDetailView.showWarning(mContext, "", errorString, "error");
                       } catch (Exception e) {
                           e.printStackTrace();
                       }
                   }else {
                       try{
                           shiftDetailView.showWarning(mContext, "", mContext.getResources().getString(R.string.handle_strange_error), "error");
                       }catch (Exception e){
                           e.printStackTrace();
                       }
                   }
            }

            @Override
            public void onFailure(Call<ShiftDetailsRes> call, Throwable t) {
                shiftDetailView.hideProgress();
                shiftDetailView.onGettingError();
            }
        });

    }



    @Override
    public void onGetAppliedShiftDetails(int id) {
        shiftDetailView.showProgress();
        BaseApplication.getRestClient().getService().getshiftApplied(SharedPrefManager.getInstance(mContext).getEmail()
                ,SharedPrefManager.getInstance(mContext).getAuthToken(),id).enqueue(new Callback<ShiftDetailsRes>() {
            @Override
            public void onResponse(Call<ShiftDetailsRes> call, Response<ShiftDetailsRes> response) {
                shiftDetailView.hideProgress();
                if(response.code()==200) {
                    shiftDetailView.onDetailsFetched(response.body());
                }else if (response.code() == 401) {
                    try {
                        String errorString;
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        errorString = jObjError.get("error").toString();
                        shiftDetailView.showWarning(mContext, "", errorString, "error");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }else if (response.code() == 422) {
                    try {
                        String errorString;
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        errorString = jObjError.get("error").toString();
                        shiftDetailView.showWarning(mContext, "", errorString, "error");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }else {
                    try{
                        shiftDetailView.showWarning(mContext, "", mContext.getResources().getString(R.string.handle_strange_error), "error");
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ShiftDetailsRes> call, Throwable t) {
                shiftDetailView.hideProgress();
                shiftDetailView.onGettingError();
            }
        });

    }


    @Override
    public void onGetInviteShiftDetails(int id) {
        shiftDetailView.showProgress();
        BaseApplication.getRestClient().getService().getShiftInvite(SharedPrefManager.getInstance(mContext).getEmail()
                ,SharedPrefManager.getInstance(mContext).getAuthToken(),id).enqueue(new Callback<ShiftDetailsRes>() {
            @Override
            public void onResponse(Call<ShiftDetailsRes> call, Response<ShiftDetailsRes> response) {
                shiftDetailView.hideProgress();
                if(response.code()==200) {
                    shiftDetailView.onDetailsFetched(response.body());
                }else if (response.code() == 401) {
                    try {
                        String errorString;
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        errorString = jObjError.get("error").toString();
                        shiftDetailView.showWarning(mContext, "", errorString, "error");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }else if (response.code() == 422) {
                    try {
                        String errorString;
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        errorString = jObjError.get("error").toString();
                        shiftDetailView.showWarning(mContext, "", errorString, "error");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }else {
                    try{
                        shiftDetailView.showWarning(mContext, "", mContext.getResources().getString(R.string.handle_strange_error), "error");
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ShiftDetailsRes> call, Throwable t) {
                shiftDetailView.hideProgress();
                shiftDetailView.onGettingError();
            }
        });

    }

    @Override
    public void onGetShiftDetailsNotification(int id, int notification_id) {
        shiftDetailView.showProgress();
        BaseApplication.getRestClient().getService().getShiftDetailsNotification(SharedPrefManager.getInstance(mContext).getEmail()
                ,SharedPrefManager.getInstance(mContext).getAuthToken(),id,notification_id).enqueue(new Callback<ShiftDetailsRes>() {
            @Override
            public void onResponse(Call<ShiftDetailsRes> call, Response<ShiftDetailsRes> response) {
                shiftDetailView.hideProgress();
                if(response.code()==200) {
                    shiftDetailView.onDetailsFetched(response.body());
                }else if (response.code() == 401) {
                    try {
                        String errorString;
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        errorString = jObjError.get("error").toString();
                        shiftDetailView.showWarning(mContext, "", errorString, "error");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }else if (response.code() == 422) {
                    try {
                        String errorString;
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        errorString = jObjError.get("error").toString();
                        shiftDetailView.showWarning(mContext, "", errorString, "error");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }else {
                    try{
                        shiftDetailView.showWarning(mContext, "", mContext.getResources().getString(R.string.handle_strange_error), "error");
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ShiftDetailsRes> call, Throwable t) {
                shiftDetailView.hideProgress();
                shiftDetailView.onGettingError();
            }
        });
    }

    @Override
    public void onGetAppliedShiftDetailsNotification(int id, int notification_id) {
        shiftDetailView.showProgress();
        BaseApplication.getRestClient().getService().getShiftAppliedNotification(SharedPrefManager.getInstance(mContext).getEmail()
                ,SharedPrefManager.getInstance(mContext).getAuthToken(),id,notification_id).enqueue(new Callback<ShiftDetailsRes>() {
            @Override
            public void onResponse(Call<ShiftDetailsRes> call, Response<ShiftDetailsRes> response) {
                shiftDetailView.hideProgress();
                if(response.code()==200) {
                    shiftDetailView.onDetailsFetched(response.body());
                }else if (response.code() == 401) {
                    try {
                        String errorString;
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        errorString = jObjError.get("error").toString();
                        shiftDetailView.showWarning(mContext, "", errorString, "error");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }else if (response.code() == 422) {
                    try {
                        String errorString;
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        errorString = jObjError.get("error").toString();
                        shiftDetailView.showWarning(mContext, "", errorString, "error");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }else {
                    try{
                        shiftDetailView.showWarning(mContext, "", mContext.getResources().getString(R.string.handle_strange_error), "error");
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ShiftDetailsRes> call, Throwable t) {
                shiftDetailView.hideProgress();
                shiftDetailView.onGettingError();
            }
        });
    }

    @Override
    public void onGetInviteShiftDetailsNotification(int id, int notification_id) {
        shiftDetailView.showProgress();
        BaseApplication.getRestClient().getService().getShiftInviteNotification(SharedPrefManager.getInstance(mContext).getEmail()
                ,SharedPrefManager.getInstance(mContext).getAuthToken(),id,notification_id).enqueue(new Callback<ShiftDetailsRes>() {
            @Override
            public void onResponse(Call<ShiftDetailsRes> call, Response<ShiftDetailsRes> response) {
                shiftDetailView.hideProgress();
                if(response.code()==200) {
                    shiftDetailView.onDetailsFetched(response.body());
                }else if (response.code() == 401) {
                    try {
                        String errorString;
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        errorString = jObjError.get("error").toString();
                        shiftDetailView.showWarning(mContext, "", errorString, "error");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }else if (response.code() == 422) {
                    try {
                        String errorString;
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        errorString = jObjError.get("error").toString();
                        shiftDetailView.showWarning(mContext, "", errorString, "error");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }else {
                    try{
                        shiftDetailView.showWarning(mContext, "", mContext.getResources().getString(R.string.handle_strange_error), "error");
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ShiftDetailsRes> call, Throwable t) {
                shiftDetailView.hideProgress();
                shiftDetailView.onGettingError();
            }
        });
    }


}
