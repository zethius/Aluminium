package com.zespolowka.entity.solutionTest;


import com.zespolowka.entity.createTest.Task;

import javax.persistence.*;

@Entity
public class TaskProgrammingSolution extends TaskSolution {
    @Lob
    @Column(length = 10000)
    private String answerCode;

    private String language;

    @Lob
    @Column(length = 10000)
    private String failedUnitTest;

    @OneToOne(targetEntity = CompilationError.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
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
        return compilationError != null;
    }

    public Boolean haveFailedUnitTest() {
        return failedUnitTest != null;
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

    public String getFailedUnitTest() {
        return failedUnitTest;
    }

    public void setFailedUnitTest(String failedUnitTest) {
        this.failedUnitTest = failedUnitTest;
    }

    @Override
    public String toString() {
        return "TaskProgrammingSolution{" +
                "TaskSolution='" + super.toString() + '\'' +
                "answerCode='" + answerCode + '\'' +
                ", language='" + language + '\'' +
                '}';
    }
}
