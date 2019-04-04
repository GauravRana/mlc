package com.mylocumchoice.MyLocumChoicePharmacy.model.profile;

import java.util.ArrayList;

public class VerificationRes {

    private boolean can_request_verification;

    public boolean getCanRequestVerification() { return this.can_request_verification; }

    public void setCanRequestVerification(boolean can_request_verification) { this.can_request_verification = can_request_verification; }

    private ArrayList<Section> sections;

    public ArrayList<Section> getSections() { return this.sections; }

    public void setSections(ArrayList<Section> sections) { this.sections = sections; }


    public class Status
    {
        private String name;

        public String getName() { return this.name; }

        public void setName(String name) { this.name = name; }

        private String colour;

        public String getColour() { return this.colour; }

        public void setColour(String colour) { this.colour = colour; }
    }

    public class DetailsRequired
    {
        private String title;

        public String getTitle() { return this.title; }

        public void setTitle(String title) { this.title = title; }

        private Status status;

        public Status getStatus() { return this.status; }

        public void setStatus(Status status) { this.status = status; }

        private Object issue_raised;

        public Object getIssueRaised() { return this.issue_raised; }

        public void setIssueRaised(Object issue_raised) { this.issue_raised = issue_raised; }
    }

    public class Section
    {
        private String key;

        public String getKey() { return this.key; }

        public void setKey(String key) { this.key = key; }

        private String name;

        public String getName() { return this.name; }

        public void setName(String name) { this.name = name; }

        private String text;

        public String getText() { return this.text; }

        public void setText(String text) { this.text = text; }

        private ArrayList<DetailsRequired> details_required;

        public ArrayList<DetailsRequired> getDetailsRequired() { return this.details_required; }

        public void setDetailsRequired(ArrayList<DetailsRequired> details_required) { this.details_required = details_required; }
    }
}


