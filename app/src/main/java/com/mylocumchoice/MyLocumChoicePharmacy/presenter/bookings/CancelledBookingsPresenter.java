package com.mylocumchoice.MyLocumChoicePharmacy.presenter.bookings;

import com.google.gson.JsonObject;

public interface CancelledBookingsPresenter {

    void onGetBookings(JsonObject jsonObject,int pageNum);
}
