package com.mylocumchoice.MyLocumChoicePharmacy.presenter.bookings;

import com.google.gson.JsonObject;

public interface AppliedShiftsPresenter {

    void getPendingBookings(JsonObject jsonObject,int pageNum);
    void getDeclinedBookings(JsonObject jsonObject,int pageNum);

}
