package com.zespolowka.forms;

import javax.validation.constraints.Size;

public class TaskForm {
    public static final int CLOSEDTASK = 0;
    public static final int OPENTASK=1;
    public static final int PROGRAMMINGTASK=2;

    @Size(min = 5,max = 25)
    private String question;

    @Size(min = 2)
    private String answer;

    @Size(min = 5)
    private String testCode;

    private int taskType;

    public TaskForm() {
    }

    public TaskForm(int taskType) {
        this.taskType = taskType;
    }

    public TaskForm(String question, int taskType, String answer) {
        this.question = question;
        this.taskType = taskType;
        this.answer = answer;
        this.testCode="";
    }

    public TaskForm(String question, String programmingAnswer, int taskType) {
        this.question = question;
        this.testCode = programmingAnswer;
        this.answer="";
        this.taskType = taskType;
    }

    public TaskForm(String question, String answer, String programmingAnswer, int taskType) {
        this.question = question;
        this.answer = answer;
        this.testCode = programmingAnswer;
        this.taskType = taskType;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getTestCode() {
        return testCode;
    }

    public void setTestCode(String testCode) {
        this.testCode = testCode;
    }

    public int getTaskType() {
        return taskType;
    }

    public void setTaskType(int taskType) {
        this.taskType = taskType;
    }

    @Override
    public String toString() {
        return "TaskForm{" +
                "question='" + question + '\'' +
                ", answer='" + answer + '\'' +
                ", testCode='" + testCode + '\'' +
                ", taskType=" + taskType +
                '}';
    }
}
