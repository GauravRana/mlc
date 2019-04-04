package com.mylocumchoice.MyLocumChoicePharmacy.model.shifts;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ShiftDetailResponse {

    @SerializedName("shift_details")
    @Expose
    private ShiftDetails shiftDetails;
    @SerializedName("payment_policy")
    @Expose
    private Object paymentPolicy;

    public ShiftDetails getShiftDetails() {
        return shiftDetails;
    }

    public void setShiftDetails(ShiftDetails shiftDetails) {
        this.shiftDetails = shiftDetails;
    }

    public Object getPaymentPolicy() {
        return paymentPolicy;
    }

    public void setPaymentPolicy(Object paymentPolicy) {
        this.paymentPolicy = paymentPolicy;
    }


    public class ShiftDetails {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("basic_details")
        @Expose
        private BasicDetails basicDetails;
        @SerializedName("pharmacy_details")
        @Expose
        private PharmacyDetails pharmacyDetails;
        @SerializedName("mandatory_requirements")
        @Expose
        private MandatoryRequirements mandatoryRequirements;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public BasicDetails getBasicDetails() {
            return basicDetails;
        }

        public void setBasicDetails(BasicDetails basicDetails) {
            this.basicDetails = basicDetails;
        }

        public PharmacyDetails getPharmacyDetails() {
            return pharmacyDetails;
        }

        public void setPharmacyDetails(PharmacyDetails pharmacyDetails) {
            this.pharmacyDetails = pharmacyDetails;
        }

        public MandatoryRequirements getMandatoryRequirements() {
            return mandatoryRequirements;
        }

        public void setMandatoryRequirements(MandatoryRequirements mandatoryRequirements) {
            this.mandatoryRequirements = mandatoryRequirements;
        }
    }

    public class BasicDetails {

        @SerializedName("details")
        @Expose
        private Details details;

        public Details getDetails() {
            return details;
        }

        public void setDetails(Details details) {
            this.details = details;
        }

    }

    public class PharmacyDetails {

        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("details")
        @Expose
        private Details_Pharmacy details;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Details_Pharmacy getDetails() {
            return details;
        }

        public void setDetails(Details_Pharmacy details) {
            this.details = details;
        }
    }

    public class MandatoryRequirements {

        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("details")
        @Expose
        private List<Detail_Mandatory> details = null;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<Detail_Mandatory> getDetails() {
            return details;
        }

        public void setDetails(List<Detail_Mandatory> details) {
            this.details = details;
        }

    }

    public class Details {

        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("logo")
        @Expose
        private Object logo;
        @SerializedName("emergency")
        @Expose
        private Boolean emergency;
        @SerializedName("address")
        @Expose
        private String address;
        @SerializedName("date_time")
        @Expose
        private String dateTime;
        @SerializedName("duration")
        @Expose
        private String duration;
        @SerializedName("vacancy")
        @Expose
        private String vacancy;
        @SerializedName("mileage_and_perks")
        @Expose
        private String mileageAndPerks;

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

        public Boolean getEmergency() {
            return emergency;
        }

        public void setEmergency(Boolean emergency) {
            this.emergency = emergency;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getDateTime() {
            return dateTime;
        }

        public void setDateTime(String dateTime) {
            this.dateTime = dateTime;
        }

        public String getDuration() {
            return duration;
        }

        public void setDuration(String duration) {
            this.duration = duration;
        }

        public String getVacancy() {
            return vacancy;
        }

        public void setVacancy(String vacancy) {
            this.vacancy = vacancy;
        }

        public String getMileageAndPerks() {
            return mileageAndPerks;
        }

        public void setMileageAndPerks(String mileageAndPerks) {
            this.mileageAndPerks = mileageAndPerks;
        }

    }

    public class Details_Pharmacy {

        @SerializedName("system")
        @Expose
        private String system;
        @SerializedName("pace")
        @Expose
        private String pace;
        @SerializedName("lunch_break")
        @Expose
        private Object lunchBreak;
        @SerializedName("staffing_levels")
        @Expose
        private String staffingLevels;
        @SerializedName("parking_details")
        @Expose
        private String parkingDetails;

        public String getSystem() {
            return system;
        }

        public void setSystem(String system) {
            this.system = system;
        }

        public String getPace() {
            return pace;
        }

        public void setPace(String pace) {
            this.pace = pace;
        }

        public Object getLunchBreak() {
            return lunchBreak;
        }

        public void setLunchBreak(Object lunchBreak) {
            this.lunchBreak = lunchBreak;
        }

        public String getStaffingLevels() {
            return staffingLevels;
        }

        public void setStaffingLevels(String staffingLevels) {
            this.staffingLevels = staffingLevels;
        }

        public String getParkingDetails() {
            return parkingDetails;
        }

        public void setParkingDetails(String parkingDetails) {
            this.parkingDetails = parkingDetails;
        }


    }

    public class Detail_Mandatory {

        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("lists")
        @Expose
        private List<DetailList> lists = null;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<DetailList> getLists() {
            return lists;
        }

        public void setLists(java.util.List<DetailList> lists) {
            this.lists = lists;
        }
    }

    public class DetailList {

        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("options")
        @Expose
        private java.util.List<Object> options = null;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public java.util.List<Object> getOptions() {
            return options;
        }

        public void setOptions(java.util.List<Object> options) {
            this.options = options;
        }

    }
}
