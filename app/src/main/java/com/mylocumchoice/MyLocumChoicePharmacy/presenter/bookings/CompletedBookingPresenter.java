package com.mylocumchoice.MyLocumChoicePharmacy.presenter.bookings;

import com.google.gson.JsonObject;

public interface CompletedBookingPresenter {

    void onGetCompletedBookings(JsonObject jsonObject,int pageNum);
}
