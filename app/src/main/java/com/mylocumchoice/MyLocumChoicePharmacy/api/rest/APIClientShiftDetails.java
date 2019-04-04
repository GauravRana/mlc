package com.mylocumchoice.MyLocumChoicePharmacy.api.rest;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mylocumchoice.MyLocumChoicePharmacy.api.APIInterface;
import com.mylocumchoice.MyLocumChoicePharmacy.model.shifts.ShiftDetailResDeserializer;
import com.mylocumchoice.MyLocumChoicePharmacy.model.shifts.ShiftDetailsRes;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.GlobalConstants;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClientShiftDetails {

    private static final String BASE_URL = GlobalConstants.BaseAPI.BASE_URL;
    private APIInterface apiService;
    private static Retrofit retrofit = null;
    Gson userDeserializer = new GsonBuilder().setLenient().registerTypeAdapter(ShiftDetailsRes.class, new ShiftDetailResDeserializer()).create();


    public APIClientShiftDetails()
    {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();


        retrofit = new Retrofit.Builder()
                .baseUrl(GlobalConstants.BaseAPI.BASE_URL+"/")
                .addConverterFactory(GsonConverterFactory.create(userDeserializer))
                .build();

        apiService = retrofit.create(APIInterface.class);
    }

    public APIInterface getService()
    {
        return apiService;
    }

}
