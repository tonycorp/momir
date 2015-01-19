package com.magic.momir.models;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

@Parcel
public class Card {
    @SerializedName("id")
    protected Integer mMultiverseId;
    @SerializedName("relatedCardId")
    protected Integer mRelatedCardId;
    @SerializedName("setNumber")
    protected Integer mSetNumber;
    @SerializedName("name")
    protected String mName;
    @SerializedName("searchName")
    protected String mSearchName;
    @SerializedName("description")
    protected String mDescription;
    @SerializedName("flavor")
    protected String mFlavor;
    @SerializedName("colors")
    protected String[] mColors;
    @SerializedName("manaCost")
    protected String mManacost;
    @SerializedName("convertedManaCost")
    protected Integer mConvertedManaCost;
    @SerializedName("cardSetName")
    protected String mCardSetName;
    @SerializedName("type")
    protected String mType;
    @SerializedName("subType")
    protected String mSubType;
    @SerializedName("power")
    protected Integer mPower;
    @SerializedName("toughness")
    protected Integer mToughness;
    @SerializedName("loyalty")
    protected Integer mLoyalty;
    @SerializedName("rarity")
    protected String mRarity;
    @SerializedName("artist")
    protected String mArtist;
    @SerializedName("cardSetId")
    protected String mCardSetId;
    @SerializedName("token")
    protected Boolean mToken;
    @SerializedName("promo")
    protected Boolean mPromo;
    @SerializedName("rulings")
    protected Ruling[] mRulings;
    @SerializedName("formats")
    protected Format[] mFormats;
    @SerializedName("releasedAt")
    protected String mReleasedAt;
    protected Integer mBoard;

    public Card(){}

    public Integer getMultiverseId() {
        return mMultiverseId;
    }

    public void setMultiverseId(Integer multiverseId) {
        mMultiverseId = multiverseId;
    }

    public Integer getRelatedCardId() {
        return mRelatedCardId;
    }

    public void setRelatedCardId(Integer relatedCardId) {
        mRelatedCardId = relatedCardId;
    }

    public Integer getSetNumber() {
        return mSetNumber;
    }

    public void setSetNumber(Integer setNumber) {
        mSetNumber = setNumber;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getSearchName() {
        return mSearchName;
    }

    public void setSearchName(String searchName) {
        mSearchName = searchName;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public String getFlavor() {
        return mFlavor;
    }

    public void setFlavor(String flavor) {
        mFlavor = flavor;
    }

    public String[] getColors() {
        return mColors;
    }

    public void setColors(String[] colors) {
        mColors = colors;
    }

    public String getManacost() {
        return mManacost;
    }

    public void setManacost(String manacost) {
        mManacost = manacost;
    }

    public Integer getConvertedManaCost() {
        return mConvertedManaCost;
    }

    public void setConvertedManaCost(Integer convertedManaCost) {
        mConvertedManaCost = convertedManaCost;
    }

    public String getCardSetName() {
        return mCardSetName;
    }

    public void setCardSetName(String cardSetName) {
        mCardSetName = cardSetName;
    }

    public String getType() {
        return mType;
    }

    public void setType(String type) {
        mType = type;
    }

    public String getSubType() {
        return mSubType;
    }

    public void setSubType(String subType) {
        mSubType = subType;
    }

    public Integer getPower() {
        return mPower;
    }

    public void setPower(Integer power) {
        mPower = power;
    }

    public Integer getToughness() {
        return mToughness;
    }

    public void setToughness(Integer toughness) {
        mToughness = toughness;
    }

    public Integer getLoyalty() {
        return mLoyalty;
    }

    public void setLoyalty(Integer loyalty) {
        mLoyalty = loyalty;
    }

    public String getRarity() {
        return mRarity;
    }

    public void setRarity(String rarity) {
        mRarity = rarity;
    }

    public String getArtist() {
        return mArtist;
    }

    public void setArtist(String artist) {
        mArtist = artist;
    }

    public String getCardSetId() {
        return mCardSetId;
    }

    public void setCardSetId(String cardSetId) {
        mCardSetId = cardSetId;
    }

    public Boolean getToken() {
        return mToken;
    }

    public void setToken(Boolean token) {
        mToken = token;
    }

    public Boolean getPromo() {
        return mPromo;
    }

    public void setPromo(Boolean promo) {
        mPromo = promo;
    }

    public Ruling[] getRulings() {
        return mRulings;
    }

    public void setRulings(Ruling[] rulings) {
        mRulings = rulings;
    }

    public Format[] getFormats() {
        return mFormats;
    }

    public void setFormats(Format[] formats) {
        mFormats = formats;
    }

    public String getReleasedAt() {
        return mReleasedAt;
    }

    public void setReleasedAt(String releasedAt) {
        mReleasedAt = releasedAt;
    }

    public Integer getBoard(){
        return mBoard;
    }

    public void setBoard(Integer board){
        mBoard = board;
    }
}

