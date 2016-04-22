package com.zespolowka.service;


import com.zespolowka.entity.createTest.*;
<<<<<<< HEAD
import com.zespolowka.entity.solutionTest.*;
import com.zespolowka.entity.solutionTest.config.SolutionConfig;
=======
import com.zespolowka.entity.solutionTest.SolutionTest;
>>>>>>> addf63146eadb4865c3e88fc9502c025b3871c1e
import com.zespolowka.entity.user.User;
import com.zespolowka.forms.SolutionTaskForm;
import com.zespolowka.forms.SolutionTestForm;
import com.zespolowka.repository.SolutionTestRepository;
import com.zespolowka.service.inteface.SolutionTestService;
<<<<<<< HEAD
import org.apache.commons.io.FileUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
=======
>>>>>>> addf63146eadb4865c3e88fc9502c025b3871c1e
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
<<<<<<< HEAD
import java.io.*;
import java.math.BigDecimal;
import java.math.MathContext;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
=======
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
>>>>>>> addf63146eadb4865c3e88fc9502c025b3871c1e

@Service
public class SolutionTestServiceImpl implements SolutionTestService {
    private static final Logger logger = LoggerFactory.getLogger(SolutionTestService.class);
    private static final String TEST_ATTRIBUTE_NAME = "solutionTestSession";
<<<<<<< HEAD
    private static final String OUTPUT = "output.json";
    private static final String CONFIG = "config.json";
=======
>>>>>>> addf63146eadb4865c3e88fc9502c025b3871c1e

    private final SolutionTestRepository solutionTestRepository;
    private final HttpSession httpSession;

<<<<<<< HEAD
    private int taskNo = 0;

    private String dir = "/home/pitek/zespolowka/skrypty/";

    private String resultDir = "/tmp/";

=======
>>>>>>> addf63146eadb4865c3e88fc9502c025b3871c1e
    @Autowired
    public SolutionTestServiceImpl(SolutionTestRepository solutionTestRepository, HttpSession httpSession) {
        this.solutionTestRepository = solutionTestRepository;
        this.httpSession = httpSession;
    }

    @Override
    public Collection<SolutionTest> getSolutionTestsByUserAndTest(User user, Test test) {
        return solutionTestRepository.findSolutionTestsByUserAndTest(user, test);
    }
<<<<<<< HEAD
=======
    @Override
    public Collection<SolutionTest> getSolutionTestsByUser(User user){
        return solutionTestRepository.findSolutionTestsByUser(user);
    }
>>>>>>> addf63146eadb4865c3e88fc9502c025b3871c1e

    @Override
    public SolutionTest getSolutionTestById(long id) {
        return solutionTestRepository.findSolutionTestById(id);
    }

    @Override
    public Collection<SolutionTest> getAllTests() {
        return solutionTestRepository.findAll();
    }

    @Override
    public SolutionTest create(SolutionTest solutionTest) {
<<<<<<< HEAD
        taskNo = 0;
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/M/d H:m:s");
        LocalDateTime dateTime = LocalDateTime.now();
        solutionTest.setEndSolution(LocalDateTime.parse(dateTime.getYear() + "/" + dateTime.getMonthValue() + '/' + dateTime.getDayOfMonth() + ' ' + dateTime.getHour() + ':' + dateTime.getMinute() + ':' + dateTime.getSecond(), dateTimeFormatter));
        return solutionTestRepository.save(solutionTest);
    }

=======
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/M/d H:m:s");
        LocalDateTime dateTime = LocalDateTime.now();
        solutionTest.setEndSolution(LocalDateTime.parse(dateTime.getYear() + "/" + dateTime.getMonthValue() + "/" + dateTime.getDayOfMonth() + " " + dateTime.getHour() + ":" + dateTime.getMinute() + ":" + dateTime.getSecond(), dateTimeFormatter));
        return solutionTestRepository.save(solutionTest);
    }


