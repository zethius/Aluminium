package com.zespolowka.service;

import com.zespolowka.entity.createTest.*;
import com.zespolowka.forms.CreateTestForm;
import com.zespolowka.forms.ProgrammingTaskForm;
import com.zespolowka.forms.TaskForm;
import com.zespolowka.repository.TestRepository;
import com.zespolowka.service.inteface.TestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class TestServiceImpl implements TestService {
    private static final Logger logger = LoggerFactory.getLogger(TestService.class);

    private final TestRepository testRepository;

    @Autowired
    public TestServiceImpl(final TestRepository testRepository) {
        this.testRepository = testRepository;
    }


    @Override
    public Test getTestById(final long id) {
        return testRepository.findTestById(id);
    }

    @Override
    public Collection<Test> getAllTests() {
        return testRepository.findAll();
    }

    @Override
    public Test create(final CreateTestForm form) {
        Test test = new Test();
        List<TaskForm> taskFormList = form.getTasks();
        test.setAttempts(Long.valueOf(form.getAttempts()));
        test.setPassword(form.getPassword());
        test.setTimePerAttempt(form.getTimePerAttempt());
        test.setBeginDate(LocalDate.parse(form.getBeginDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        test.setEndDate(LocalDate.parse(form.getEndDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        test.setName(form.getName());
        for (TaskForm taskForm : taskFormList) {
            switch (taskForm.getTaskType()) {
                case 0: {
                    TaskClosed taskClosed = new TaskClosed(taskForm.getQuestion(), (float) taskForm.getPoints());
                    String answerList = taskForm.getAnswer();
                    String[] answers = answerList.split("[\\r\\n]+");
                    for (String answer : answers) {
                        if (answer.startsWith("<*>")) {
                            taskClosed.getAnswers().put(answer.substring(3, answer.length()), true);
                        } else {
                            taskClosed.getAnswers().put(answer, false);
                        }
                    }
                    if (taskForm.getWrongReset()) {
                        taskClosed.setCountingType(taskClosed.WRONG_RESET);
                    } else {
                        taskClosed.setCountingType(taskClosed.COUNT_NOT_FULL);
                    }
                    test.addTaskToTest(taskClosed);
                    break;
                }
                case 1: {
                    TaskOpen taskOpen = new TaskOpen(taskForm.getQuestion(), (float) taskForm.getPoints());
                    taskOpen.setAnswer(taskForm.getAnswer());
                    taskOpen.setCaseSens(taskForm.getCaseSensitivity());
                    test.addTaskToTest(taskOpen);
                    break;
                }
                case 2: {
                    TaskProgramming taskProgramming = new TaskProgramming(taskForm.getQuestion(), (float) taskForm.getPoints());
                    Set<ProgrammingTaskForm> programmingTaskForms = taskForm.getProgrammingTaskForms();
                    programmingTaskForms.stream().filter(ProgrammingTaskForm::getHidden).forEach(programmingTaskForm -> {
                        logger.info(' ' + programmingTaskForm.toString());
                        TaskProgrammingDetail taskProgrammingDetail = new TaskProgrammingDetail();
                        taskProgrammingDetail.setTestCode(programmingTaskForm.getTestCode());
                        taskProgrammingDetail.setWhiteList(programmingTaskForm.getWhiteList());
                        taskProgrammingDetail.setLanguage(ProgrammingLanguages.valueOf(programmingTaskForm.getLanguage()));
                        taskProgrammingDetail.setSolutionClassName(programmingTaskForm.getSolutionClassName());
                        taskProgrammingDetail.setTestClassName(programmingTaskForm.getTestClassName());
                        taskProgramming.addTaskkProgrammingDetail(taskProgrammingDetail);
                    });
                    test.addTaskToTest(taskProgramming);
                    break;
                }
                case 3: {
                    TaskSql taskSql = new TaskSql(taskForm.getQuestion(), (float) taskForm.getPoints());
                    taskSql.setPreparations(taskForm.getPreparations());
                    taskSql.setSqlAnswer(taskForm.getAnswer());
                    test.addTaskToTest(taskSql);

                    break;
                }
            }
        }
        return testRepository.save(test);
    }

    @Override
    public CreateTestForm createForm(Test test) {
        CreateTestForm createTestForm = new CreateTestForm();
        createTestForm.setName(test.getName());
        createTestForm.setPassword(test.getPassword());
        createTestForm.setBeginDate(test.getBeginDate().toString());
        createTestForm.setEndDate(test.getEndDate().toString());
        createTestForm.setAttempts(test.getAttempts().intValue());
        createTestForm.setTimePerAttempt(test.getTimePerAttempt());
        List<TaskForm> taskForms = new ArrayList<>();
        for (Task task : test.getTasks()) {
            TaskForm taskForm = new TaskForm();
            if (task instanceof TaskClosed) {
                taskForm.setTaskType(TaskForm.CLOSEDTASK);
                taskForm.setQuestion(task.getQuestion());
                taskForm.setPoints(task.getMax_points().intValue());
                if (((TaskClosed) task).getCountingType() == TaskClosed.WRONG_RESET) taskForm.setWrongReset(true);
                else taskForm.setCountNotFull(true);
                String answer = "";
                for (Map.Entry<String, Boolean> stringBooleanEntry : ((TaskClosed) task).getAnswers().entrySet()) {
                    answer = answer + stringBooleanEntry.getKey() + "\n";
                }
                taskForm.setAnswer((answer));
            } else if (task instanceof TaskOpen) {
                taskForm.setTaskType(TaskForm.OPENTASK);
                taskForm.setQuestion(task.getQuestion());
                taskForm.setAnswer(((TaskOpen) task).getAnswer());
                taskForm.setPoints(task.getMax_points().intValue());
                taskForm.setCaseSensitivity(((TaskOpen) task).getCaseSens());

            } else if (task instanceof TaskProgramming) {
                taskForm.setTaskType(TaskForm.PROGRAMMINGTASK);
                taskForm.setQuestion(task.getQuestion());
                taskForm.setPoints(task.getMax_points().intValue());
                Set<TaskProgrammingDetail> taskProgrammingDetailSet = ((TaskProgramming) task).getProgrammingDetailSet();
                Set<ProgrammingTaskForm> programmingTaskFormSet = new TreeSet<>();
                Set<String> languages = new TreeSet<>();
                for (TaskProgrammingDetail taskProgrammingDetail : taskProgrammingDetailSet) {
                    ProgrammingTaskForm programmingTaskForm = new ProgrammingTaskForm(taskProgrammingDetail.getLanguage().toString(), true);
                    programmingTaskForm.setTestCode(taskProgrammingDetail.getTestCode());
                    programmingTaskForm.setTestClassName(taskProgrammingDetail.getTestClassName());
                    programmingTaskForm.setSolutionClassName(taskProgrammingDetail.getSolutionClassName());
                    programmingTaskForm.setWhiteList(taskProgrammingDetail.getWhiteList());
                    languages.add(taskProgrammingDetail.getLanguage().toString());
                    programmingTaskFormSet.add(programmingTaskForm);
                }
                for (ProgrammingLanguages lang : ProgrammingLanguages.values()) {
                    if (!languages.contains(lang.name())) {
                        ProgrammingTaskForm programmingTaskForm = new ProgrammingTaskForm(lang.name(), false);
                        programmingTaskFormSet.add(programmingTaskForm);
                    }
                }
                taskForm.setLanguages(languages);
                taskForm.setProgrammingTaskForms(programmingTaskFormSet);
            } else if (task instanceof TaskSql) {
                taskForm.setTaskType(TaskForm.SQLTASK);
                taskForm.setQuestion(task.getQuestion());
                taskForm.setPoints(task.getMax_points().intValue());
                taskForm.setPreparations(((TaskSql) task).getPreparations());
                taskForm.setAnswer(((TaskSql) task).getSqlAnswer());
            }
            taskForms.add(taskForm);
            logger.info(taskForm.toString());
        }
        createTestForm.setTasks(taskForms);
        return createTestForm;
    }

    @Override
    public Collection<Test> getTestByEndDateBefore(LocalDate date) {
        return testRepository.findByEndDateBefore(date);
    }

    @Override
    public String toString() {
        return "TestServiceImpl{" +
                "testRepository=" + testRepository +
                '}';
    }
}
