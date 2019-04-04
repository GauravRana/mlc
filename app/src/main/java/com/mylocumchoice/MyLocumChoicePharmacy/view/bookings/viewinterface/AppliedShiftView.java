package com.mylocumchoice.MyLocumChoicePharmacy.view.bookings.viewinterface;

import com.mylocumchoice.MyLocumChoicePharmacy.model.bookings.AppliedShiftResponse;
import com.mylocumchoice.MyLocumChoicePharmacy.view.base.BaseView;

public interface AppliedShiftView extends BaseView {

    void onPendingListFetch(AppliedShiftResponse appliedShiftResponse);
    void onPendingListError();

    void onDeclinedListFetch(AppliedShiftResponse appliedShiftResponse);
    void onDeclinedListError();
}