>>>>>>> addf63146eadb4865c3e88fc9502c025b3871c1e
    @Override
    public SolutionTestForm createForm(Test test, User user) {
        SolutionTest solutionTest = (SolutionTest) this.httpSession.getAttribute(TEST_ATTRIBUTE_NAME);
        logger.info(test.toString());
        if (solutionTest == null) {
            solutionTest = new SolutionTest(test, user);
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/M/d H:m:s");
            LocalDateTime dateTime = LocalDateTime.now();
<<<<<<< HEAD
            solutionTest.setBeginSolution(LocalDateTime.parse(dateTime.getYear() + "/" + dateTime.getMonthValue() + '/' + dateTime.getDayOfMonth() + ' ' + dateTime.getHour() + ':' + dateTime.getMinute() + ':' + dateTime.getSecond(), dateTimeFormatter));
            solutionTest.setAttempt(getSolutionTestsByUserAndTest(user, test).size() + 1);
            this.httpSession.setAttribute(TEST_ATTRIBUTE_NAME, solutionTest);
            this.taskNo = 0;
=======
            solutionTest.setBeginSolution(LocalDateTime.parse(dateTime.getYear() + "/" + dateTime.getMonthValue() + "/" + dateTime.getDayOfMonth() + " " + dateTime.getHour() + ":" + dateTime.getMinute() + ":" + dateTime.getSecond(), dateTimeFormatter));
            solutionTest.setAttempt(getSolutionTestsByUserAndTest(user, test).size() + 1);
            this.httpSession.setAttribute(TEST_ATTRIBUTE_NAME, solutionTest);
>>>>>>> addf63146eadb4865c3e88fc9502c025b3871c1e
        }
        SolutionTestForm solutionTestForm = new SolutionTestForm();
        solutionTestForm.setName(test.getName());
        solutionTestForm.setTimeToEnd(solutionTest.secondsToEnd());
        List<SolutionTaskForm> solutionTaskFormList = new ArrayList<>();
        List<Task> tasks = test.getTasks();
        Collections.shuffle(tasks);
        test.setTasks(tasks);
<<<<<<< HEAD
        solutionTest.setTest(test);
=======
>>>>>>> addf63146eadb4865c3e88fc9502c025b3871c1e
        for (Task task : test.getTasks()) {
            if (task instanceof TaskClosed) {
                solutionTaskFormList.add(new SolutionTaskForm(task, SolutionTaskForm.CLOSEDTASK));
            } else if (task instanceof TaskOpen) {
                solutionTaskFormList.add(new SolutionTaskForm(task, SolutionTaskForm.OPENTASK));
            } else if (task instanceof TaskProgramming) {
                solutionTaskFormList.add(new SolutionTaskForm(task, SolutionTaskForm.PROGRAMMINGTASK));
<<<<<<< HEAD
            } else if (task instanceof TaskSql) {
                solutionTaskFormList.add(new SolutionTaskForm(task, SolutionTaskForm.SQLTASK));
            }
        }
        solutionTestForm.setTasks(solutionTaskFormList);
        this.httpSession.setAttribute(TEST_ATTRIBUTE_NAME, solutionTest);
        this.taskNo = 0;
        return solutionTestForm;

    }

    public void addTaskSolutionToTest(SolutionTest solutionTest, TaskSolution taskSolution) throws IOException, ParseException {
        taskSolution.setTask(solutionTest.getTest().getTasks().get(taskNo++));
        if (taskSolution instanceof TaskClosedSolution) {
            TaskClosedSolution taskSol = (TaskClosedSolution) taskSolution;
            TaskClosed taskClo = (TaskClosed) taskSol.getTask();
            Map<String, Boolean> userAnswers = taskSol.getAnswers();
            Map<String, Boolean> correctAnswers = taskClo.getAnswers();
            if (taskClo.getCountingType() == taskClo.WRONG_RESET) {
                Boolean theSame = true;
                for (Map.Entry<String, Boolean> stringBooleanEntry : userAnswers.entrySet()) {
                    if ((stringBooleanEntry.getValue() != null && stringBooleanEntry.getValue()) && (!correctAnswers.get(stringBooleanEntry.getKey()))) {
                        theSame = false;
                        break;
                    } else if ((stringBooleanEntry.getValue() == null || !stringBooleanEntry.getValue()) && (correctAnswers.get(stringBooleanEntry.getKey()))) {
                        theSame = false;
                        break;
                    }
                }
                if (theSame) {
                    taskSol.setPoints(taskClo.getMax_points());
                } else taskSol.setPoints(0.0f);
            } else {
                Float pointsDivide = 0.0f;
                Float noCorrectAnswers = 0.0f;
                Boolean chooseIncorect = false;
                for (Map.Entry<String, Boolean> stringBooleanEntry : userAnswers.entrySet()) {
                    if (stringBooleanEntry.getValue() == null) userAnswers.put(stringBooleanEntry.getKey(), false);
                    if (correctAnswers.get(stringBooleanEntry.getKey())) pointsDivide++;
                    if (stringBooleanEntry.getValue() && !correctAnswers.get(stringBooleanEntry.getKey())) {
                        chooseIncorect = true;
                        break;
                    } else if (stringBooleanEntry.getValue().equals(correctAnswers.get(stringBooleanEntry.getKey())) && correctAnswers.get(stringBooleanEntry.getKey()))
                        noCorrectAnswers++;
                }
                if (chooseIncorect || noCorrectAnswers < 1.0F) {
                    taskSol.setPoints(0.0f);
                } else {
                    taskSol.setPoints(taskClo.getMax_points() / (pointsDivide / noCorrectAnswers));
                }
            }
            solutionTest.setPoints(solutionTest.getPoints() + taskSol.getPoints());
            solutionTest.getSolutionTasks().add(taskSol);
        }
        if (taskSolution instanceof TaskOpenSolution) {
            TaskOpenSolution taskSol = (TaskOpenSolution) taskSolution;
            TaskOpen taskOp = (TaskOpen) taskSol.getTask();
            if (!taskOp.getCaseSens()) {
                if (taskSol.getAnswer().toUpperCase().equals(taskOp.getAnswer().toUpperCase())) {
                    solutionTest.setPoints(solutionTest.getPoints() + taskOp.getMax_points());
                    taskSol.setPoints(taskOp.getMax_points());
                } else taskSol.setPoints(0.0f);
            } else {
                if (taskSol.getAnswer().equals(taskOp.getAnswer())) {
                    solutionTest.setPoints(solutionTest.getPoints() + taskOp.getMax_points());
                    taskSol.setPoints(taskOp.getMax_points());
                } else taskSol.setPoints(0.0f);
            }
            solutionTest.getSolutionTasks().add(taskSol);
        }
        if (taskSolution instanceof TaskProgrammingSolution) {
            TaskProgrammingSolution taskSol = (TaskProgrammingSolution) taskSolution;
            TaskProgramming taskProgramming = (TaskProgramming) taskSol.getTask();

            SolutionConfig solutionConfig = new SolutionConfig();
            JSONObject jsonObject;
            String userDirectory = solutionTest.getTest().getName() + '_' + solutionTest.getAttempt() + '_' + solutionTest.getUser().getEmail() + '_' + UUID.randomUUID().toString().substring(0, 4) + '/';

            Set<TaskProgrammingDetail> taskProgrammingDetails = taskProgramming.getProgrammingDetailSet();
            for (TaskProgrammingDetail taskProgrammingDetail : taskProgrammingDetails) {
                if (taskProgrammingDetail.getLanguage().equals(ProgrammingLanguages.JAVA)) {
                    jsonObject = solutionConfig.createJavaConfig(taskProgrammingDetail.getSolutionClassName(), taskProgrammingDetail.getTestClassName(), "restricted_list_java");
                    FileUtils.writeStringToFile(new File(dir + userDirectory + taskProgrammingDetail.getSolutionClassName()), taskSol.getAnswerCode());
                    FileUtils.writeStringToFile(new File(dir + userDirectory + taskProgrammingDetail.getTestClassName()), taskProgrammingDetail.getTestCode());
                    FileUtils.writeStringToFile(new File(dir + userDirectory + "restricted_list_java"), taskProgrammingDetail.getWhiteList());
                    FileUtils.writeStringToFile(new File(dir + userDirectory + CONFIG), jsonObject.toJSONString());
                } else if (taskProgrammingDetail.getLanguage().equals(ProgrammingLanguages.CPP)) {
                    jsonObject = solutionConfig.createCppConfig(taskProgrammingDetail.getSolutionClassName(), taskProgrammingDetail.getTestClassName(), "restricted_list_cpp", "-w");
                    FileUtils.writeStringToFile(new File(dir + userDirectory + taskProgrammingDetail.getSolutionClassName()), taskSol.getAnswerCode());
                    FileUtils.writeStringToFile(new File(dir + userDirectory + taskProgrammingDetail.getTestClassName()), taskProgrammingDetail.getTestCode());
                    FileUtils.writeStringToFile(new File(dir + userDirectory + "restricted_list_cpp"), taskProgrammingDetail.getWhiteList());
                    FileUtils.writeStringToFile(new File(dir + userDirectory + CONFIG), jsonObject.toJSONString());
                } else if (taskProgrammingDetail.getLanguage().equals(ProgrammingLanguages.PYTHON)) {
                    jsonObject = solutionConfig.createPythonConfig(taskProgrammingDetail.getSolutionClassName(), taskProgrammingDetail.getTestClassName(), "restricted_list_python");
                    FileUtils.writeStringToFile(new File(dir + userDirectory + taskProgrammingDetail.getSolutionClassName()), taskSol.getAnswerCode());
                    FileUtils.writeStringToFile(new File(dir + userDirectory + taskProgrammingDetail.getTestClassName()), taskProgrammingDetail.getTestCode());
                    FileUtils.writeStringToFile(new File(dir + userDirectory + "restricted_list_python"), taskProgrammingDetail.getWhiteList());
                    FileUtils.writeStringToFile(new File(dir + userDirectory + CONFIG), jsonObject.toJSONString());
                }
            }
            executeCommand("ruby " + dir + "skrypt.rb \"" + dir + "\" \"" + userDirectory + "\"");

            JSONParser parser = new JSONParser();
            Object result = parser.parse(new FileReader(resultDir + userDirectory + OUTPUT));
            jsonObject = (JSONObject) result;
            if (jsonObject.get("time") != null) {
                BigDecimal all = BigDecimal.valueOf((Long) jsonObject.get("all"));
                BigDecimal passed = BigDecimal.valueOf((Long) jsonObject.get("passed"));
                BigDecimal time = BigDecimal.valueOf((Double) jsonObject.get("time"));
                BigDecimal resultTest = (passed.divide(all, MathContext.DECIMAL128).setScale(2)); //TODO dodac czas rozwiazania do statystyk
                BigDecimal points = resultTest.multiply(BigDecimal.valueOf(taskSol.getTask().getMax_points())).setScale(2);
                taskSol.setPoints(points.floatValue());
                solutionTest.setPoints(solutionTest.getPoints() + points.floatValue());
            } else {
                logger.info("blad kompilacji itp, obrobic to potem");
                logger.info(jsonObject.toJSONString());
                taskSol.setPoints(0f);
            }
            solutionTest.getSolutionTasks().add(taskSol);
        }
        if (taskSolution instanceof TaskSqlSolution) {
            TaskSqlSolution taskSqlSolution = (TaskSqlSolution) taskSolution;
            TaskSql taskSql = (TaskSql) taskSqlSolution.getTask();
            SolutionConfig solutionConfig = new SolutionConfig();
            JSONObject jsonObject;
            JSONObject source = new JSONObject();
            source.put("task0", taskSqlSolution.getAnswer());
            JSONObject tests = new JSONObject();
            JSONArray array = new JSONArray();
            array.add("type0");
            array.add(taskSql.getSqlAnswer());
            tests.put("task0", array);
            String userDirectory = solutionTest.getTest().getName() + '_' + solutionTest.getAttempt() + '_' + solutionTest.getUser().getEmail() + '_' + UUID.randomUUID().toString().substring(0, 4) + '/';
            jsonObject = solutionConfig.createSqlConfig("sources.json", "preparations.txt", "tests.json", "restricted_list_sql");
            FileUtils.writeStringToFile(new File(dir + userDirectory + "tests.json"), tests.toJSONString());
            FileUtils.writeStringToFile(new File(dir + userDirectory + "sources.json"), source.toJSONString());
            FileUtils.writeStringToFile(new File(dir + userDirectory + "preparations.txt"), taskSql.getPreparations());
            FileUtils.writeStringToFile(new File(dir + userDirectory + "restricted_list_sql"), "drop");
            FileUtils.writeStringToFile(new File(dir + userDirectory + CONFIG), jsonObject.toJSONString());
            logger.info("ugabuga");
            executeCommand("ruby " + dir + "skrypt.rb \"" + dir + "\" \"" + userDirectory + "\"");

            JSONParser parser = new JSONParser();
            Object result = parser.parse(new FileReader(resultDir + userDirectory + OUTPUT));
            jsonObject = (JSONObject) result;
            if (jsonObject.get("time") != null) {
                BigDecimal all = BigDecimal.valueOf((Long) jsonObject.get("all"));
                BigDecimal passed = BigDecimal.valueOf((Long) jsonObject.get("passed"));
                BigDecimal resultTest = (passed.divide(all, MathContext.DECIMAL128).setScale(2));
                BigDecimal points = resultTest.multiply(BigDecimal.valueOf(taskSqlSolution.getTask().getMax_points())).setScale(2);
                taskSqlSolution.setPoints(points.floatValue());
                solutionTest.setPoints(solutionTest.getPoints() + points.floatValue());
            } else {
                logger.info("blad kompilacji itp, obrobic to potem");
                logger.info(jsonObject.toJSONString());
                taskSqlSolution.setPoints(0f);
            }
            solutionTest.getSolutionTasks().add(taskSqlSolution);
        }
    }

    public SolutionTest create(SolutionTest solutionTest, SolutionTestForm solutionTestForm) throws IOException, ParseException {
        List<SolutionTaskForm> solutionTaskForms = solutionTestForm.getTasks();
        this.taskNo = 0;
        for (SolutionTaskForm solutionTaskForm : solutionTaskForms)
            if (solutionTaskForm.getTaskType() == SolutionTaskForm.CLOSEDTASK) {
                TaskClosedSolution taskClosedSolution = new TaskClosedSolution(solutionTaskForm.getTask());
                TreeMap<String, Boolean> answers = new TreeMap<>();
                answers.putAll(solutionTaskForm.getAnswers());
                taskClosedSolution.setAnswers(answers);
                addTaskSolutionToTest(solutionTest, taskClosedSolution);
            } else if (solutionTaskForm.getTaskType() == SolutionTaskForm.OPENTASK) {
                TaskOpenSolution taskOpenSolution = new TaskOpenSolution(solutionTaskForm.getTask());
                taskOpenSolution.setAnswer(solutionTaskForm.getAnswer());
                addTaskSolutionToTest(solutionTest, taskOpenSolution);
            } else if (solutionTaskForm.getTaskType() == SolutionTaskForm.PROGRAMMINGTASK) {
                logger.info(solutionTaskForm.toString());
                TaskProgrammingSolution taskProgrammingSolution = new TaskProgrammingSolution(solutionTaskForm.getTask());
                taskProgrammingSolution.setAnswerCode(solutionTaskForm.getAnswerCode());
                taskProgrammingSolution.setLanguage(solutionTaskForm.getLanguage());
                addTaskSolutionToTest(solutionTest, taskProgrammingSolution);
            } else if (solutionTaskForm.getTaskType() == SolutionTaskForm.SQLTASK) {
                logger.info(solutionTaskForm.toString());
                TaskSqlSolution taskSqlSolution = new TaskSqlSolution(solutionTaskForm.getTask());
                taskSqlSolution.setAnswer(solutionTaskForm.getAnswerCode());
                addTaskSolutionToTest(solutionTest, taskSqlSolution);
            }
        return solutionTest;
    }

    @Override
    public Collection<SolutionTest> getSolutionTestsByUser(User user) {
        return solutionTestRepository.findSolutionTestsByUser(user);
    }

    public String executeCommand(String command) {
        logger.info(command);
        StringBuilder output = new StringBuilder();
        Process p;
        try {
            p = Runtime.getRuntime().exec(command);
            p.waitFor();
            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(p.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append('\n');
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return output.toString();
    }

    @Override
    public String toString() {
        return "SolutionTestServiceImpl{" +
                "solutionTestRepository=" + solutionTestRepository +
                ", taskNo=" + taskNo +
                ", dir='" + dir + '\'' +
                ", resultDir='" + resultDir + '\'' +
                '}';
=======
            }
        }
        solutionTestForm.setTasks(solutionTaskFormList);

        return solutionTestForm;

>>>>>>> addf63146eadb4865c3e88fc9502c025b3871c1e
    }
}
