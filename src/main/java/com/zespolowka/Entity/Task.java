package com.zespolowka.Entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by Peps on 2016-03-09.
 */
@Entity
@Table(name="tasks")
public class Task {
    @Id
    @GeneratedValue
    private Long id;

    String task_group;
    String question;
    int max_points;
    int points=0;

    public Task(String task_group, String question, int max_points) {
        this.task_group = task_group;
        this.question = question;
        this.max_points = max_points;
    }

    public String getTask_group() {
        return task_group;
    }

    public void setTask_group(String task_group) {
        this.task_group = task_group;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public int getMax_points() {
        return max_points;
    }

    public void setMax_points(int max_points) {
        this.max_points = max_points;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
