package com.mylocumchoice.MyLocumChoicePharmacy.model.eventmodels;


import com.mylocumchoice.MyLocumChoicePharmacy.model.bookings.CompletedBookingResponse;

public class CompletedBookingData {

    CompletedBookingResponse.Booking selectedBooking;

    private Integer paidBookingsCount;

    private Integer unpaidBookingsCount;

    private String totalAmount;

    public CompletedBookingResponse.Booking getSelectedBooking() {
        return selectedBooking;
    }

    public void setSelectedBooking(CompletedBookingResponse.Booking selectedBooking) {
        this.selectedBooking = selectedBooking;
    }

    public Integer getPaidBookingsCount() {
        return paidBookingsCount;
    }

    public void setPaidBookingsCount(Integer paidBookingsCount) {
        this.paidBookingsCount = paidBookingsCount;
    }

    public Integer getUnpaidBookingsCount() {
        return unpaidBookingsCount;
    }

    public void setUnpaidBookingsCount(Integer unpaidBookingsCount) {
        this.unpaidBookingsCount = unpaidBookingsCount;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }
}
