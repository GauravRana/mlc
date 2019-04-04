package com.mylocumchoice.MyLocumChoicePharmacy.model.shifts;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Comparator;

public class OpenShiftResponse {

    private ArrayList<Shift> shifts;

    public ArrayList<Shift> getShifts() { return this.shifts; }

    public void setShifts(ArrayList<Shift> shifts) { this.shifts = shifts; }

    private int total_pages;

    public int getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(int total_pages) {
        this.total_pages = total_pages;
    }

    private boolean filter_applied;

    public boolean isFilter_applied() {
        return filter_applied;
    }

    public boolean unseen_notifications;

    public boolean isUnseen_notifications() {
        return unseen_notifications;
    }

    public void setUnseen_notifications(boolean unseen_notifications) {
        this.unseen_notifications = unseen_notifications;
    }

    public void setFilter_applied(boolean filter_applied) {
        this.filter_applied = filter_applied;
    }

    private Object next_page;

    public Object getNextPage() { return this.next_page; }

    public void setNextPage(Object next_page) { this.next_page = next_page; }


    private Object address_text;

    public Object getAddress_text() {
        return address_text;
    }

    public void setAddress_text(Object address_text) {
        this.address_text = address_text;
    }

    private Object address_latitude;

    public Object getAddressLatitude() { return this.address_latitude; }

    public void setAddressLatitude(Object address_latitude) { this.address_latitude = address_latitude; }

    private Object address_longitude;

    public Object getAddressLongitude() { return this.address_longitude; }

    public void setAddressLongitude(Object address_longitude) { this.address_longitude = address_longitude; }

    private int total_shifts;

    public int getTotalShifts() { return this.total_shifts; }

    public void setTotalShifts(int total_shifts) { this.total_shifts = total_shifts; }

    private ArrayList<Pace> paces;

    public ArrayList<Pace> getPaces() { return this.paces; }

    public void setPaces(ArrayList<Pace> paces) { this.paces = paces; }

    private ArrayList<County> counties;

    public ArrayList<County> getCounties() { return this.counties; }

    public void setCounties(ArrayList<County> counties) { this.counties = counties; }

    private ArrayList<Distance> distances;

    public ArrayList<Distance> getDistances() { return this.distances; }

    public void setDistances(ArrayList<Distance> distances) { this.distances = distances; }

    public class Shift
    {
        private int id;

        public int getId() { return this.id; }

        public void setId(int id) { this.id = id; }

        private boolean best_match;

        public boolean isBest_match() {
            return best_match;
        }

        public void setBest_match(boolean best_match) {
            this.best_match = best_match;
        }

        private boolean emergency;

        public boolean getEmergency() { return this.emergency; }

        public void setEmergency(boolean emergency) { this.emergency = emergency; }

        private String name;

        public String getName() { return this.name; }

        public void setName(String name) { this.name = name; }

        private Object logo;

        public Object getLogo() { return this.logo; }

        public void setLogo(Object logo) { this.logo = logo; }

        private String rate;

        public String getRate() { return this.rate; }

        public void setRate(String rate) { this.rate = rate; }

        private String date;

        public String getDate() { return this.date; }

        public void setDate(String date) { this.date = date; }

        private String time_range;

        public String getTimeRange() { return this.time_range; }

        public void setTimeRange(String time_range) { this.time_range = time_range; }

        private String pace;

        public String getPace() { return this.pace; }

        public void setPace(String pace) { this.pace = pace; }

        private String town;

        public String getCounty() { return this.town; }

        public void setCounty(String town) { this.town = town; }

        private Object distance;

        public Object getDistance() { return this.distance; }

        public void setDistance(Object distance) { this.distance = distance; }
    }

    public class Pace
    {
        private int id;

        public int getId() { return this.id; }

        public void setId(int id) { this.id = id; }

        private String name;

        public String getName() { return this.name; }

        public void setName(String name) { this.name = name; }

        private boolean isSelected;

        public boolean isSelected() {
            return isSelected;
        }

        public void setSelected(boolean selected) {
            isSelected = selected;
        }
    }

    public class County
    {
        private int id;

        public int getId() { return this.id; }

        public void setId(int id) { this.id = id; }

        private String name;

        public String getName() { return this.name; }

        public void setName(String name) { this.name = name; }

        private boolean selected;

        public boolean isSelected() {
            return selected;
        }

        public void setSelected(boolean selected) {
            this.selected = selected;
        }


    }

    public class Distance
    {
        private int id;

        public int getId() { return this.id; }

        public void setId(int id) { this.id = id; }

        private String name;

        public String getName() { return this.name; }

        public void setName(String name) { this.name = name; }
    }

    public static Comparator<County> countyNameComparator = new Comparator<County>() {
        @Override
        public int compare(County o1, County o2) {
            String StudentName1 = o1.getName();
            String StudentName2 = o2.getName();

            //ascending order
            return StudentName1.compareTo(StudentName2);
        }
    };

}
