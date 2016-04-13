package com.zespolowka.controller.doWyjebania;

import java.util.ArrayList;
import java.util.List;

public class SubjectGroup {
    private String name;
    private List<SubjectGroupOption> options;

    public SubjectGroup(String name, List<SubjectGroupOption> options) {
        this.name = name;
        this.options = options;
    }

    public SubjectGroup() {
        this.options = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<SubjectGroupOption> getOptions() {
        return options;
    }

    public void setOptions(List<SubjectGroupOption> options) {
        this.options = options;
    }

    @Override
    public String toString() {
        return "SubjectGroup{" +
                "name='" + name + '\'' +
                ", options=" + options +
                '}';
    }
}