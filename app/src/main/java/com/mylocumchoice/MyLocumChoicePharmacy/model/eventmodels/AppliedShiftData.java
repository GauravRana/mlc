package com.mylocumchoice.MyLocumChoicePharmacy.model.eventmodels;


import com.mylocumchoice.MyLocumChoicePharmacy.model.bookings.AppliedShiftResponse;

import java.util.List;

public class AppliedShiftData {

    List<AppliedShiftResponse.Shift> al_shifts;

    public List<AppliedShiftResponse.Shift> getAl_shifts() {
        return al_shifts;
    }

    public void setAl_shifts(List<AppliedShiftResponse.Shift> al_shifts) {
        this.al_shifts = al_shifts;
    }
}
