package com.Mediscreen.Patients.model;

public enum Gender {
    FEMALE("Female"),
    MALE("Male");

    private final String displayGenderValue;

    private Gender(String displayGenderValue) {
        this.displayGenderValue = displayGenderValue;
    }

    public String getDisplayGenderValue() {
        return displayGenderValue;
    }
}
