package com.mylocumchoice.MyLocumChoicePharmacy.model.bookings;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AppliedShiftResponse {

    @SerializedName("shifts")
    @Expose
    private List<Shift> shifts = null;
    @SerializedName("next_page")
    @Expose
    private Object nextPage;
    @SerializedName("total_pages")
    @Expose
    private Integer totalPages;

    @SerializedName("total_shifts")
    @Expose
    private Integer total_shifts;

    public Integer getTotalShifts() {
        return this.total_shifts;
    }

    public void setTotalShifts(Integer total_shifts) {
        this.total_shifts = total_shifts;
    }

    public List<Shift> getShifts() {
        return shifts;
    }

    public void setShifts(List<Shift> shifts) {
        this.shifts = shifts;
    }

    public Object getNextPage() {
        return nextPage;
    }

    public void setNextPage(Object nextPage) {
        this.nextPage = nextPage;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }



    public class Shift {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("logo")
        @Expose
        private Object logo;
        @SerializedName("rate")
        @Expose
        private String rate;
        @SerializedName("date")
        @Expose
        private String date;
        @SerializedName("time_range")
        @Expose
        private String timeRange;
        @SerializedName("town")
        @Expose
        private String town;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Object getLogo() {
            return logo;
        }

        public void setLogo(Object logo) {
            this.logo = logo;
        }

        public String getRate() {
            return rate;
        }

        public void setRate(String rate) {
            this.rate = rate;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getTimeRange() {
            return timeRange;
        }

        public void setTimeRange(String timeRange) {
            this.timeRange = timeRange;
        }

        public String getCounty() {
            return town;
        }

        public void setCounty(String county) {
            this.town = town;
        }
    }
}
