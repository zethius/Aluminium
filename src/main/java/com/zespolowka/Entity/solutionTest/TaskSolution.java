package com.zespolowka.entity.solutionTest;

import com.zespolowka.entity.createTest.Task;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class TaskSolution {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;
    private Integer points;

    @ManyToOne(optional = true, cascade =CascadeType.MERGE)
    private Task task;

    public TaskSolution() {
    }

    public TaskSolution(Task task) {
        this.task = task;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    @Override
    public String toString() {
        return "SolutionTask{" +
                "id=" + id +
                ", points=" + points +
                //", task=" + task +
                '}';
    }
}
