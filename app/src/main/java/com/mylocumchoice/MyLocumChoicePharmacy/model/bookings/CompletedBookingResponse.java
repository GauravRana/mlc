package com.mylocumchoice.MyLocumChoicePharmacy.model.bookings;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mylocumchoice.MyLocumChoicePharmacy.model.shifts.OpenShiftResponse;

import java.util.Comparator;
import java.util.List;

public class CompletedBookingResponse {

    @SerializedName("bookings")
    @Expose
    private List<Booking> bookings = null;
    @SerializedName("next_page")
    @Expose
    private Object nextPage;
    @SerializedName("total_bookings")
    @Expose
    private Integer totalShifts;
    @SerializedName("total_pages")
    @Expose
    private Integer totalPages;
    @SerializedName("clients")
    @Expose
    private List<Client> clients = null;
    @SerializedName("paid_bookings_count")
    @Expose
    private Integer paidBookingsCount;
    @SerializedName("unpaid_bookings_count")
    @Expose
    private Integer unpaidBookingsCount;
    @SerializedName("total_amount")
    @Expose
    private String totalAmount;

    @SerializedName("filter_applied")
    @Expose
    private boolean filter_applied;

    public boolean isFilter_applied() {
        return filter_applied;
    }

    public void setFilter_applied(boolean filter_applied) {
        this.filter_applied = filter_applied;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }

    public Object getNextPage() {
        return nextPage;
    }

    public void setNextPage(Object nextPage) {
        this.nextPage = nextPage;
    }

    public Integer getTotalShifts() {
        return totalShifts;
    }

    public void setTotalShifts(Integer totalShifts) {
        this.totalShifts = totalShifts;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public List<Client> getClients() {
        return clients;
    }

    public void setClients(List<Client> clients) {
        this.clients = clients;
    }

    public Integer getPaidBookingsCount() {
        return paidBookingsCount;
    }

    public void setPaidBookingsCount(Integer paidBookingsCount) {
        this.paidBookingsCount = paidBookingsCount;
    }

    public Integer getUnpaidBookingsCount() {
        return unpaidBookingsCount;
    }

    public void setUnpaidBookingsCount(Integer unpaidBookingsCount) {
        this.unpaidBookingsCount = unpaidBookingsCount;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public class Booking {

        @SerializedName("paid")
        @Expose
        private Boolean paid;
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
        @SerializedName("total_amount")
        @Expose
        private String totalAmount;

        public Boolean getPaid() {
            return paid;
        }

        public void setPaid(Boolean paid) {
            this.paid = paid;
        }

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

        public String getTown() {
            return town;
        }

        public void setTown(String town) {
            this.town = town;
        }

        public String getTotalAmount() {
            return totalAmount;
        }

        public void setTotalAmount(String totalAmount) {
            this.totalAmount = totalAmount;
        }

    }

    public class Client {

        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("logo")
        @Expose
        private Object logo;

        public Object getLogo() {
            return logo;
        }

        public void setLogo(Object logo) {
            this.logo = logo;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

    }

    public static Comparator<CompletedBookingResponse.Client> clientComparator = new Comparator<Client>() {
        @Override
        public int compare(CompletedBookingResponse.Client o1, CompletedBookingResponse.Client o2) {
            String StudentName1 = o1.getName();
            String StudentName2 = o2.getName();

            //ascending order
            return StudentName1.compareTo(StudentName2);
        }
    };

}
