package com.zespolowka.forms;


import java.util.List;

public class SolutionTestForm {
    private String name;

    private int timePerAttempt;

    private List<SolutionTaskForm> tasks;

    public SolutionTestForm() {
    }

    public SolutionTestForm(List<SolutionTaskForm> tasks) {
        this.tasks = tasks;
    }

    public List<SolutionTaskForm> getTasks() {
        return tasks;
    }

    public void setTasks(List<SolutionTaskForm> tasks) {
        this.tasks = tasks;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTimePerAttempt() {
        return timePerAttempt;
    }

    public void setTimePerAttempt(int timePerAttempt) {
        this.timePerAttempt = timePerAttempt;
    }

    @Override
    public String toString() {
        return "SolutionTestForm{" +
                "tasks=" + tasks +
                '}';
    }
}
