package org.example;

public class MatchedStyle {
    private style style;
    private int matchCount;

    public MatchedStyle(style style, int matchCount) {
        this.style = style;
        this.matchCount = matchCount;
    }

    public style getStyle() {
        return style;
    }

    public int getMatchCount() {
        return matchCount;
    }
}
