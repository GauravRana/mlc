package com.mylocumchoice.MyLocumChoicePharmacy.model.calender;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class CalendarEventsRes {

    @SerializedName("unseen_notifications")
    @Expose
    private boolean unseen_notifications;

    public boolean isUnseen_notifications() {
        return unseen_notifications;
    }

    public void setUnseen_notifications(boolean unseen_notifications) {
        this.unseen_notifications = unseen_notifications;
    }

    private ArrayList<CalendarEvent> calendar_events;

    public ArrayList<CalendarEvent> getCalendarEvents() { return this.calendar_events; }

    public void setCalendarEvents(ArrayList<CalendarEvent> calendar_events) { this.calendar_events = calendar_events; }

    private ArrayList<RecurringType> recurring_types;

    public ArrayList<RecurringType> getRecurringTypes() { return this.recurring_types; }

    public void setRecurringTypes(ArrayList<RecurringType> recurring_types) { this.recurring_types = recurring_types; }

    public class Event
    {
        private boolean recurring;

        private String recurring_type;

        private int id;

        public int getId() { return this.id; }

        public void setId(int id) { this.id = id; }

        private Object shift_id;

        public Object getBookingId() { return this.shift_id; }

        public void setBookingId(Object booking_id) { this.shift_id = shift_id; }

        private String title;

        public String getTitle() { return this.title; }

        public void setTitle(String title) { this.title = title; }

        private Object sub_title;

        public Object getSubTitle() { return this.sub_title; }

        public void setSubTitle(Object sub_title) { this.sub_title = sub_title; }

        private String start_time;

        public String getStartTime() { return this.start_time; }

        public void setStartTime(String start_time) { this.start_time = start_time; }

        private String end_time;

        public String getEndTime() { return this.end_time; }

        public void setEndTime(String end_time) { this.end_time = end_time; }


        public boolean isRecurring() {
            return recurring;
        }

        public void setRecurring(boolean recurring) {
            this.recurring = recurring;
        }

        public String getRecurring_type() {
            return recurring_type;
        }

        public void setRecurring_type(String recurring_type) {
            this.recurring_type = recurring_type;
        }
    }

    public class CalendarEvent
    {
        private String date;

        public String getDate() { return this.date; }

        public void setDate(String date) { this.date = date; }

        private boolean has_booking;

        public boolean getHasBooking() { return this.has_booking; }

        public void setHasBooking(boolean has_booking) { this.has_booking = has_booking; }

        private ArrayList<Event> events;

        public ArrayList<Event> getEvents() { return this.events; }

        public void setEvents(ArrayList<Event> events) { this.events = events; }
    }

    public class RecurringType {

        private int id;

        public int getId() { return this.id; }

        public void setId(int id) { this.id = id; }

        private String name;

        public String getName() { return this.name; }

        public void setName(String name) { this.name = name; }
    }

}
