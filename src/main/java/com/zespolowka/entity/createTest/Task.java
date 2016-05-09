package com.zespolowka.entity.createTest;

import javax.persistence.*;

@Entity
@Table(name = "tasks")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name = "idTask")
    private Long id;
    @Lob
    @Column(length = 10000)
    private String question;
    private Float max_points;


    public Task(final String question, final Float max_points) {
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

    public Float getMax_points() {
        return max_points;
    }

    public void setMax_points(final Float max_points) {
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
