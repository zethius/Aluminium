package com.zespolowka.entity.solutionTest;


public enum SolutionStatus {
    OPEN("Open"),
    DURING("During"),
    FINISHED("Finished");

    private String value;

    SolutionStatus(String displayName) {
        this.value = displayName;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
