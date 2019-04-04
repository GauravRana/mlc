package com.mylocumchoice.MyLocumChoicePharmacy.presenter.profile;

import android.app.Activity;
import android.content.Context;

import com.mylocumchoice.MyLocumChoicePharmacy.R;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.SharedPrefManager;
import com.mylocumchoice.MyLocumChoicePharmacy.view.base.BaseApplication;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddReferencePresenter implements AddReferenceVP.Presenter{

    private AddReferenceVP.View mView;
    private Activity activity;

    public AddReferencePresenter(AddReferenceVP.View view, Activity activity){
        this.mView = view;
        this.activity = activity;
    }

    @Override
    public void addReference(Context context
            , RequestBody rName
            , RequestBody rTextEmail
            , RequestBody rJob
            , RequestBody rCompany
            , MultipartBody.Part doc) {

        mView.showProgress();
        BaseApplication.getRestClient().getService().addLocumReference(
                SharedPrefManager.getInstance(context).getEmail()
                ,SharedPrefManager.getInstance(context).getAuthToken()
            ,rName, rTextEmail, rJob, rCompany, doc).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                try{
                    mView.addReferenceSuccess(response);
                    mView.hideProgress();
                }catch (Exception e){
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                try {
                    mView.hideProgress();
                    mView.showWarning(activity, "",activity.getResources().getString(R.string.errorTimeOut), "error");
                }catch (Exception e){

                }
            }
        });
    }

    @Override
    public void addReferenceId(Context context
             ,String referId
            , RequestBody rName
            , RequestBody rTextEmail
            , RequestBody rJob
            , RequestBody rCompany
            , MultipartBody.Part doc) {

        mView.showProgress();
        BaseApplication.getRestClient().getService().addLocumReferenceID(
                 referId
                ,SharedPrefManager.getInstance(context).getEmail()
                ,SharedPrefManager.getInstance(context).getAuthToken()
                ,rName, rTextEmail, rJob, rCompany, doc).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                try{
                    mView.addReferenceIDSuccess(response);
                    mView.hideProgress();
                }catch (Exception e){
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
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
    public void addReferenceWithoutDoc(Context context
            , String referId
            , RequestBody rName
            , RequestBody rTextEmail
            , RequestBody rJob
            , RequestBody rCompany) {

        mView.showProgress();
        BaseApplication.getRestClient().getService().addLocumReferenceWithoutDoc(
                 referId
                ,SharedPrefManager.getInstance(context).getEmail()
                ,SharedPrefManager.getInstance(context).getAuthToken()
                ,rName, rTextEmail, rJob, rCompany).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                try{
                    mView.addReferenceWithoutDocSuccess(response);
                    mView.hideProgress();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                try {
                    mView.hideProgress();
                    mView.showWarning(activity, "",activity.getResources().getString(R.string.errorTimeOut), "error");
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        });
    }
}
