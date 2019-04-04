package com.mylocumchoice.MyLocumChoicePharmacy.view.bookings.viewinterface;

import com.mylocumchoice.MyLocumChoicePharmacy.model.bookings.CancelBookingResponse;
import com.mylocumchoice.MyLocumChoicePharmacy.view.base.BaseView;

public interface CancelBookingView extends BaseView{

    void onCancelledSuccess(CancelBookingResponse response);
    void onCancelledFailed();
}
