package com.mylocumchoice.MyLocumChoicePharmacy.model.profile;

public class UpdateRes {

    private Locum locum;

    public Locum getLocum() { return this.locum; }

    public void setLocum(Locum locum) { this.locum = locum; }


    public class Locum
    {
        private String email;

        public String getEmail() { return this.email; }

        public void setEmail(String email) { this.email = email; }

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
    }
}



