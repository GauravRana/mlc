package com.mylocumchoice.MyLocumChoicePharmacy.model.profile;

import java.util.ArrayList;

public class SingleSelectData {
    private String from;
    private String title;
    private String placeHolder;
    private int position;
    private ArrayList<String> valueArrayList;

    private int field_id;

    private int option_id;

    private int select_id;


    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPlaceHolder() {
        return placeHolder;
    }

    public void setPlaceHolder(String placeHolder) {
        this.placeHolder = placeHolder;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public ArrayList<String> getValueArrayList() {
        return valueArrayList;
    }

    public void setValueArrayList(ArrayList<String> valueArrayList) {
        this.valueArrayList = valueArrayList;
    }


    public int getFieldID() {
        return field_id;
    }

    public void setFieldID(int field_id) {
        this.field_id = field_id;
    }


    public int getOptionID() {
        return option_id;
    }

    public void setOptionID(int option_id) {
        this.option_id = option_id;
    }

    public void setSelectID(int select_id) {
        this.select_id = select_id;
    }

    public int getSelectID() {
        return select_id;
    }
}
