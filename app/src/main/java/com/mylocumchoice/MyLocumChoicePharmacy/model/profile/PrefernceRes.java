package com.mylocumchoice.MyLocumChoicePharmacy.model.profile;

import com.google.gson.JsonObject;

import java.util.ArrayList;

import javax.annotation.Nullable;

public class PrefernceRes {

    private String name;

    public String getName() { return this.name; }

    public void setName(String name) { this.name = name; }

    private ArrayList<Section> sections;

    public ArrayList<Section> getSections() { return this.sections; }

    public void setSections(ArrayList<Section> sections) { this.sections = sections; }

    public class TextFieldDetails
    {
        private String placeholder;

        public String getPlaceholder() { return this.placeholder; }

        public void setPlaceholder(String placeholder) { this.placeholder = placeholder; }
    }

    public class PageDetails
    {
        private String title;

        public String getTitle() { return this.title; }

        public void setTitle(String title) { this.title = title; }
    }

    public class DocumentFieldDetails
    {
        private String upload_text;

        public String getUploadText() { return this.upload_text; }

        public void setUploadText(String upload_text) { this.upload_text = upload_text; }
    }

    public class TextFieldDetails2
    {
        private String placeholder;

        public String getPlaceholder() { return this.placeholder; }

        public void setPlaceholder(String placeholder) { this.placeholder = placeholder; }
    }

    public class PageDetails2
    {
        private String title;

        public String getTitle() { return this.title; }

        public void setTitle(String title) { this.title = title; }
    }

    public class FieldForOption
    {
        private int id;

        public int getId() { return this.id; }

        public void setId(int id) { this.id = id; }

        private String type;

        public String getType() { return this.type; }

        public void setType(String type) { this.type = type; }

        private Object title;

        public Object getTitle() { return this.title; }

        public void setTitle(Object title) { this.title = title; }

        private String text_response;

        public String getTextResponse() { return this.text_response; }

        public void setTextResponse(String text_response) { this.text_response = text_response; }

        private TextFieldDetails2 text_field_details;

        public TextFieldDetails2 getTextFieldDetails() { return this.text_field_details; }

        public void setTextFieldDetails(TextFieldDetails2 text_field_details) { this.text_field_details = text_field_details; }

        private PageDetails2 page_details;

        public PageDetails2 getPageDetails() { return this.page_details; }

        public void setPageDetails(PageDetails2 page_details) { this.page_details = page_details; }
    }

    public class SelectOption
    {
        private int id;

        public int getId() { return this.id; }

        public void setId(int id) { this.id = id; }

        private String name;

        public String getName() { return this.name; }

        public void setName(String name) { this.name = name; }

        private boolean selected;

        public boolean getSelected() { return this.selected; }

        public void setSelected(boolean selected) { this.selected = selected; }

        private FieldForOption field_for_option;

        public FieldForOption getFieldForOption() { return this.field_for_option; }

        public void setFieldForOption(FieldForOption field_for_option) { this.field_for_option = field_for_option; }
    }

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

        private String text_response;

        public String getTextResponse() { return this.text_response; }

        public void setTextResponse(String text_response) { this.text_response = text_response; }

        private TextFieldDetails text_field_details;

        public TextFieldDetails getTextFieldDetails() { return this.text_field_details; }

        public void setTextFieldDetails(TextFieldDetails text_field_details) { this.text_field_details = text_field_details; }

        private PageDetails page_details;

        public PageDetails getPageDetails() { return this.page_details; }

        public void setPageDetails(PageDetails page_details) { this.page_details = page_details; }

        private Boolean polar_response;

        public Boolean getPolarResponse() { return this.polar_response; }

        public void setPolarResponse(Boolean polar_response) { this.polar_response = polar_response; }

        private Object date_response;

        public Object getDateResponse() { return this.date_response; }

        public void setDateResponse(Object date_response) { this.date_response = date_response; }

        private DocumentResponse document_response;

        public DocumentResponse getDocumentResponse() { return this.document_response; }

        public void setDocumentResponse(DocumentResponse document_response) { this.document_response = document_response; }

        private DocumentFieldDetails document_field_details;

        public DocumentFieldDetails getDocumentFieldDetails() { return this.document_field_details; }

        public void setDocumentFieldDetails(DocumentFieldDetails document_field_details) { this.document_field_details = document_field_details; }

        private ArrayList<SelectOption> select_options;

        public ArrayList<SelectOption> getSelectOptions() { return this.select_options; }

        public void setSelectOptions(ArrayList<SelectOption> select_options) { this.select_options = select_options; }
    }

    public class Group
    {
        private Object help_text;

        public Object getHelpText() { return this.help_text; }

        public void setHelpText(Object help_text) { this.help_text = help_text; }

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

    public class DocumentResponse {

        private Object doc_thumbnail_url;

        private Status status;

        public Status getStatus() {
            return this.status;
        }

        public void setStatus(Status status) {
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

        public Object getDoc_thumbnail_url() {
            return doc_thumbnail_url;
        }

        public void setDoc_thumbnail_url(Object doc_thumbnail_url) {
            this.doc_thumbnail_url = doc_thumbnail_url;
        }
    }

    public class Status
    {
        private int id;

        private String colour;

        public int getId() { return this.id; }

        public void setId(int id) { this.id = id; }

        private String name;

        public String getName() { return this.name; }

        public void setName(String name) { this.name = name; }

        public String getColor() { return this.colour; }

        public void setColor(String name) { this.colour = colour; }

    }

}
