package com.zespolowka.service;


import com.zespolowka.entity.createTest.*;
import com.zespolowka.entity.solutionTest.*;
import com.zespolowka.entity.solutionTest.config.SolutionConfig;
import com.zespolowka.entity.user.User;
import com.zespolowka.forms.NewMessageForm;
import com.zespolowka.forms.SolutionTaskForm;
import com.zespolowka.forms.SolutionTestForm;
import com.zespolowka.repository.SolutionTestRepository;
import com.zespolowka.service.inteface.NotificationService;
import com.zespolowka.service.inteface.SolutionTestService;
import com.zespolowka.service.inteface.UserService;
import org.apache.commons.io.FileUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.io.*;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class SolutionTestServiceImpl implements SolutionTestService {
    private static final Logger logger = LoggerFactory.getLogger(SolutionTestService.class);
    private static final String TEST_ATTRIBUTE_NAME = "solutionTestSession";
    private static final String OUTPUT = "output.json";
    private static final String CONFIG = "config.json";

    private final SolutionTestRepository solutionTestRepository;
    private final HttpSession httpSession;
    private final NotificationService notificationService;
    private final UserService userService;
    private int taskNo = 0;

    private String dir = "/home/pitek/zespolowka/skrypty/";

    private String resultDir = "/tmp/";

    @Autowired
    public SolutionTestServiceImpl(SolutionTestRepository solutionTestRepository, HttpSession httpSession, NotificationService notificationService, UserService userService) {
        this.solutionTestRepository = solutionTestRepository;
        this.httpSession = httpSession;
        this.notificationService = notificationService;
        this.userService=userService;
    }

    @Override
    public List<SolutionTest> getSolutionsWithTheBestResult(User user) {
        return solutionTestRepository.getSolutionsWithTheBestResult(user);
    }

    @Override
    public Collection<SolutionTest> getSolutionTestsByUserAndTest(User user, Test test) {
        return solutionTestRepository.findSolutionTestsByUserAndTest(user, test);
    }

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
        taskNo = 0;
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/M/d H:m:s");
        LocalDateTime dateTime = LocalDateTime.now();
        solutionTest.setEndSolution(LocalDateTime.parse(dateTime.getYear() + "/" + dateTime.getMonthValue() + '/' + dateTime.getDayOfMonth() + ' ' + dateTime.getHour() + ':' + dateTime.getMinute() + ':' + dateTime.getSecond(), dateTimeFormatter));
        return solutionTestRepository.save(solutionTest);
    }

    @Override
    public SolutionTestForm createForm(Test test, User user) {
        SolutionTest solutionTest = (SolutionTest) this.httpSession.getAttribute(TEST_ATTRIBUTE_NAME);
        if (solutionTest == null) {
            solutionTest = new SolutionTest(test, user);
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/M/d H:m:s");
            LocalDateTime dateTime = LocalDateTime.now();
            solutionTest.setBeginSolution(LocalDateTime.parse(dateTime.getYear() + "/" + dateTime.getMonthValue() + '/' + dateTime.getDayOfMonth() + ' ' + dateTime.getHour() + ':' + dateTime.getMinute() + ':' + dateTime.getSecond(), dateTimeFormatter));
            solutionTest.setAttempt(getSolutionTestsByUserAndTest(user, test).size() + 1);
            this.httpSession.setAttribute(TEST_ATTRIBUTE_NAME, solutionTest);
            this.taskNo = 0;
        }
        SolutionTestForm solutionTestForm = new SolutionTestForm();
        solutionTestForm.setName(test.getName());
        solutionTestForm.setTimeToEnd(solutionTest.secondsToEnd());
        List<SolutionTaskForm> solutionTaskFormList = new ArrayList<>();
        List<Task> tasks = test.getTasks();
        Collections.shuffle(tasks);
        test.setTasks(tasks);
        solutionTest.setTest(test);
        for (Task task : test.getTasks()) {
            if (task instanceof TaskClosed) {
                solutionTaskFormList.add(new SolutionTaskForm(task, SolutionTaskForm.CLOSEDTASK));
            } else if (task instanceof TaskOpen) {
                solutionTaskFormList.add(new SolutionTaskForm(task, SolutionTaskForm.OPENTASK));
            } else if (task instanceof TaskProgramming) {
                solutionTaskFormList.add(new SolutionTaskForm(task, SolutionTaskForm.PROGRAMMINGTASK));
            } else if (task instanceof TaskSql) {
                solutionTaskFormList.add(new SolutionTaskForm(task, SolutionTaskForm.SQLTASK));
            }
        }
        solutionTestForm.setTasks(solutionTaskFormList);
        this.httpSession.setAttribute(TEST_ATTRIBUTE_NAME, solutionTest);
        this.taskNo = 0;
        return solutionTestForm;

    }

    public void addTaskSolutionToTest(SolutionTest solutionTest, TaskSolution taskSolution) throws IOException, ParseException, FileNotFoundException {
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
            String userDirectory = solutionTest.getTest().getName() + '_' + solutionTest.getAttempt() + '_' + solutionTest.getUser().getId() + '_' + UUID.randomUUID().toString().substring(0, 4) + '/';
            userDirectory = userDirectory.replaceAll(" ", "");
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
                BigDecimal resultTest = (passed.divide(all, MathContext.DECIMAL128).setScale(4, RoundingMode.HALF_UP)); //TODO dodac czas rozwiazania do statystyk
                BigDecimal points = resultTest.multiply(BigDecimal.valueOf(taskSol.getTask().getMax_points())).setScale(2);
                taskSol.setPoints(points.floatValue());
                solutionTest.setPoints(solutionTest.getPoints() + points.floatValue());
            } else {
                CompilationError compilationError = new CompilationError();

                for (CompilationErrorTypes type : CompilationErrorTypes.values()) {
                    if (jsonObject.get(type.getValue()) != null) {
                        compilationError.setType(type);
                        compilationError.setError(jsonObject.get(type.getValue()).toString());
                    }
                }
                taskSol.setCompilationError(compilationError);
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
            source.put("task0", taskSqlSolution.getSqlAnswer());
            JSONObject tests = new JSONObject();
            JSONArray array = new JSONArray();
            array.add("type0");
            array.add(taskSql.getSqlAnswer());
            tests.put("task0", array);
            String userDirectory = solutionTest.getTest().getName() + '_' + solutionTest.getAttempt() + '_' + solutionTest.getUser().getId() + '_' + UUID.randomUUID().toString().substring(0, 4) + '/';
            userDirectory = userDirectory.replaceAll(" ", "");
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
                BigDecimal resultTest = (passed.divide(all, MathContext.DECIMAL128).setScale(4, RoundingMode.HALF_UP));
                BigDecimal points = resultTest.multiply(BigDecimal.valueOf(taskSqlSolution.getTask().getMax_points())).setScale(2);
                taskSqlSolution.setPoints(points.floatValue());
                solutionTest.setPoints(solutionTest.getPoints() + points.floatValue());
            } else {
                CompilationError compilationError = new CompilationError();

                for (CompilationErrorTypes type : CompilationErrorTypes.values()) {
                    if (jsonObject.get(type.getValue()) != null) {
                        compilationError.setType(type);
                        compilationError.setError(jsonObject.get(type.getValue()).toString());
                    }
                }
                taskSqlSolution.setCompilationError(compilationError);
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
                TaskProgrammingSolution taskProgrammingSolution = new TaskProgrammingSolution(solutionTaskForm.getTask());
                taskProgrammingSolution.setAnswerCode(solutionTaskForm.getAnswerCode());
                taskProgrammingSolution.setLanguage(solutionTaskForm.getLanguage());
                addTaskSolutionToTest(solutionTest, taskProgrammingSolution);
            } else if (solutionTaskForm.getTaskType() == SolutionTaskForm.SQLTASK) {
                logger.info(solutionTaskForm.toString());
                TaskSqlSolution taskSqlSolution = new TaskSqlSolution(solutionTaskForm.getTask());
                taskSqlSolution.setSqlAnswer(solutionTaskForm.getAnswerCode());
                addTaskSolutionToTest(solutionTest, taskSqlSolution);
            }
        ResourceBundle messages = ResourceBundle.getBundle("messages", LocaleContextHolder.getLocale());
        NewMessageForm newMessageForm = new NewMessageForm();
        newMessageForm.setReceivers(solutionTest.getUser().getEmail());
        newMessageForm.setTopic(messages.getString("results.topic") + " " + solutionTest.getTest().getName());
        newMessageForm.setMessage(messages.getString("results.message") + " " + solutionTest.getPoints() + " / " + solutionTest.getTest().getMaxPoints());
        User system = userService.getUserById(1)
                .orElseThrow(() -> new NoSuchElementException(String.format("Uzytkownik o id =%s nie istnieje",1)));
        logger.info("SYS:"+system);
        newMessageForm.setSender(system);
        notificationService.sendMessage(newMessageForm);
        return solutionTest;
    }

    @Override
    public Collection<SolutionTest> getSolutionTestsByUser(User user) {
        return solutionTestRepository.findSolutionTestsByUser(user);
    }

    @Override
    public Collection<SolutionTest> getSolutionTestsByTest(Test test) {
        return solutionTestRepository.findSolutionTestsByTest(test);
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

        } catch (InterruptedException e) {
            e.printStackTrace();
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
    }
}
