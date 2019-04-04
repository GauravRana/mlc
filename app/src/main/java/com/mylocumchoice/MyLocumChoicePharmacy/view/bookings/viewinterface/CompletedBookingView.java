package com.mylocumchoice.MyLocumChoicePharmacy.view.bookings.viewinterface;

import com.mylocumchoice.MyLocumChoicePharmacy.model.bookings.CompletedBookingResponse;
import com.mylocumchoice.MyLocumChoicePharmacy.view.base.BaseView;

public interface CompletedBookingView extends BaseView{

    void onCompletedBookingSuccess(CompletedBookingResponse completedBookingResponse);
    void onCompletedBookingFailure();
}
