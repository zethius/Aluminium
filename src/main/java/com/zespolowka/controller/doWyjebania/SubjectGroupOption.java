package com.zespolowka.controller.doWyjebania;

public class SubjectGroupOption {
    private String name;
    private int type;

    public SubjectGroupOption(String name) {
        this.name = name;
    }


    public SubjectGroupOption() {
        this.name = "chuj";
        this.type = 0;
    }

    public SubjectGroupOption(int type) {
        this.name = "test";
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "SubjectGroupOption{" +
                "name='" + name + '\'' +
                '}';
    }
}
