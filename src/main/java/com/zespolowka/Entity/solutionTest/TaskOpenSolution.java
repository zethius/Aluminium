package com.zespolowka.entity.solutionTest;

import com.zespolowka.entity.createTest.Task;

import javax.persistence.Entity;

@Entity
public class TaskOpenSolution extends TaskSolution {
    private String answer;

    public TaskOpenSolution() {
    }

    public TaskOpenSolution(Task task) {
        super(task);
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
