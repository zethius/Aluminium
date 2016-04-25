package com.zespolowka.entity.solutionTest;

import com.zespolowka.entity.createTest.Task;

import javax.persistence.*;

@Entity
public class TaskSqlSolution extends TaskSolution {
    @Column(length = 1000)
    private String sqlAnswer;

    @OneToOne(targetEntity = CompilationError.class,fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private CompilationError compilationError;

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
    public Boolean haveError(){
        if (compilationError!=null) return true;
        else return false;
    }

    public String getSqlAnswer() {
        return sqlAnswer;
    }

    public void setSqlAnswer(String sqlAnswer) {
        this.sqlAnswer = sqlAnswer;
    }

    public CompilationError getCompilationError() {
        return compilationError;
    }

    public void setCompilationError(CompilationError compilationError) {
        this.compilationError = compilationError;
    }

    @Override
    public String toString() {
        return "TaskSqlSolution{" +
                "sqlAnswer='" + sqlAnswer + '\'' +
                '}';
    }
}
