package com.mylocumchoice.MyLocumChoicePharmacy.model.eventmodels;


import com.mylocumchoice.MyLocumChoicePharmacy.model.bookings.Booking;

import java.util.List;

public class UpcomingBookingData {

    List<Booking> al_bookings;

    public List<Booking> getAl_bookings() {
        return al_bookings;
    }

    public void setAl_bookings(List<Booking> al_bookings) {
        this.al_bookings = al_bookings;
    }
}
