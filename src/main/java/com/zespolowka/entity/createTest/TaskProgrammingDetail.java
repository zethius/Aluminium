package com.zespolowka.entity.createTest;

import javax.persistence.*;

@Entity
public class TaskProgrammingDetail {
    @Id
    @GeneratedValue
    private Long id;
    @Column(length = 10000)
    private String testCode;
    @Column(length = 10000)
    private String whiteList;
    @Enumerated(EnumType.STRING)
    private ProgrammingLanguages language;
    private String testClassName;
    private String solutionClassName;

    public TaskProgrammingDetail() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTestCode() {
        return testCode;
    }

    public void setTestCode(String testCode) {
        this.testCode = testCode;
    }

    public String getWhiteList() {
        return whiteList;
    }

    public void setWhiteList(String whiteList) {
        this.whiteList = whiteList;
    }

    public ProgrammingLanguages getLanguage() {
        return language;
    }

    public void setLanguage(ProgrammingLanguages language) {
        this.language = language;
    }

    public String getTestClassName() {
        return testClassName;
    }

    public void setTestClassName(String testClassName) {
        this.testClassName = testClassName;
    }

    public String getSolutionClassName() {
        return solutionClassName;
    }

    public void setSolutionClassName(String solutionClassName) {
        this.solutionClassName = solutionClassName;
    }

    @Override
    public String toString() {
        return "TaskProgrammingDetail{" +
                "testCode='" + testCode + '\'' +
                ", whiteList='" + whiteList + '\'' +
                ", solutionClassName='" + solutionClassName + '\'' +
                ", testClassName='" + testClassName + '\'' +
                ", language=" + language +
                '}';
    }
}
