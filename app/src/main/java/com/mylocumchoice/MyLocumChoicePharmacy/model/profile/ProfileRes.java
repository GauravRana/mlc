package com.mylocumchoice.MyLocumChoicePharmacy.model.profile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

    public class ProfileRes {
        private String name;

        public String getName() {
            return this.name;
        }

        public void setName(String name) {
            this.name = name;
        }

        private String rating;

        public String getRating() {
            return this.rating;
        }

        public void setRating(String rating) {
            this.rating = rating;
        }

        private Object cancellation_rate;

        @SerializedName("unseen_notifications")
        @Expose
        private boolean unseen_notifications;

        public boolean isUnseen_notifications() {
            return unseen_notifications;
        }

        public void setUnseen_notifications(boolean unseen_notifications) {
            this.unseen_notifications = unseen_notifications;
        }

        public Object getCancellationRate() {
            return this.cancellation_rate;
        }

        public void setCancellationRate(Object cancellation_rate) {
            this.cancellation_rate = cancellation_rate;
        }

        private Status status;

        public Status getStatus() {
            return this.status;
        }

        public void setStatus(Status status) {
            this.status = status;
        }

        private Object profile_pic_url;

        public Object getProfilePicUrl() {
            return this.profile_pic_url;
        }

        public void setProfilePicUrl(Object profile_pic_url) {
            this.profile_pic_url = profile_pic_url;
        }

        private boolean preferences_warning;

        public boolean getPreferencesWarning() {
            return this.preferences_warning;
        }

        public void setPreferencesWarning(boolean preferences_warning) {
            this.preferences_warning = preferences_warning;
        }

        private boolean right_to_work_warning;

        public boolean getRightToWorkWarning() {
            return this.right_to_work_warning;
        }

        public void setRightToWorkWarning(boolean right_to_work_warning) {
            this.right_to_work_warning = right_to_work_warning;
        }

        private boolean skills_warning;

        public boolean getSkillsWarning() {
            return this.skills_warning;
        }

        public void setSkillsWarning(boolean skills_warning) {
            this.skills_warning = skills_warning;
        }

        private boolean accreditations_warning;

        public boolean getAccreditationsWarning() {
            return this.accreditations_warning;
        }

        public void setAccreditationsWarning(boolean accreditations_warning) {
            this.accreditations_warning = accreditations_warning;
        }

        private boolean references_warning;

        public boolean getReferencesWarning() {
            return this.references_warning;
        }

        public void setReferencesWarning(boolean references_warning) {
            this.references_warning = references_warning;
        }

        private boolean pharmacy_compliance_warning;

        public boolean getPharmacyComplianceWarning() {
            return this.pharmacy_compliance_warning;
        }

        public void setPharmacyComplianceWarning(boolean pharmacy_compliance_warning) {
            this.pharmacy_compliance_warning = pharmacy_compliance_warning;
        }

        private boolean systems_warning;

        public boolean isSystems_warning() {
            return systems_warning;
        }

        public void setSystems_warning(boolean systems_warning) {
            this.systems_warning = systems_warning;
        }

        private boolean basic_details_warning;

        public boolean isBasic_details_warning() {
            return basic_details_warning;
        }

        public void setBasic_details_warning(boolean basic_details_warning) {
            this.basic_details_warning = basic_details_warning;
        }

        private String shop_link;

        public String getShopLink() {
            return this.shop_link;
        }

        public void setShopLink(String shop_link) {
            this.shop_link = shop_link;
        }

        private boolean deactivated;

        public boolean getDeactivated() {
            return this.deactivated;
        }

        public void setDeactivated(boolean deactivated) {
            this.deactivated = deactivated;
        }


        public class Status {
            private String name;

            public String getName() {
                return this.name;
            }

            public void setName(String name) {
                this.name = name;
            }

            private String colour;

            public String getColour() {
                return this.colour;
            }

            public void setColour(String colour) {
                this.colour = colour;
            }

            private boolean is_link;

            public boolean getIsLink() {
                return this.is_link;
            }

            public void setIsLink(boolean is_link) {
                this.is_link = is_link;
            }
        }
    }

