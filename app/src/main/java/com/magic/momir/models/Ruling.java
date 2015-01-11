package com.magic.momir.models;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

@Parcel
public class Ruling {
    @SerializedName("releasedAt")
    protected String mReleasedAt;
    @SerializedName("rule")
    protected String mRule;

    public String getReleasedAt() {
        return mReleasedAt;
    }

    public void setReleasedAt(String releasedAt) {
        mReleasedAt = releasedAt;
    }

    public String getRule() {
        return mRule;
    }

    public void setRule(String rule) {
        mRule = rule;
    }
}
