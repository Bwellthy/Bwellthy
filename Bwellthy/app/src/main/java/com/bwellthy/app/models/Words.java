package com.bwellthy.app.models;

import com.google.gson.annotations.SerializedName;

public class Words {

    public Words() {
    }

    public Words(int id, String word, int variant, String meaning, float ratio) {
        setId(id);
        setWord(word);
        setVariant(variant);
        setMeaning(meaning);
        setRatio(ratio);
    }

    @SerializedName("id")
    private int id;

    @SerializedName("word")
    private String word;

    @SerializedName("variant")
    private int variant;

    @SerializedName("meaning")
    private String meaning;

    @SerializedName("ratio")
    private double ratio;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMeaning() {
        return meaning;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }

    public double getRatio() {
        return ratio;
    }

    public void setRatio(double ratio) {
        this.ratio = ratio;
    }

    public int getVariant() {
        return variant;
    }

    public void setVariant(int variant) {
        this.variant = variant;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }
}

