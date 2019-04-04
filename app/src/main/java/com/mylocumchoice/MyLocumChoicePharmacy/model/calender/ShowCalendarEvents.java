package com.mylocumchoice.MyLocumChoicePharmacy.model.calender;

import android.content.Context;

import java.util.List;

public class ShowCalendarEvents {

    private String date;
    private int id;
    private String title;
    private Object sub_title;
    private String start_time;
    private String end_time;

    private boolean recurring;

    private String recurringType;



    private Context mcontext;
    private List<CalendarEventsRes.Event> lv_events;
    private List<CalendarEventsRes.CalendarEvent> lv_calendar;


    public List<CalendarEventsRes.CalendarEvent> getLv_calendar() {
        return lv_calendar;
    }

    public void setLv_calendar(List<CalendarEventsRes.CalendarEvent> lv_calendar) {
        this.lv_calendar = lv_calendar;
    }

    public List<CalendarEventsRes.Event> getLv_events() {
        return lv_events;
    }

    public void setLv_events(List<CalendarEventsRes.Event> lv_events) {
        this.lv_events = lv_events;
    }

    public Context getMcontext() {
        return mcontext;
    }

    public void setMcontext(Context mcontext) {
        this.mcontext = mcontext;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Object getSub_title() {
        return sub_title;
    }

    public void setSub_title(Object sub_title) {
        this.sub_title = sub_title;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public boolean isRecurring() {
        return recurring;
    }

    public void setRecurring(boolean recurring) {
        this.recurring = recurring;
    }


    public String getRecurringType() {
        return recurringType;
    }

    public void setRecurringType(String recurringType) {
        this.recurringType = recurringType;
    }
}
