package com.mylocumchoice.MyLocumChoicePharmacy.model.shifts;

import java.util.ArrayList;

public class OpenShift {
    private String start_date;

    public String getStartDate() { return this.start_date; }

    public void setStartDate(String start_date) { this.start_date = start_date; }

    private String end_date;

    public String getEndDate() { return this.end_date; }

    public void setEndDate(String end_date) { this.end_date = end_date; }

    private ArrayList<Integer> pace_ids;

    public ArrayList<Integer> getPaceIds() { return this.pace_ids; }

    public void setPaceIds(ArrayList<Integer> pace_ids) { this.pace_ids = pace_ids; }

    private ArrayList<Integer> county_ids;

    public ArrayList<Integer> getCountyIds() { return this.county_ids; }

    public void setCountyIds(ArrayList<Integer> county_ids) { this.county_ids = county_ids; }

    private double longitude;

    public double getLongitude() { return this.longitude; }

    public void setLongitude(double longitude) { this.longitude = longitude; }

    private double latitude;

    public double getLatitude() { return this.latitude; }

    public void setLatitude(double latitude) { this.latitude = latitude; }

    private int distance_id;

    public int getDistanceId() { return this.distance_id; }

    public void setDistanceId(int distance_id) { this.distance_id = distance_id; }

    private Boolean emergency;

    public Boolean getEmergency() { return this.emergency; }

    public void setEmergency(Boolean emergency) { this.emergency = emergency; }

    private double rate;

    public double getRate() { return this.rate; }

    public void setRate(double rate) { this.rate = rate; }

    public boolean getBest_match() {
        return best_match;
    }

    public void setBest_match(boolean best_match) {
        this.best_match = best_match;
    }

    private boolean best_match;

    private String sort_by;

    public String getSortBy() { return this.sort_by; }

    public void setSortBy(String sort_by) { this.sort_by = sort_by; }

    public int nextPage;

    public int getNextPage() {
        return nextPage;
    }

    public void setNextPage(int nextPage) {
        this.nextPage = nextPage;
    }

    @Override
    public String toString() {
        return "Start Date -" + getStartDate() + "\n"
                + "End Date -" + getEndDate() + "\n"
                + "pace id -" + getPaceIds() + "\n"
                + "county id -" + getCountyIds() + "\n"
                + "Longitude -" + getLongitude() + "\n"
                + "Latitude -" + getLatitude() + "\n"
                + "distance id -" + getDistanceId() + "\n"
                + "Emergency -" + getEmergency() + "\n"
                + "Rate -" + getRate() + "\n"
                + "Sort by -" + getSortBy() + "\n";
    }
}
