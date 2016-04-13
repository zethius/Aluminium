package com.zespolowka.entity.createTest;

import javax.persistence.Entity;

@Entity
public class TaskProgramming extends Task {
    private String testCode;

    public TaskProgramming() {
    }

    public TaskProgramming(final String question, final Float max_points) {
        super(question, max_points);
    }

    public TaskProgramming(final String question, final Float max_points, final String testCode) {
        super(question, max_points);
        this.testCode = testCode;
    }


    public String getTestCode() {
        return testCode;
    }

    public void setTestCode(String testCode) {
        this.testCode = testCode;
    }

    @Override
    public String toString() {
        return "TaskProgramming{" +
                "Task='" + super.toString() + '\'' +
                "question='" + super.getQuestion() + '\'' +
                ", testCode='" + testCode + '\'' +
                '}';
    }
}
