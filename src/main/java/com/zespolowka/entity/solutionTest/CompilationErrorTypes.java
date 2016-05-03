package com.zespolowka.entity.solutionTest;


public enum CompilationErrorTypes {
    TERMINATED("Terminated"),
    ERROR("Error"),
    RESTRICTED_WORDS("Restricted words"),
    COMPILATION_ERROR("Compilation Error");

    private String value;

    CompilationErrorTypes(String displayName) {
        this.value = displayName;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
