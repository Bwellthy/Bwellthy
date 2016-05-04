package com.bwellthy.app.api.response;

import com.bwellthy.app.models.Words;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Vocabulary {

    @SerializedName("words")
    private List<Words> words = new ArrayList<Words>();


    public List<Words> getWords() {
        return words;
    }

    public void setWords(List<Words> items) {
        this.words = items;
    }
}
