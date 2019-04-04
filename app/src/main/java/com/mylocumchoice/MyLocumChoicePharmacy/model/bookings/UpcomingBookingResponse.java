package com.mylocumchoice.MyLocumChoicePharmacy.model.bookings;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UpcomingBookingResponse {

    @SerializedName("bookings")
    @Expose
    private List<Booking> bookings = null;
    @SerializedName("next_page")
    @Expose
    private Object nextPage;
    @SerializedName("total_pages")
    @Expose
    private Integer totalPages;
    @SerializedName("total_bookings")
    @Expose
    private Integer totalBookings;
    @SerializedName("unseen_notifications")
    @Expose
    private boolean unseen_notifications;

    public boolean isUnseen_notifications() {
        return unseen_notifications;
    }

    public void setUnseen_notifications(boolean unseen_notifications) {
        this.unseen_notifications = unseen_notifications;
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

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public Integer getTotalBookings() {
        return totalBookings;
    }

    public void setTotalBookings(Integer totalBookings) {
        this.totalBookings = totalBookings;
    }
}
