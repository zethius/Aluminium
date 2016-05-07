package com.zespolowka.entity.solutionTest;

import javax.persistence.*;

@Entity
public class CompilationError {
    @Id
    @GeneratedValue
    private Long id;
    @Enumerated(EnumType.STRING)
    private CompilationErrorTypes type;
    @Lob
    @Column(length = 100000)
    private String Error;


    public CompilationError() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CompilationErrorTypes getType() {
        return type;
    }

    public void setType(CompilationErrorTypes type) {
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
                "Error='" + Error + '\'' +
                ", id=" + id +
                ", type=" + type +
                '}';
    }
}