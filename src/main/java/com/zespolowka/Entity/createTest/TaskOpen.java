package com.zespolowka.entity.createTest;

import javax.persistence.Entity;

@Entity
public class TaskOpen extends Task {
    private String answer;

    public TaskOpen(String question, Integer max_points, String answer) {
        super(question, max_points);
        this.answer = answer;
    }

    public TaskOpen() {
    }

    public TaskOpen(String question, Integer max_points) {
        super(question, max_points);
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    @Override
    public String toString() {
        return "TaskOpen{" +
                "Task='" + super.toString() + '\'' +
                "question='" + super.getQuestion() + '\'' +
                "answer='" + answer + '\'' +
                '}';
    }
}
