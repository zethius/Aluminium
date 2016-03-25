package com.zespolowka.entity.createTest;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import java.util.Map;
import java.util.TreeMap;

@Entity
public class TaskClosed extends Task {

    @ElementCollection
    private Map<String, Boolean> answers = new TreeMap<>();

    public TaskClosed() {
    }

    public TaskClosed(String question, Integer max_points, TreeMap<String, Boolean> answers) {
        super(question, max_points);
        this.answers = answers;
    }

    public TaskClosed(String question, Integer max_points) {
        super(question, max_points);
    }

    public Map<String, Boolean> getAnswers() {
        return answers;
    }

    public void setAnswers(TreeMap<String, Boolean> answers) {
        this.answers = answers;
    }

    @Override
    public String toString() {
        return "TaskClosed{" +
                "Task='" + super.toString() + '\'' +
                "question='" + super.getQuestion() + '\'' +
                "answers=" + answers.toString() +
                '}';
    }
}
