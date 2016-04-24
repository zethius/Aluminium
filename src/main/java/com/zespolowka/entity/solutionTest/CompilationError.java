package com.zespolowka.entity.solutionTest;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class CompilationError {
    @Id
    @GeneratedValue
    private Long id;
    private String type;
    private String Error;


    public CompilationError() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getError() {
        return Error;
    }

    public void setError(String error) {
        Error = error;
    }

    @Override
    public String toString() {
        return "CompilationError{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", Error='" + Error + '\'' +
                '}';
    }
}