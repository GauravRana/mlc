package com.mylocumchoice.MyLocumChoicePharmacy.presenter.bookings;

import com.google.gson.JsonObject;

public interface UpcomingBookingPresenter {

    void getUpcomingBookings(JsonObject jsonObject,int pageNum);
}
