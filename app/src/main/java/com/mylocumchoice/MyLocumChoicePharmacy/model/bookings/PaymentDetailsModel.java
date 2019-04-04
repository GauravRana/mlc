package com.mylocumchoice.MyLocumChoicePharmacy.model.bookings;

public class PaymentDetailsModel {

    Double duration,unpaid_hours,extra_hours,other_payment,total_hours,total_amount,rate;

    public Double getDuration() {
        return duration;
    }

    public void setDuration(Double duration) {
        this.duration = duration;
    }

    public Double getUnpaid_hours() {
        return unpaid_hours;
    }

    public void setUnpaid_hours(Double unpaid_hours) {
        this.unpaid_hours = unpaid_hours;
    }

    public Double getExtra_hours() {
        return extra_hours;
    }

    public void setExtra_hours(Double extra_hours) {
        this.extra_hours = extra_hours;
    }

    public Double getOther_payment() {
        return other_payment;
    }

    public void setOther_payment(Double other_payment) {
        this.other_payment = other_payment;
    }

    public Double getTotal_hours() {
        return total_hours;
    }

    public void setTotal_hours(Double total_hours) {
        this.total_hours = total_hours;
    }

    public Double getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(Double total_amount) {
        this.total_amount = total_amount;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }
}
