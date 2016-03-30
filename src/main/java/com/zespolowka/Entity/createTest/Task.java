package com.zespolowka.entity.createTest;

import javax.persistence.*;

@Entity
@Table(name = "tasks")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name = "idTask")
    private Long id;
    private String question;
    private Integer max_points;


    public Task(final String question, final Integer max_points) {
        this.question = question;
        this.max_points = max_points;
    }

    public Task() {
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(final String question) {
        this.question = question;
    }

    public Integer getMax_points() {
        return max_points;
    }

    public void setMax_points(final int max_points) {
        this.max_points = max_points;
    }


    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", question='" + question + '\'' +
                ", max_points=" + max_points +
                '}';
    }
}
