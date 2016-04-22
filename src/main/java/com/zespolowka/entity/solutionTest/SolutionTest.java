package com.zespolowka.entity.solutionTest;

<<<<<<< HEAD
import com.zespolowka.entity.createTest.Test;
import com.zespolowka.entity.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
=======
import com.zespolowka.entity.createTest.*;
import com.zespolowka.entity.solutionTest.config.SolutionConfig;
import com.zespolowka.entity.user.User;
import com.zespolowka.forms.SolutionTaskForm;
import com.zespolowka.forms.SolutionTestForm;
import org.apache.commons.io.FileUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.*;
import java.math.BigDecimal;
import java.net.Socket;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;
>>>>>>> addf63146eadb4865c3e88fc9502c025b3871c1e

@Entity
public class SolutionTest {
    private static final Logger logger = LoggerFactory.getLogger(SolutionTest.class);
    @Id
    @GeneratedValue
    private Long id;

    @OneToOne(targetEntity = Test.class, optional = true, cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "idTest")
    private Test test;
    private Integer attempt;
<<<<<<< HEAD
    private LocalDateTime beginSolution;
=======
    @DateTimeFormat(pattern = "yyyy/M/d H:m:s")
    private LocalDateTime beginSolution;
    @DateTimeFormat(pattern = "yyyy/M/d H:m:s")
>>>>>>> addf63146eadb4865c3e88fc9502c025b3871c1e
    private LocalDateTime endSolution;
    private Float points;
    @ManyToOne
    private User user;

<<<<<<< HEAD
=======
    @Transient
    private int taskNo;

    @Transient
    private String dir = "/home/pitek/zespolowka/skrypty/";

    @Transient
    private String resultDir = "/tmp/";

>>>>>>> addf63146eadb4865c3e88fc9502c025b3871c1e
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<TaskSolution> solutionTasks;

    public SolutionTest() {
        this.solutionTasks = new ArrayList<>();
<<<<<<< HEAD
        this.points = 0.0f;
    }

    public SolutionTest(Test test, User user) {
        this.solutionTasks = new ArrayList<>();
        this.test = test;
        this.points = 0.0f;
=======
        this.points = 0f;
    }

