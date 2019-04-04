package com.mylocumchoice.MyLocumChoicePharmacy.model.profile;

import java.util.ArrayList;

public class PharmacySysUpdateReq {
    private ArrayList<Integer> selected_system_ids;
    public ArrayList<Integer> getSelectedSystemIds() { return this.selected_system_ids; }
    public void setSelectedSystemIds(ArrayList<Integer> selected_system_ids) { this.selected_system_ids = selected_system_ids; }
}
