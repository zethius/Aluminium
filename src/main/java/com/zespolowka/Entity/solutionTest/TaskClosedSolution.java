package com.zespolowka.entity.solutionTest;

import com.zespolowka.entity.createTest.Task;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import java.util.Map;
import java.util.TreeMap;

@Entity
public class TaskClosedSolution extends TaskSolution {
    @ElementCollection
    private Map<String, Boolean> answers = new TreeMap<>();

    public TaskClosedSolution() {
    }

    public TaskClosedSolution(Task task) {
        super(task);
    }

    public TaskClosedSolution(Task task, TreeMap<String, Boolean> answers) {
        super(task);
        this.answers = answers;
    }

    public Map<String, Boolean> getAnswers() {
        return answers;
    }

    public void setAnswers(TreeMap<String, Boolean> answers) {
        this.answers = answers;
    }

    @Override
    public String toString() {
        return "TaskClosedSolution{" +
                "TaskSolution='" + super.toString() + '\'' +
                "answers=" + answers +
                '}';
    }
}
