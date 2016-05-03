package com.zespolowka.entity.solutionTest;


import com.zespolowka.entity.createTest.Task;

import javax.persistence.*;

@Entity
public class TaskProgrammingSolution extends TaskSolution {
    @Column(length = 10000)
    private String answerCode;

    private String language;

    @OneToOne(targetEntity = CompilationError.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private CompilationError compilationError;


    public TaskProgrammingSolution() {
    }

    public TaskProgrammingSolution(Task task) {
        super(task);
        this.answerCode = "";
    }

    public TaskProgrammingSolution(Task task, String answerCode) {
        super(task);
        this.answerCode = answerCode;
    }

    public Boolean haveError() {
        if (compilationError != null) return true;
        else return false;
    }

    public String getAnswerCode() {
        return answerCode;
    }

    public void setAnswerCode(String answerCode) {
        this.answerCode = answerCode;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public CompilationError getCompilationError() {
        return compilationError;
    }

    public void setCompilationError(CompilationError compilationError) {
        this.compilationError = compilationError;
    }

    @Override
    public String toString() {
        return "TaskProgrammingSolution{" +
                "TaskSolution='" + super.toString() + '\'' +
                "answerCode='" + answerCode + '\'' +
                "language='" + language + '\'' +
                '}';
    }
}
