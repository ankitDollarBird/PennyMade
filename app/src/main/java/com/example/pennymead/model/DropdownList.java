package com.example.pennymead.model;

import com.google.gson.annotations.SerializedName;

public class DropdownList {

    @SerializedName("name")
    String name;
    @SerializedName("reference")
    String referenceId;

    public String getName() {
        return name;
    }

    public String getReferenceId() {
        return referenceId;
    }
}
