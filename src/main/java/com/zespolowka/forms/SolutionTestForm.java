package com.zespolowka.forms;


import java.util.List;

public class SolutionTestForm {
    private String name;

    private Long solutionId;

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

    public Long getSolutionId() {
        return solutionId;
    }

    public void setSolutionId(Long solutionId) {
        this.solutionId = solutionId;
    }

    @Override
    public String toString() {
        return "SolutionTestForm{" +
                "tasks=" + tasks +
                '}';
    }
}
