package com.mylocumchoice.MyLocumChoicePharmacy.model.calender;

public class EventModel {

    String startTime,endTime,title,desc;

    public EventModel(String startTime, String endTime,String title, String desc) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.title = title;
        this.desc = desc;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
