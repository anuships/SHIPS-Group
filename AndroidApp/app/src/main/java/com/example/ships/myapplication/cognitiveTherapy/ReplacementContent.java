package com.example.ships.myapplication.cognitiveTherapy;

/**
 * Created by user on 2017/9/15.
 */

class ReplacementContent {
    int index;
    String concerns;
    String positiveSummary;
    String negativeSummary;

    public ReplacementContent(int i, String concerns, String positiveSummary, String negativeSummary ) {
        index = i;
        this.concerns = concerns;
        this.positiveSummary = positiveSummary;
        this.negativeSummary = negativeSummary;
    }

    public int getIndex() {
        return index;
    }

    public String getConcerns() {
        return concerns;
    }

    public String getPositive() {
        return positiveSummary;
    }

    public String getNegative() {
        return negativeSummary;
    }
}
