package com.magic.momir.models;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

@Parcel
public class Format {
    @SerializedName("name")
    protected String mName;
    @SerializedName("legality")
    protected String mLegality;

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getLegality() {
        return mLegality;
    }

    public void setLegality(String legality) {
        mLegality = legality;
    }
}
