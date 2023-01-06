package com.Mediscreen.Report.model;

public enum GenderModel {
    FEMALE("Female"),
    MALE("Male");

    private final String displayGenderValue;

    private GenderModel(String displayGenderValue) {
        this.displayGenderValue = displayGenderValue;
    }

    public String getDisplayGenderValue() {
        return displayGenderValue;
    }
}
