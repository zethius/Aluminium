package com.zespolowka.service;


import com.zespolowka.entity.createTest.*;
import com.zespolowka.entity.solutionTest.*;
import com.zespolowka.entity.solutionTest.config.SolutionConfig;
import com.zespolowka.entity.user.User;
import com.zespolowka.forms.SolutionTaskForm;
import com.zespolowka.forms.SolutionTestForm;
import com.zespolowka.repository.SolutionTestRepository;
import com.zespolowka.service.inteface.SolutionTestService;
import org.apache.commons.io.FileUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.io.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class SolutionTestServiceImpl implements SolutionTestService {
    private static final Logger logger = LoggerFactory.getLogger(SolutionTestService.class);
    private static final String TEST_ATTRIBUTE_NAME = "solutionTestSession";

    private final SolutionTestRepository solutionTestRepository;
    private final HttpSession httpSession;

    private int taskNo = 0;

    private String dir = "/home/pitek/zespolowka/skrypty/";

    private String resultDir = "/tmp/";

    @Autowired
    public SolutionTestServiceImpl(SolutionTestRepository solutionTestRepository, HttpSession httpSession) {
        this.solutionTestRepository = solutionTestRepository;
        this.httpSession = httpSession;
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
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/M/d H:m:s");
        LocalDateTime dateTime = LocalDateTime.now();
        solutionTest.setEndSolution(LocalDateTime.parse(dateTime.getYear() + "/" + dateTime.getMonthValue() + '/' + dateTime.getDayOfMonth() + ' ' + dateTime.getHour() + ':' + dateTime.getMinute() + ':' + dateTime.getSecond(), dateTimeFormatter));
        return solutionTestRepository.save(solutionTest);
    }

    @Override
    public SolutionTestForm createForm(Test test, User user) {
        SolutionTest solutionTest = (SolutionTest) this.httpSession.getAttribute(TEST_ATTRIBUTE_NAME);
        logger.info(test.toString());
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
                    jsonObject = solutionConfig.createJavaConfig("Dodawanie.java", "MyTests.java", "allowed_list_path");
                    FileUtils.writeStringToFile(new File(dir + userDirectory + "Dodawanie.java"), taskSol.getAnswerCode());
                    FileUtils.writeStringToFile(new File(dir + userDirectory + "MyTests.java"), taskProgrammingDetail.getTestCode());
                    FileUtils.writeStringToFile(new File(dir + userDirectory + "allowed_list_java"), taskProgrammingDetail.getWhiteList());
                    FileUtils.writeStringToFile(new File(dir + userDirectory + "config.json"), jsonObject.toJSONString());
                }
            }
            executeCommand(dir + "skrypt.rb \"" + dir + " \""+userDirectory+"\"");
            /*Socket socket = new Socket("localhost", 54321);
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
            printWriter.println("\"" + userDirectory + "\"");
            printWriter.close();
            socket.close();
*/
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

            solutionTest.setPoints(solutionTest.getPoints() + taskProgramming.getMax_points());
            taskSol.setPoints(taskProgramming.getMax_points());
            solutionTest.getSolutionTasks().add(taskSol);
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
            }
        return solutionTest;
    }

    public String executeCommand(String command) {
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
                ", httpSession=" + httpSession +
                ", taskNo=" + taskNo +
                ", dir='" + dir + '\'' +
                ", resultDir='" + resultDir + '\'' +
                '}';
    }
}
