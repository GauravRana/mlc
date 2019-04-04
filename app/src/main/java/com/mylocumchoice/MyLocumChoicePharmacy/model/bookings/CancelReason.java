package com.mylocumchoice.MyLocumChoicePharmacy.model.bookings;

import java.util.List;

public class CancelReason {

    List<BookingDetailResponse.CancelReason> lv_cancelReason;

    public List<BookingDetailResponse.CancelReason> getLv_cancelReason() {
        return lv_cancelReason;
    }

    public void setLv_cancelReason(List<BookingDetailResponse.CancelReason> lv_cancelReason) {
        this.lv_cancelReason = lv_cancelReason;
    }
}
