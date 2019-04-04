package com.mylocumchoice.MyLocumChoicePharmacy.model.profile;

import android.app.Activity;
import android.content.Context;

import java.util.ArrayList;

public class MultiSelectData {

    private String from;
    private String title;
    private String placeHolder;
    private int position;
    private ArrayList<String> valueArrayList;

    private String type;

    private String optionalText;

    private String text_response;

    private int field_id;

    private int option_id;

    private int select_id;

    public Context context;

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public Activity activity;

    ArrayList<PrefernceRes.SelectOption> selectOptionsList;


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


    public void setContext(Context context) {
        this.context = context;
    }


    public Context getContext() {
        return context;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ArrayList<PrefernceRes.SelectOption> getSelectOptionsList() {
        return selectOptionsList;
    }

    public void setSelectOptionsList(ArrayList<PrefernceRes.SelectOption> selectOptionsList) {
        this.selectOptionsList = selectOptionsList;

    }

    public String getText_response() {
        return text_response;
    }

    public void setText_response(String text_response) {
        this.text_response = text_response;
    }

    public String getOptionalText() {
        return optionalText;
    }

    public void setOptionalText(String optionalText) {
        this.optionalText = optionalText;
    }
}
