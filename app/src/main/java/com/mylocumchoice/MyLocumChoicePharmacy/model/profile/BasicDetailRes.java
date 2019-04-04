package com.mylocumchoice.MyLocumChoicePharmacy.model.profile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class BasicDetailRes{

        @SerializedName("locum")
        @Expose
        private Locum locum;
        @SerializedName("countries")
        @Expose
        private List<Country> countries = null;

        public Locum getLocum() {
            return locum;
        }

        public void setLocum(Locum locum) {
            this.locum = locum;
        }

        public List<Country> getCountries() {
            return countries;
        }

        public void setCountries(List<Country> countries) {
            this.countries = countries;
        }

        public class Country implements Serializable {
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("counties")
        @Expose
        private List<County> counties = null;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<County> getCounties() {
            return counties;
        }

        public void setCounties(List<County> counties) {
            this.counties = counties;
        }

    }

        public class County implements Serializable {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("name")
        @Expose
        private String name;

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

    }
    public class Locum implements Serializable {

        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("mobile")
        @Expose
        private String mobile;
        @SerializedName("gphc_no")
        @Expose
        private Integer gphcNo;
        @SerializedName("gphc_expiry_date")
        @Expose
        private String gphcExpiresOn;

        @SerializedName("address_line_1")
        @Expose
        private String address1;

        @SerializedName("cv_url")
        @Expose
        private String cvUrl;

        @SerializedName("cv_file_name")
        @Expose
        private String cvFileName;

        @SerializedName("address_line_2")
        @Expose
        private String address2;

        @SerializedName("address_line_3")
        @Expose
        private String address3;

        @SerializedName("registration_details_status")
        @Expose
        private String registration_details_status;

        @SerializedName("town")
        @Expose
        private Object town;
        @SerializedName("postcode")
        @Expose
        private Object postcode;
        @SerializedName("county")
        @Expose
        private Object county;
        @SerializedName("country")
        @Expose

        private Object country;

        @SerializedName("gphc_details_editable")
        @Expose
        private boolean gphcDetailEditable;


        private final static long serialVersionUID = -1089939969165906546L;

        private GphcStatus gphc_status;

        public GphcStatus getGphcStatus() { return this.gphc_status; }

        public void setGphcStatus(GphcStatus gphc_status) { this.gphc_status = gphc_status; }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public Integer getGphcNo() {
            return gphcNo;
        }

        public void setGphcNo(Integer gphcNo) {
            this.gphcNo = gphcNo;
        }

        public String getGphcExpiresOn() {
            return gphcExpiresOn;
        }

        public void setGphcExpiresOn(String gphcExpiresOn) {
            this.gphcExpiresOn = gphcExpiresOn;
        }

        public String getCvUrl() {
            return cvUrl;
        }

        public String getCvFileName() {
            return cvFileName;
        }

        public void setAddress1(String address1) {
            this.address1 = address1;
        }

        public String getAddress1() {
            return address1;
        }

        public String getAddress2() {
            return address2;
        }

        public void setAddress2(String address2) {
            this.address2 = address2;
        }

        public String getAddress3() {
            return address3;
        }

        public void setAddress3(String address3) {
            this.address3 = address3;
        }




        public Object getTown() {
            return town;
        }

        public void setTown(Object town) {
            this.town = town;
        }

        public Object getPostcode() {
            return postcode;
        }

        public void setPostcode(Object postcode) {
            this.postcode = postcode;
        }

        public Object getCounty() {
            return county;
        }

        public void setCounty(Object county) {
            this.county = county;
        }

        public Object getCountry() {
            return country;
        }

        public void setCountry(Object country) {
            this.country = country;
        }

        public String getRegistration_details_status() {
            return registration_details_status;
        }

        public void setRegistration_details_status(String registration_details_status) {
            this.registration_details_status = registration_details_status;
        }

        private Object gphc_issue_raised;

        public Object getGphcIssueRaised() { return this.gphc_issue_raised; }

        public void setGphcIssueRaised(Object gphc_issue_raised) { this.gphc_issue_raised = gphc_issue_raised; }

        public boolean isGphcDetailEditable() {
            return gphcDetailEditable;
        }

        public void setGphcDetailEditable(boolean gphcDetailEditable) {
            this.gphcDetailEditable = gphcDetailEditable;
        }
    }


    public class GphcStatus
    {
        private String name;

        public String getName() { return this.name; }

        public void setName(String name) { this.name = name; }

        private String colour;

        public String getColour() { return this.colour; }

        public void setColour(String colour) { this.colour = colour; }
    }
}
