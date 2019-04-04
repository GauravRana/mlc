package com.mylocumchoice.MyLocumChoicePharmacy.model.shifts;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ShiftDetailsRes {

    private ShiftDetails shift_details;

    public ShiftDetails getShiftDetails() { return this.shift_details; }

    public void setShiftDetails(ShiftDetails shift_details) { this.shift_details = shift_details; }

    public class Detail
    {
        private String type;

        public String getType() { return this.type; }

        public void setType(String type) { this.type = type; }

        private Object ShiftDetailResObj;

        public Object getShiftDetailResObj() { return this.ShiftDetailResObj; }

        public void setShiftDetailResObj(Object ShiftDetailResObj) { this.ShiftDetailResObj = ShiftDetailResObj; }

        public Object value1;

        public Object getValue1() { return this.value1; }

        public void setValue1(Object value1) { this.value1 = value1; }

        private String value2;

        public String getValue2() { return this.value2; }

        public void setValue2(String value2) { this.value2 = value2; }

        private Object image_url;

        public Object getImageUrl() { return this.image_url; }

        public void setImageUrl(Object image_url) { this.image_url = image_url; }


        private ArrayList<List> lists;

        public ArrayList<List> getLists() { return this.lists; }

        public void setLists(ArrayList<List> lists) { this.lists = lists; }
    }

    public class Section
    {
        private String title;

        public String getTitle() { return this.title; }

        public void setTitle(String title) { this.title = title; }

        private ArrayList<Detail> details;

        public ArrayList<Detail> getDetails() { return this.details; }

        public void setDetails(ArrayList<Detail> details) { this.details = details; }
    }

    public class OtherOption
    {
        private String type;

        public String getType() { return this.type; }

        public void setType(String type) { this.type = type; }

        private String title;

        public String getTitle() { return this.title; }

        public void setTitle(String title) { this.title = title; }

        private Object value;

        public Object getValue() { return this.value; }

        public void setValue(Object value) { this.value = value; }
    }

    public class ShiftDetails
    {
        private int id;

        public int getId() { return this.id; }

        public void setId(int id) { this.id = id; }

        private boolean emergency;

        public boolean getEmergency() { return this.emergency; }

        public void setEmergency(boolean emergency) { this.emergency = emergency; }

        private Object rate_value;

        public Object getRate_value() {
            return rate_value;
        }

        public void setRate_value(Object rate_value) {
            this.rate_value = rate_value;
        }

        private ArrayList<Section> sections;

        public ArrayList<Section> getSections() { return this.sections; }

        public void setSections(ArrayList<Section> sections) { this.sections = sections; }

        private ArrayList<OtherOption> other_options;

        public ArrayList<OtherOption> getOtherOptions() { return this.other_options; }

        public void setOtherOptions(ArrayList<OtherOption> other_options) { this.other_options = other_options; }

        private boolean accept;

        public boolean getAccept() { return this.accept; }

        public void setAccept(boolean accept) { this.accept = accept; }

        private boolean apply;

        public boolean getApply() { return this.apply; }

        public void setApply(boolean apply) { this.apply = apply; }


    }

    public class ListOption
    {
        private String name;

        public String getName() { return this.name; }

        public void setName(String name) { this.name = name; }

        private ArrayList<Object> options;

        public ArrayList<Object> getOptions() { return this.options; }

        public void setOptions(ArrayList<Object> options) { this.options = options; }
    }


    public class Value1
    {
        private String title;

        public String getTitle() { return this.title; }

        public void setTitle(String title) { this.title = title; }

        private ArrayList<ListOption> list_options;

        public ArrayList<ListOption> getListOptions() { return this.list_options; }

        public void setListOptions(ArrayList<ListOption> list_options) { this.list_options = list_options; }
    }




    public class List
    {
        private String title;

        public String getTitle() { return this.title; }

        public void setTitle(String title) { this.title = title; }

        private ArrayList<ListOption> list_options;

        public ArrayList<ListOption> getListOptions() { return this.list_options; }

        public void setListOptions(ArrayList<ListOption> list_options) { this.list_options = list_options; }
    }


    public class Options
    {

    }


}
