package com.mylocumchoice.MyLocumChoicePharmacy.presenter.bookings;

import com.google.gson.JsonObject;

public interface CancelBookingPresenter {

    void onBookingCancel(int id,JsonObject jsonObject);
}
