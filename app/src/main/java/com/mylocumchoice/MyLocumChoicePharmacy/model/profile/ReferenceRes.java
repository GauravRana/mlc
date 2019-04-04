package com.mylocumchoice.MyLocumChoicePharmacy.model.profile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ReferenceRes {

    private ArrayList<LocumReference> locum_references;

    public ArrayList<LocumReference> getLocumReferences() { return this.locum_references; }

    public void setLocumReferences(ArrayList<LocumReference> locum_references) { this.locum_references = locum_references; }

public class LocumReference
{
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("job_title")
    @Expose
    private String jobTitle;
    @SerializedName("company")
    @Expose
    private String company;
    @SerializedName("editable")
    @Expose
    private Boolean editable;
    @SerializedName("document")
    @Expose
    private Document document;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public Boolean getEditable() {
        return editable;
    }

    public void setEditable(Boolean editable) {
        this.editable = editable;
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }
}

    public class Document {

        @SerializedName("issue_raised")
        @Expose
        private Object issueRaised;
        @SerializedName("doc_file_name")
        @Expose
        private String docFileName;
        @SerializedName("doc_url")
        @Expose
        private String docUrl;
        @SerializedName("doc_thumbnail_url")
        @Expose
        private Object docThumbnailUrl;
        @SerializedName("status")
        @Expose
        private Status status;


        public Object getIssueRaised() {
            return issueRaised;
        }

        public void setIssueRaised(Object issueRaised) {
            this.issueRaised = issueRaised;
        }

        public String getDocFileName() {
            return docFileName;
        }

        public void setDocFileName(String docFileName) {
            this.docFileName = docFileName;
        }

        public String getDocUrl() {
            return docUrl;
        }

        public void setDocUrl(String docUrl) {
            this.docUrl = docUrl;
        }

        public Object getDocThumbnailUrl() {
            return docThumbnailUrl;
        }

        public void setDocThumbnailUrl(Object docThumbnailUrl) {
            this.docThumbnailUrl = docThumbnailUrl;
        }

        public Status getStatus() {
            return status;
        }

        public void setStatus(Status status) {
            this.status = status;
        }

    }

    public class Status {

        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("colour")
        @Expose
        private String colour;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getColour() {
            return colour;
        }

        public void setColour(String colour) {
            this.colour = colour;
        }

    }
}