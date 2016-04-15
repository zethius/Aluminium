package com.zespolowka.forms;

/**
 * Created by Admin on 2016-04-12.
 */
public class ProgrammingTaskForm implements Comparable<ProgrammingTaskForm> {
    private String language;
    private String whiteList;
    private String testCode;
    private Boolean hidden = false;

    public ProgrammingTaskForm(String language) {
        this.language = language;
        this.hidden = false;
    }

    public ProgrammingTaskForm(String language, Boolean hidden) {
        this.language = language;
        this.hidden = hidden;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getWhiteList() {
        return whiteList;
    }

    public void setWhiteList(String whiteList) {
        this.whiteList = whiteList;
    }

    public String getTestCode() {
        return testCode;
    }

    public void setTestCode(String testCode) {
        this.testCode = testCode;
    }

    public Boolean getHidden() {
        return hidden;
    }

    public void setHidden(Boolean hidden) {
        this.hidden = hidden;
    }

    @Override
    public String toString() {
        return "ProgrammingTaskForm{" +
                "language='" + language + '\'' +
                ", whiteList='" + whiteList + '\'' +
                ", testCode='" + testCode + '\'' +
                ", hidden='" + hidden + '\'' +
                '}';
    }


    @Override
    public int compareTo(ProgrammingTaskForm o) {
        return this.language.compareTo(o.getLanguage());
    }
}
