package com.mylocumchoice.MyLocumChoicePharmacy.model.signup;

public class AccountDetRes  {
    private Locum locum;

    public Locum getLocum() { return this.locum; }

    public void setLocum(Locum locum) { this.locum = locum; }


    public class Locum
    {
        private int id;

        public int getId() { return this.id; }

        public void setId(int id) { this.id = id; }

        private int status_id;

        public int getStatusId() { return this.status_id; }

        public void setStatusId(int status_id) { this.status_id = status_id; }

        private String email;

        public String getEmail() { return this.email; }

        public void setEmail(String email) { this.email = email; }

        private String authentication_token;

        public String getAuthenticationToken() { return this.authentication_token; }

        public void setAuthenticationToken(String authentication_token) { this.authentication_token = authentication_token; }
    }

}