    public SolutionTest(Test test, User user) {
        this.taskNo = 0;
        this.solutionTasks = new ArrayList<>();
        this.test = test;
        this.points = 0f;
>>>>>>> addf63146eadb4865c3e88fc9502c025b3871c1e
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

<<<<<<< HEAD
=======
    public void addTaskSolutionToTest(TaskSolution taskSolution) throws IOException, ParseException {
        taskSolution.setTask(test.getTasks().get(taskNo++));
        logger.info(test.toString());
        logger.info(taskSolution.getTask().toString());
        if (taskSolution instanceof TaskClosedSolution) {
            TaskClosedSolution taskSol = (TaskClosedSolution) taskSolution;
            TaskClosed taskClo = (TaskClosed) taskSol.getTask();
            Map<String, Boolean> userAnswers = taskSol.getAnswers();
            Map<String, Boolean> correctAnswers = taskClo.getAnswers();
            if (taskClo.getCountingType() == taskClo.WRONG_RESET) {
                Boolean theSame = true;
                for (String key : userAnswers.keySet()) {
                    if ((userAnswers.get(key) != null && userAnswers.get(key)) && (!correctAnswers.get(key))) {
                        theSame = false;
                        break;
                    } else if ((userAnswers.get(key) == null || !userAnswers.get(key)) && (correctAnswers.get(key))) {
                        theSame = false;
                        break;
                    }
                }
                if (theSame) {
                    taskSol.setPoints(taskClo.getMax_points());
                } else taskSol.setPoints(0f);
            } else {
                Float pointsDivide = 0f;
                Float noCorrectAnswers = 0f;
                Boolean chooseIncorect = false;
                for (String key : userAnswers.keySet()) {
                    if (userAnswers.get(key) == null) userAnswers.put(key, false);
                    if (correctAnswers.get(key)) pointsDivide++;
                    if (userAnswers.get(key) && !correctAnswers.get(key)) {
                        chooseIncorect = true;
                        break;
                    } else if (userAnswers.get(key).equals(correctAnswers.get(key)) && correctAnswers.get(key))
                        noCorrectAnswers++;
                }
                if (chooseIncorect || noCorrectAnswers < 1) {
                    taskSol.setPoints(0f);
                } else {
                    taskSol.setPoints(taskClo.getMax_points() / (pointsDivide / noCorrectAnswers));
                }
            }
            points += taskSol.getPoints();
            solutionTasks.add(taskSol);
        }
        if (taskSolution instanceof TaskOpenSolution) {
            TaskOpenSolution taskSol = (TaskOpenSolution) taskSolution;
            TaskOpen taskOp = (TaskOpen) taskSol.getTask();
            if (!taskOp.getCaseSens()) {
                if (taskSol.getAnswer().toUpperCase().equals(taskOp.getAnswer().toUpperCase())) {
                    points += taskOp.getMax_points();
                    taskSol.setPoints(taskOp.getMax_points());
                } else taskSol.setPoints(0f);
            } else {
                if (taskSol.getAnswer().equals(taskOp.getAnswer())) {
                    points += taskOp.getMax_points();
                    taskSol.setPoints(taskOp.getMax_points());
                } else taskSol.setPoints(0f);
            }
            solutionTasks.add(taskSol);
        }
        if (taskSolution instanceof TaskProgrammingSolution) {
            logger.info("tu wchodzisz?");
            TaskProgrammingSolution taskSol = (TaskProgrammingSolution) taskSolution;
            TaskProgramming taskProgramming = (TaskProgramming) taskSol.getTask();
            logger.info("sprawdzam czy to działa");

            SolutionConfig solutionConfig = new SolutionConfig();
            JSONObject jsonObject;
            String userDirectory = test.getName() + "_" + this.getAttempt() + "_" + user.getEmail() + "_" + UUID.randomUUID().toString().substring(0, 4) + "/";


            Set<TaskProgrammingDetail> taskProgrammingDetails = taskProgramming.getProgrammingDetailSet();
            for (TaskProgrammingDetail taskProgrammingDetail : taskProgrammingDetails) {
                if (taskProgrammingDetail.getLanguage().equals(ProgrammingLanguages.JAVA)) {
                    jsonObject = solutionConfig.createJavaConfig("Dodawanie.java", "MyTests.java", "allowed_list_path");
                    FileUtils.writeStringToFile(new File(dir + userDirectory + "Dodawanie.java"), taskSol.getAnswerCode());
                    FileUtils.writeStringToFile(new File(dir + userDirectory + "MyTests.java"), taskProgrammingDetail.getTestCode());
                    FileUtils.writeStringToFile(new File(dir + userDirectory + "allowed_list_java"), taskProgrammingDetail.getWhiteList());
                    FileUtils.writeStringToFile(new File(dir + userDirectory + "config.json"), jsonObject.toJSONString());
                }
            }
            Socket socket = new Socket("localhost", 54321);
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
            printWriter.println("\"" + userDirectory + "\"");
            printWriter.close();
            socket.close();

            JSONParser parser = new JSONParser();
            Object result = parser.parse(new FileReader(resultDir + userDirectory + "output.json"));
            if (result instanceof JSONArray) {
                JSONArray jsonArray = (JSONArray) result;
                Long passed = (Long) jsonArray.get(1);
                Long all = (Long) jsonArray.get(2);
                BigDecimal wynik = new BigDecimal((float) passed / all).setScale(2);
                logger.info(wynik.doubleValue() + "");
            } else {
                jsonObject = (JSONObject) result;
                logger.info(jsonObject.toString());
            }

            points += taskProgramming.getMax_points();
            taskSol.setPoints(taskProgramming.getMax_points());
            solutionTasks.add(taskSol);
        }
    }

    public SolutionTest create(SolutionTestForm solutionTestForm) throws IOException, ParseException {
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
                logger.info("WCHODZISZ TU?");
                logger.info(solutionTaskForm.toString());
                TaskProgrammingSolution taskProgrammingSolution = new TaskProgrammingSolution(solutionTaskForm.getTask());
                taskProgrammingSolution.setAnswerCode(solutionTaskForm.getAnswerCode());

                taskProgrammingSolution.setLanguage(solutionTaskForm.getLanguage());
                addTaskSolutionToTest(taskProgrammingSolution);
            }
        return this;
    }

    private String executeCommand(String command) {
        StringBuilder output = new StringBuilder();
        Process p;
        try {
            p = Runtime.getRuntime().exec(command);
            p.waitFor();
            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(p.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return output.toString();
    }

>>>>>>> addf63146eadb4865c3e88fc9502c025b3871c1e
    public Long secondsToEnd() {
        Timestamp timestamp = Timestamp.valueOf(beginSolution.plusMinutes(test.getTimePerAttempt()));
        Timestamp timestamp1 = Timestamp.valueOf(LocalDateTime.now());
        Long value = timestamp.getTime() - timestamp1.getTime();
<<<<<<< HEAD
        return value / 1000L;
=======
        return value / 1000;
>>>>>>> addf63146eadb4865c3e88fc9502c025b3871c1e
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
