package com.zespolowka.entity.solutionTest;

import com.zespolowka.entity.createTest.TaskClosed;
import com.zespolowka.entity.createTest.TaskOpen;
import com.zespolowka.entity.createTest.Test;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Entity
public class SolutionTest {
    @Id
    @GeneratedValue
    private Long id;

    @OneToOne(targetEntity = Test.class, optional = true, cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "idTest")
    private Test test;
    private Integer attempt;
    private LocalDate beginSolution;
    private LocalDate endSolution;
    private Integer points;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<TaskSolution> solutionTasks;

    public SolutionTest() {
        this.solutionTasks = new ArrayList<>();
        this.points = 0;
    }

    public SolutionTest(Test test) {
        this.solutionTasks = new ArrayList<>();
        this.test = test;
        this.points = 0;
    }

    public SolutionTest(Test test, Integer attempt, LocalDate beginSolution, LocalDate endSolution, Integer points) {
        this.solutionTasks = new ArrayList<>();
        this.test = test;
        this.attempt = attempt;
        this.beginSolution = beginSolution;
        this.endSolution = endSolution;
        this.points = points;
    }

    public void addTaskSolutionToTest(TaskSolution taskSolution) {
        solutionTasks.add(taskSolution);
        if (taskSolution instanceof TaskClosedSolution) {
            TaskClosedSolution taskSol = (TaskClosedSolution) taskSolution;
            TaskClosed taskClo = (TaskClosed) taskSol.getTask();
            Map<String, Boolean> userAnswers = taskSol.getAnswers();
            Map<String, Boolean> correctAnswers = taskClo.getAnswers();
            System.out.println(userAnswers.toString());
            System.out.println(correctAnswers.toString());
            System.out.println(userAnswers.size());
            Integer pointPerAnswer = taskClo.getMax_points() / correctAnswers.size();
            for (String key : userAnswers.keySet()) {
                if (userAnswers.get(key).equals(correctAnswers.get(key)))
                    points +=pointPerAnswer;
            }

        }
        if (taskSolution instanceof TaskOpenSolution) {
            TaskOpenSolution taskSol = (TaskOpenSolution) taskSolution;
            TaskOpen taskOp = (TaskOpen) taskSol.getTask();
            if (taskSol.getAnswer().equals(taskOp.getAnswer())) {
                points += taskOp.getMax_points();
            }
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Test getTest() {
        return test;
    }

    public void setTest(Test test) {
        this.test = test;
    }


    public Integer getAttempt() {
        return attempt;
    }

    public void setAttempt(Integer attempt) {
        this.attempt = attempt;
    }

    public LocalDate getBeginSolution() {
        return beginSolution;
    }

    public void setBeginSolution(LocalDate beginSolution) {
        this.beginSolution = beginSolution;
    }

    public LocalDate getEndSolution() {
        return endSolution;
    }

    public void setEndSolution(LocalDate endSolution) {
        this.endSolution = endSolution;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    @Override
    public String toString() {
        return "SolutionTest{" +
                "id=" + id +
                ", Test name =" + test.getName() +
                ", Max Points =" + test.getMaxPoints() +
                ", attempt=" + attempt +
                ", beginSolution=" + beginSolution +
                ", endSolution=" + endSolution +
                ", solutionTasks=" + solutionTasks.toString() +
                ", points=" + points +
                '}';
    }
}
