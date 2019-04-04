package com.mylocumchoice.MyLocumChoicePharmacy.model.calender;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class EventListResponse {

    @SerializedName("events")
    @Expose
    private List<EventList> events = null;

    public List<EventList> getEvents() {
        return events;
    }

    public void setEvents(List<EventList> events) {
        this.events = events;
    }
}
