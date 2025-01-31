package org.example;

public class style {
    private String name;
    private int min;
    private int max;
    private String definition;
    private String bodyShape;
    private String occasion;

    public style() {
    }

    public style(String name, int min, int max, String definition, String bodyShape, String occasion) {
        this.name = name;
        this.min = min;
        this.max = max;
        this.definition = definition;
        this.bodyShape = bodyShape;
        this.occasion = occasion;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public String getBodyShape() {
        return bodyShape;
    }

    public void setBodyShape(String bodyShape) {
        this.bodyShape = bodyShape;
    }

    public String getOccasion() {
        return occasion;
    }

    public void setOccasion(String occasion) {
        this.occasion = occasion;
    }

    @Override
    public String toString() {
        return name + ": " + definition;
    }
}