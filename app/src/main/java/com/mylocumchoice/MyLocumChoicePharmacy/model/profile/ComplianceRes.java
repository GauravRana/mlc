package com.mylocumchoice.MyLocumChoicePharmacy.model.profile;

import java.util.ArrayList;

public class ComplianceRes {

    private ArrayList<Client> clients;

    public ArrayList<Client> getClients() { return this.clients; }

    public void setClients(ArrayList<Client> clients) { this.clients = clients; }

    public class Client
    {
        private boolean warning;

        private int id;

        public int getId() { return this.id; }

        public void setId(int id) { this.id = id; }

        private String name;

        public String getName() { return this.name; }

        public void setName(String name) { this.name = name; }

        private Object logo;

        public Object getLogo() { return this.logo; }

        public void setLogo(Object logo) { this.logo = logo; }

        private Status status;

        public Status getStatus() { return this.status; }

        public void setStatus(Status status) { this.status = status; }

        public boolean getWarning() { return this.warning; }

        public void setWarning(boolean warning) { this.warning = warning; }
    }


    public class Status
    {
        private String name;

        public String getName() { return this.name; }

        public void setName(String name) { this.name = name; }

        private String colour;

        public String getColour() { return this.colour; }

        public void setColour(String colour) { this.colour = colour; }
    }

}
