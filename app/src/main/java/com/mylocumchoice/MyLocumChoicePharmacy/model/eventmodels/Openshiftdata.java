package com.mylocumchoice.MyLocumChoicePharmacy.model.eventmodels;

import com.mylocumchoice.MyLocumChoicePharmacy.model.shifts.OpenShiftResponse;

import java.util.ArrayList;

public class Openshiftdata {

    ArrayList<OpenShiftResponse.Shift> al_shifts;

    public ArrayList<OpenShiftResponse.Shift> getAl_shifts() {
        return al_shifts;
    }

    public void setAl_shifts(ArrayList<OpenShiftResponse.Shift> al_shifts) {
        this.al_shifts = al_shifts;
    }
}
