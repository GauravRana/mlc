package com.mylocumchoice.MyLocumChoicePharmacy.model.profile;

import java.util.ArrayList;

public class ComplianceDetailsRes {

    private String name;

    public String getName() { return this.name; }

    public void setName(String name) { this.name = name; }

    private ArrayList<String> documents_to_sign;

    public ArrayList<String> getDocumentsToSign() { return this.documents_to_sign; }

    public void setDocumentsToSign(ArrayList<String> documents_to_sign) { this.documents_to_sign = documents_to_sign; }

    private boolean can_request_verification;

    public boolean getCanRequestVerification() { return this.can_request_verification; }

    public void setCanRequestVerification(boolean can_request_verification) { this.can_request_verification = can_request_verification; }

    private ArrayList<Section> sections;

    public ArrayList<Section> getSections() { return this.sections; }

    public void setSections(ArrayList<Section> sections) { this.sections = sections; }


    public class Field
    {
        private int id;

        public int getId() { return this.id; }

        public void setId(int id) { this.id = id; }

        private String type;

        public String getType() { return this.type; }

        public void setType(String type) { this.type = type; }

        private String title;

        public String getTitle() { return this.title; }

        public void setTitle(String title) { this.title = title; }

        private DocumentResponse document_response;

        public DocumentResponse getDocumentResponse() { return this.document_response; }

        public void setDocumentResponse(DocumentResponse document_response) { this.document_response = document_response; }

        private DocumentFieldDetails document_field_details;

        public DocumentFieldDetails getDocumentFieldDetails() { return this.document_field_details; }

        public void setDocumentFieldDetails(DocumentFieldDetails document_field_details) { this.document_field_details = document_field_details; }
    }

    public class Group
    {
        private String help_text;

        public String getHelpText() { return this.help_text; }

        public void setHelpText(String help_text) { this.help_text = help_text; }

        private ArrayList<Field> fields;

        public ArrayList<Field> getFields() { return this.fields; }

        public void setFields(ArrayList<Field> fields) { this.fields = fields; }
    }

    public class Section
    {
        private String name;

        public String getName() { return this.name; }

        public void setName(String name) { this.name = name; }

        private ArrayList<Group> groups;

        public ArrayList<Group> getGroups() { return this.groups; }

        public void setGroups(ArrayList<Group> groups) { this.groups = groups; }
    }

    public class DocumentFieldDetails
    {
        private String upload_text;

        public String getUploadText() { return this.upload_text; }

        public void setUploadText(String upload_text) { this.upload_text = upload_text; }
    }


    public class DocumentResponse {


        private PrefernceRes.Status status;

        public PrefernceRes.Status getStatus() {
            return this.status;
        }

        public void setStatus(PrefernceRes.Status status) {
            this.status = status;
        }

        private Object issue_raised;

        public Object getIssueRaised() {
            return this.issue_raised;
        }

        public void setIssueRaised(Object issue_raised) {
            this.issue_raised = issue_raised;
        }

        private String doc_file_name;

        public String getDocFileName() {
            return this.doc_file_name;
        }

        public void setDocFileName(String doc_file_name) {
            this.doc_file_name = doc_file_name;
        }


        private String doc_url;

        public String getDocUrl() {
            return this.doc_url;
        }

        public void setDocUrl(String doc_url) {
            this.doc_url = doc_url;
        }
    }



}
