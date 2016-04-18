package com.zespolowka.entity.solutionTest;

import com.zespolowka.entity.createTest.Task;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class TaskOpenSolution extends TaskSolution {
    @Column(length = 10000)
    private String answer;

    public TaskOpenSolution() {
        this.answer = "";
    }

    public TaskOpenSolution(Task task) {
        super(task);
        this.answer = "";
    }

    public TaskOpenSolution(Task task, String answer) {
        super(task);
        this.answer = answer;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    @Override
    public String toString() {
        return "TaskOpenSolution{" +
                "TaskSolution='" + super.toString() + '\'' +
                "answer='" + answer + '\'' +
                '}';
    }
}
