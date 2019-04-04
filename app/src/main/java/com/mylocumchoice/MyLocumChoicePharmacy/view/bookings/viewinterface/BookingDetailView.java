package com.mylocumchoice.MyLocumChoicePharmacy.view.bookings.viewinterface;

import com.mylocumchoice.MyLocumChoicePharmacy.model.bookings.BookingDetailResponse;
import com.mylocumchoice.MyLocumChoicePharmacy.view.base.BaseView;

public interface BookingDetailView extends BaseView {

    void onDetailsFetched(BookingDetailResponse response);
    void onGettingError();
}
