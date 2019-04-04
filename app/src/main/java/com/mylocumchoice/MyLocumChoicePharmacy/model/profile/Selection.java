package com.mylocumchoice.MyLocumChoicePharmacy.model.profile;

import android.app.Activity;
import android.content.Context;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

public class Selection {
    List<PrefernceRes.SelectOption> selectOptions;
    List<PrefernceRes.Field> selectFields;
    ArrayList<PrefernceRes.SelectOption> selectOptionsList;
    private int select_id;


    public Context context;
    public Activity activity;
    int field_id;

    int id  = 0;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public List<PrefernceRes.SelectOption> getSelectOptions() {
        return selectOptions;
    }

    public List<PrefernceRes.Field> getSelectedFields() {
        return selectFields;
    }

    public void setSelectOptions(List<PrefernceRes.SelectOption> selectOptions) {
        this.selectOptions = selectOptions;

    } public void setFieldId(int id) {
        this.id = id;
    }

    public int getFieldId() {
        return id;
    }
    String type,from, title;
    PageDetail pageDetails;


    public String set() {
        return type;
    }


    public PageDetail getPageDetails(){
        return pageDetails;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle(){
        return title;
    }

    public void setContext(Context context) {
        this.context = context;
    }


    public Context getContext() {
        return context;
    }



    public void setActivity(Activity activity) {
        this.activity = activity;
    }


    public Activity getActivity() {
        return activity;
    }


    public class PageDetail{
        String title;

        public void setTitle(String title) {
            this.title = title;
        }

        public String getTitle(){
            return title;
        }
    }




    public ArrayList<PrefernceRes.SelectOption> getSelectOptionsList() {
        return selectOptionsList;
    }

    public void setSelectOptionsList(ArrayList<PrefernceRes.SelectOption> selectOptionsList) {
        this.selectOptionsList = selectOptionsList;

    }




}
