package com.mylocumchoice.MyLocumChoicePharmacy.model.shifts;

import java.util.ArrayList;

public class ShiftDetailResObj extends ShiftDetailsRes {

        private String title;

        public String getTitle() { return this.title; }

        public void setTitle(String title) { this.title = title; }

        private ArrayList<ShiftDetailsRes.ListOption> list_options;

        public ArrayList<ShiftDetailsRes.ListOption> getListOptions() { return this.list_options; }

        public void setListOptions(ArrayList<ShiftDetailsRes.ListOption> list_options) { this.list_options = list_options; }
}
