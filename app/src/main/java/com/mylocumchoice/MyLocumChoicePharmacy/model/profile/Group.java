package com.mylocumchoice.MyLocumChoicePharmacy.model.profile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Group {

    @SerializedName("help_text")
    @Expose
    private Object helpText;
    @SerializedName("fields")
    @Expose
    private List<Field> fields = null;

    public Object getHelpText() {
        return helpText;
    }

    public void setHelpText(Object helpText) {
        this.helpText = helpText;
    }

    public List<Field> getFields() {
        return fields;
    }

    public void setFields(List<Field> fields) {
        this.fields = fields;
    }
}
