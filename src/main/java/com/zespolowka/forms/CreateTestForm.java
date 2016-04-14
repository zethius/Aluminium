package com.zespolowka.forms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;


public class CreateTestForm {
    private static final Logger logger = LoggerFactory.getLogger(CreateTestForm.class);

    @Size(min = 5, max = 30)
    private String name;

    @Min(1)
    private Integer attempts;

    private String beginDate;

    private String endDate;

    private int timePerAttempt;

    private String password = "";

    private List<TaskForm> tasks;

    public CreateTestForm() {
        this.tasks = new ArrayList<>();
    }

    public CreateTestForm(String name, Integer attempts, String beginDate, String endDate, List<TaskForm> tasks) {
        this.name = name;
        this.attempts = attempts;
        this.beginDate = beginDate;
        this.endDate = endDate;
        this.tasks = tasks;
    }

    public void addTask(TaskForm task) {
        this.tasks.add(task);
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public Integer getAttempts() {
        return attempts;
    }

    public void setAttempts(final Integer attempts) {
        this.attempts = attempts;
    }

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(final String beginDate) {
        this.beginDate = beginDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(final String endDate) {
        this.endDate = endDate;
    }

    public List<TaskForm> getTasks() {
        return tasks;
    }

    public void setTasks(List<TaskForm> tasks) {
        this.tasks = tasks;
    }

    public int getTimePerAttempt() {
        return timePerAttempt;
    }

    public void setTimePerAttempt(int timePerAttempt) {
        this.timePerAttempt = timePerAttempt;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "CreateTestForm{" +
                "name='" + name + '\'' +
                ", attempts=" + attempts +
                ", beginDate='" + beginDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", timePerAttempt=" + timePerAttempt +
                ", password='" + password + '\'' +
                ", tasks=" + tasks +
                '}';
    }
}
