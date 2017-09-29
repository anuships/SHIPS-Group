package com.example.ships.myapplication.cognitiveTherapy;

/**
 * Created by user on 2017/7/30.
 */

public class FactSheetContent {
    int index;
    String concerns;
    String explanations;

    public FactSheetContent(int i, String concerns, String explanations) {
        index = i;
        this.concerns = concerns;
        this.explanations = explanations;
    }

    public int getIndex() {
        return index;
    }

    public String getConcerns() {
        return concerns;
    }

    public String getExplanations() {
        return explanations;
    }

}
