package com.zespolowka.entity.solutionTest;

import com.zespolowka.entity.createTest.Task;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class TaskSqlSolution extends TaskSolution {
    @Column(length = 1000)
    private String sqlAnswer;

    public TaskSqlSolution() {
    }

    public TaskSqlSolution(Task task, String answer) {
        super(task);
        this.sqlAnswer = answer;
    }

    public TaskSqlSolution(Task task) {
        super(task);
        this.sqlAnswer = "";
    }

    public String getAnswer() {
        return sqlAnswer;
    }

    public void setAnswer(String answer) {
        this.sqlAnswer = answer;
    }

    @Override
    public String toString() {
        return "TaskSqlSolution{" +
                "sqlAnswer='" + sqlAnswer + '\'' +
                '}';
    }
}
