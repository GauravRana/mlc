package com.mylocumchoice.MyLocumChoicePharmacy.api.rest;


import com.mylocumchoice.MyLocumChoicePharmacy.api.APIInterface;
import com.mylocumchoice.MyLocumChoicePharmacy.model.shifts.RouteList;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.GlobalConstants;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RestClient
{
    private APIInterface apiService;
    private static Retrofit retrofit = null;

    public RestClient()
    {
        Gson gson = new GsonBuilder()
            .registerTypeAdapterFactory(new ItemTypeAdapterFactory())
            .create();

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).connectTimeout(5, TimeUnit.MINUTES)
                .readTimeout(10, TimeUnit.MINUTES)
                .writeTimeout(10, TimeUnit.MINUTES)
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl(GlobalConstants.BaseAPI.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build();

        apiService = retrofit.create(APIInterface.class);
    }



    public APIInterface getService()
    {
        return apiService;
    }
}
