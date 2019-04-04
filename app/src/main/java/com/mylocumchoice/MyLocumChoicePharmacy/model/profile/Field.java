package com.mylocumchoice.MyLocumChoicePharmacy.model.profile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.json.JSONObject;

import java.util.List;

public class Field {

    @SerializedName("required")
    @Expose
    private Boolean required;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("document_field_detail")
    @Expose
    private DocumentFieldDetail documentFieldDetail;
    @SerializedName("text_field_detail")
    @Expose
    private TextFieldDetail textFieldDetail;
    @SerializedName("page_detail")
    @Expose
    private PageDetail pageDetail;

    @SerializedName("select_options")
    @Expose
    private List<PrefernceRes.SelectOption> selectOption;

    public List<PrefernceRes.SelectOption> getSelectOption() {
        return selectOption;
    }

    public void setSelectOption(List<PrefernceRes.SelectOption> selectOption) {
        this.selectOption = selectOption;
    }

    public Boolean getRequired() {
        return required;
    }

    public void setRequired(Boolean required) {
        this.required = required;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public DocumentFieldDetail getDocumentFieldDetail() {
        return documentFieldDetail;
    }

    public void setDocumentFieldDetail(DocumentFieldDetail documentFieldDetail) {
        this.documentFieldDetail = documentFieldDetail;
    }

    public TextFieldDetail getTextFieldDetail() {
        return textFieldDetail;
    }

    public void setTextFieldDetail(TextFieldDetail textFieldDetail) {
        this.textFieldDetail = textFieldDetail;
    }

    public PageDetail getPageDetail() {
        return pageDetail;
    }

    public void setPageDetail(PageDetail pageDetail) {
        this.pageDetail = pageDetail;
    }
}
