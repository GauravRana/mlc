package com.mylocumchoice.MyLocumChoicePharmacy.model.bookings;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BookingDetailResponse {


    @SerializedName("shift_details")
    @Expose
    private ShiftDetails shiftDetails;

    @SerializedName("paid_bookings_count")
    @Expose
    private Integer paidBookingsCount;
    @SerializedName("unpaid_bookings_count")
    @Expose
    private Integer unpaidBookingsCount;

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

    @SerializedName("total_amount")
    @Expose

    private String totalAmount;

    public ShiftDetails getShiftDetails() {
        return shiftDetails;
    }

    public void setShiftDetails(ShiftDetails shiftDetails) {
        this.shiftDetails = shiftDetails;
    }

    public class ShiftDetails {

        @SerializedName("id")
        @Expose
        private Integer id;

        @SerializedName("rated")
        @Expose
        private Object rated;

        public Object getRated() {
            return rated;
        }

        public void setRated(Object rated) {
            this.rated = rated;
        }

        @SerializedName("sections")
        @Expose
        private List<Section> sections = null;
        @SerializedName("other_options")
        @Expose
        private List<OtherOption> otherOptions = null;
        @SerializedName("can_cancel")
        @Expose
        private Boolean canCancel;

        @SerializedName("payment_status")
        @Expose
        private String paymentStatus;
        @SerializedName("paid")
        @Expose
        private Boolean paid;
        @SerializedName("unpaid_hours")
        @Expose
        private Object unpaidHours;
        @SerializedName("extra_hours")
        @Expose
        private Object extraHours;
        @SerializedName("duration")
        @Expose
        private Object duration;
        @SerializedName("other_payment")
        @Expose
        private Object otherPayment;
        @SerializedName("total_hours")
        @Expose
        private Object totalHours;
        @SerializedName("total_amount")
        @Expose
        private Object totalAmount;

        @SerializedName("total_booking_amount")
        @Expose
        private Object total_booking_amount;

        public Object getTotal_booking_amount() {
            return total_booking_amount;
        }

        public void setTotal_booking_amount(Object total_booking_amount) {
            this.total_booking_amount = total_booking_amount;
        }

        @SerializedName("rate")
        @Expose
        private Object rate;

        @SerializedName("cancelled_by")
        @Expose
        private String cancelledBy;
        @SerializedName("cancelled_on")
        @Expose
        private String cancelledOn;
        @SerializedName("reason")
        @Expose
        private String reason;

        @SerializedName("cancel_reasons")
        @Expose
        private List<CancelReason> cancelReasons = null;

        private String address_longitude;

        public String getAddressLongitude() { return this.address_longitude; }

        public void setAddressLongitude(String address_longitude) { this.address_longitude = address_longitude; }

        private String address_latitude;

        public String getAddressLatitude() { return this.address_latitude; }

        public void setAddressLatitude(String address_latitude) { this.address_latitude = address_latitude; }


        public Object getRate() {
            return rate;
        }

        public void setRate(Object rate) {
            this.rate = rate;
        }

        public Object getDuration() {
            return duration;
        }

        public void setDuration(Object duration) {
            this.duration = duration;
        }

        public List<CancelReason> getCancelReasons() {
            return cancelReasons;
        }

        public void setCancelReasons(List<CancelReason> cancelReasons) {
            this.cancelReasons = cancelReasons;
        }

        public String getCancelledBy() {
            return cancelledBy;
        }

        public void setCancelledBy(String cancelledBy) {
            this.cancelledBy = cancelledBy;
        }

        public String getCancelledOn() {
            return cancelledOn;
        }

        public void setCancelledOn(String cancelledOn) {
            this.cancelledOn = cancelledOn;
        }

        public String getReason() {
            return reason;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }

        public String getPaymentStatus() {
            return paymentStatus;
        }

        public void setPaymentStatus(String paymentStatus) {
            this.paymentStatus = paymentStatus;
        }

        public Boolean getPaid() {
            return paid;
        }

        public void setPaid(Boolean paid) {
            this.paid = paid;
        }

        public Object getUnpaidHours() {
            return unpaidHours;
        }

        public void setUnpaidHours(Object unpaidHours) {
            this.unpaidHours = unpaidHours;
        }

        public Object getExtraHours() {
            return extraHours;
        }

        public void setExtraHours(Object extraHours) {
            this.extraHours = extraHours;
        }

        public Object getOtherPayment() {
            return otherPayment;
        }

        public void setOtherPayment(Object otherPayment) {
            this.otherPayment = otherPayment;
        }

        public Object getTotalHours() {
            return totalHours;
        }

        public void setTotalHours(Object totalHours) {
            this.totalHours = totalHours;
        }

        public Object getTotalAmount() {
            return totalAmount;
        }

        public void setTotalAmount(Object totalAmount) {
            this.totalAmount = totalAmount;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public List<Section> getSections() {
            return sections;
        }

        public void setSections(List<Section> sections) {
            this.sections = sections;
        }

        public List<OtherOption> getOtherOptions() {
            return otherOptions;
        }

        public void setOtherOptions(List<OtherOption> otherOptions) {
            this.otherOptions = otherOptions;
        }

        public Boolean getCanCancel() {
            return canCancel;
        }

        public void setCanCancel(Boolean canCancel) {
            this.canCancel = canCancel;
        }

    }

    public class CancelReason {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("input_required")
        @Expose
        private Boolean inputRequired;

        private boolean isSelected=false;

        public boolean isSelected() {
            return isSelected;
        }

        public void setSelected(boolean selected) {
            isSelected = selected;
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

        public Boolean getInputRequired() {
            return inputRequired;
        }

        public void setInputRequired(Boolean inputRequired) {
            this.inputRequired = inputRequired;
        }

    }


    public class Section {

        @SerializedName("title")
        @Expose
        private String title;
        @SerializedName("details")
        @Expose
        private List<Detail> details = null;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public List<Detail> getDetails() {
            return details;
        }

        public void setDetails(List<Detail> details) {
            this.details = details;
        }

    }

    public class Detail {

        @SerializedName("type")
        @Expose
        private String type;
        @SerializedName("value1")
        @Expose
        private String value1;
        @SerializedName("value2")
        @Expose
        private String value2;
        @SerializedName("image_url")
        @Expose
        private Object imageUrl;
        @SerializedName("details")
        @Expose
        private java.util.List<Detail_> details = null;
        @SerializedName("lists")
        @Expose
        private java.util.List<Lists> lists = null;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getValue1() {
            return value1;
        }

        public void setValue1(String value1) {
            this.value1 = value1;
        }

        public String getValue2() {
            return value2;
        }

        public void setValue2(String value2) {
            this.value2 = value2;
        }

        public Object getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(Object imageUrl) {
            this.imageUrl = imageUrl;
        }

        public java.util.List<Detail_> getDetails() {
            return details;
        }

        public void setDetails(java.util.List<Detail_> details) {
            this.details = details;
        }

        public java.util.List<Lists> getLists() {
            return lists;
        }

        public void setLists(java.util.List<Lists> lists) {
            this.lists = lists;
        }

    }

    public class Detail_ {

        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("value")
        @Expose
        private String value;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

    }

    public class OtherOption {

        @SerializedName("type")
        @Expose
        private String type;
        @SerializedName("title")
        @Expose
        private String title;
        @SerializedName("value")
        @Expose
        private Object value;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public Object getValue() {
            return value;
        }

        public void setValue(Object value) {
            this.value = value;
        }

    }

    public class ListOption {

        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("options")
        @Expose
        private List<Object> options = null;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<Object> getOptions() {
            return options;
        }

        public void setOptions(List<Object> options) {
            this.options = options;
        }

    }

    public class Lists {

        @SerializedName("title")
        @Expose
        private String title;
        @SerializedName("list_options")
        @Expose
        private List<ListOption> listOptions = null;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public java.util.List<ListOption> getListOptions() {
            return listOptions;
        }

        public void setListOptions(java.util.List<ListOption> listOptions) {
            this.listOptions = listOptions;
        }

    }

}
