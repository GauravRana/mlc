package com.mylocumchoice.MyLocumChoicePharmacy.utils;

import retrofit2.Response;

public interface ApiResponseListener {

    public void onGetResponse(Response<Void> response);
}
