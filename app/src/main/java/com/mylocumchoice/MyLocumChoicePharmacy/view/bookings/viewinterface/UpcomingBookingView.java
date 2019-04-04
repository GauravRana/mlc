package com.mylocumchoice.MyLocumChoicePharmacy.view.bookings.viewinterface;

import com.mylocumchoice.MyLocumChoicePharmacy.model.bookings.UpcomingBookingResponse;
import com.mylocumchoice.MyLocumChoicePharmacy.view.base.BaseView;

public interface UpcomingBookingView extends BaseView{

    void onBookingListFetch(UpcomingBookingResponse upcomingBookingResponse);
    void onBookingListError();
}
