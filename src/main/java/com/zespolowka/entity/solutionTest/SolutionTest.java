package com.zespolowka.entity.solutionTest;

import com.zespolowka.entity.createTest.TaskClosed;
import com.zespolowka.entity.createTest.TaskOpen;
import com.zespolowka.entity.createTest.TaskProgramming;
import com.zespolowka.entity.createTest.Test;
import com.zespolowka.entity.user.User;
import com.zespolowka.forms.SolutionTaskForm;
import com.zespolowka.forms.SolutionTestForm;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Entity
public class SolutionTest {
    @Id
    @GeneratedValue
    private Long id;

    @OneToOne(targetEntity = Test.class, optional = true, cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "idTest")
    private Test test;
    private Integer attempt;
    @DateTimeFormat(pattern = "yyyy/M/d H:m:s")
    private LocalDateTime beginSolution;
    @DateTimeFormat(pattern = "yyyy/M/d H:m:s")
    private LocalDateTime endSolution;
    private Float points;
    @ManyToOne
    private User user;

    @Transient
    private int taskNo;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<TaskSolution> solutionTasks;

    public SolutionTest() {
        this.solutionTasks = new ArrayList<>();
        this.points = 0f;
    }

    public SolutionTest(Test test, User user) {
        this.taskNo = 0;
        this.solutionTasks = new ArrayList<>();
        this.test = test;
        this.points = 0f;
        this.user = user;
    }

    public SolutionTest(Test test, Integer attempt, LocalDateTime beginSolution, LocalDateTime endSolution, Float points) {
        this.solutionTasks = new ArrayList<>();
        this.test = test;
        this.attempt = attempt;
        this.beginSolution = beginSolution;
        this.endSolution = endSolution;
        this.points = points;
    }

    public void addTaskSolutionToTest(TaskSolution taskSolution) {
        taskSolution.setTask(test.getTasks().get(taskNo++));
        if (taskSolution instanceof TaskClosedSolution) {
            TaskClosedSolution taskSol = (TaskClosedSolution) taskSolution;
            TaskClosed taskClo = (TaskClosed) taskSol.getTask();
            Map<String, Boolean> userAnswers = taskSol.getAnswers();
            Map<String, Boolean> correctAnswers = taskClo.getAnswers();
            Float pointsDivide = 0f;
            Float noCorrectAnswers = 0f;
            Boolean chooseIncorect = false;
            for (String key : userAnswers.keySet()) {
                if (userAnswers.get(key) == null) userAnswers.put(key, false);
                System.out.println(correctAnswers);
                if (correctAnswers.get(key) == true) pointsDivide++;
                if (userAnswers.get(key) == true && correctAnswers.get(key) == false) {
                    chooseIncorect = true;
                    break;
                } else if (userAnswers.get(key).equals(correctAnswers.get(key)) && correctAnswers.get(key) == true)
                    noCorrectAnswers++;
            }
            if (chooseIncorect || noCorrectAnswers < 1) {
                taskSol.setPoints(0f);
            } else {
                taskSol.setPoints(taskClo.getMax_points() / (pointsDivide / noCorrectAnswers));
            }
            points += taskSol.getPoints();
            System.out.println(taskSol.getPoints());
            solutionTasks.add(taskSol);
        }
        if (taskSolution instanceof TaskOpenSolution) {
            TaskOpenSolution taskSol = (TaskOpenSolution) taskSolution;
            TaskOpen taskOp = (TaskOpen) taskSol.getTask();
            if (taskSol.getAnswer().equals(taskOp.getAnswer())) {
                points += taskOp.getMax_points();
                taskSol.setPoints(taskOp.getMax_points());
            } else taskSol.setPoints(0f);
            solutionTasks.add(taskSol);
        }
        if (taskSolution instanceof TaskProgrammingSolution) {
            TaskProgrammingSolution taskSol = (TaskProgrammingSolution) taskSolution;
            TaskProgramming taskProgramming = (TaskProgramming) taskSol.getTask();
            if (taskSol.getAnswerCode().equals(taskProgramming.getTestCode())) {
                points += taskProgramming.getMax_points();
            }
            solutionTasks.add(taskSol);
        }
    }

    public SolutionTest create(SolutionTestForm solutionTestForm) {
        List<SolutionTaskForm> solutionTaskForms = solutionTestForm.getTasks();
        for (SolutionTaskForm solutionTaskForm : solutionTaskForms)
            if (solutionTaskForm.getTaskType() == SolutionTaskForm.CLOSEDTASK) {
                TaskClosedSolution taskClosedSolution = new TaskClosedSolution(solutionTaskForm.getTask());
                TreeMap<String, Boolean> answers = new TreeMap<>();
                answers.putAll(solutionTaskForm.getAnswers());
                taskClosedSolution.setAnswers(answers);
                addTaskSolutionToTest(taskClosedSolution);
            } else if (solutionTaskForm.getTaskType() == SolutionTaskForm.OPENTASK) {
                TaskOpenSolution taskOpenSolution = new TaskOpenSolution(solutionTaskForm.getTask());
                taskOpenSolution.setAnswer(solutionTaskForm.getAnswer());
                addTaskSolutionToTest(taskOpenSolution);
            } else if (solutionTaskForm.getTaskType() == SolutionTaskForm.PROGRAMMINGTASK) {
                TaskProgrammingSolution taskProgrammingSolution = new TaskProgrammingSolution(solutionTaskForm.getTask());
                taskProgrammingSolution.setAnswerCode(solutionTaskForm.getAnswerCode());
                addTaskSolutionToTest(taskProgrammingSolution);
            }
        return this;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getAttempt() {
        return attempt;
    }

    public void setAttempt(Integer attempt) {
        this.attempt = attempt;
    }

    public LocalDateTime getBeginSolution() {
        return beginSolution;
    }

    public void setBeginSolution(LocalDateTime beginSolution) {
        this.beginSolution = beginSolution;
    }

    public LocalDateTime getEndSolution() {
        return endSolution;
    }

    public void setEndSolution(LocalDateTime endSolution) {
        this.endSolution = endSolution;
    }

    public Float getPoints() {
        return points;
    }

    public void setPoints(Float points) {
        this.points = points;
    }

    public List<TaskSolution> getSolutionTasks() {
        return solutionTasks;
    }

    public void setSolutionTasks(List<TaskSolution> solutionTasks) {
        this.solutionTasks = solutionTasks;
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
