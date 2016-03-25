package com.zespolowka.service;

import com.zespolowka.entity.createTest.TaskClosed;
import com.zespolowka.entity.createTest.TaskOpen;
import com.zespolowka.entity.createTest.TaskProgramming;
import com.zespolowka.entity.createTest.Test;
import com.zespolowka.forms.CreateTestForm;
import com.zespolowka.forms.TaskForm;
import com.zespolowka.repository.TestRepository;
import com.zespolowka.service.inteface.TestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.List;

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
        return (Collection<Test>) testRepository.findAll();
    }

    /*
    TODO Zmienic maxPoints jak powstanie w widoku
     */
    @Override
    public Test create(final CreateTestForm form) {
        Test test = new Test();
        List<TaskForm> taskFormList = form.getTasks();
        test.setAttempts(Long.valueOf(form.getAttempts()));
        test.setBeginDate(LocalDate.parse(form.getBeginDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        test.setEndDate(LocalDate.parse(form.getEndDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        test.setName(form.getName());
        for (TaskForm taskForm : taskFormList) {
            switch (taskForm.getTaskType()) {
                case 0: {
                    TaskClosed taskClosed = new TaskClosed(taskForm.getQuestion(), 1);
                    String answerList = taskForm.getAnswer();
                    String[] answers = answerList.split("[\\r\\n]+");
                    for (String answer : answers) {
                        if (answer.startsWith("<*>")) {
                            taskClosed.getAnswers().put(answer.substring(3, answer.length()),true);
                        } else {
                            taskClosed.getAnswers().put(answer,false);
                        }
                    }
                    test.addTaskToTest(taskClosed);
                    break;
                }
                case 1: {
                    TaskOpen taskOpen = new TaskOpen(taskForm.getQuestion(), 1);
                    taskOpen.setAnswer(taskForm.getAnswer());
                    test.addTaskToTest(taskOpen);
                    break;
                }
                case 2: {
                    TaskProgramming taskProgramming = new TaskProgramming(taskForm.getQuestion(), 1);
                    taskProgramming.setTestCode(taskForm.getTestCode());
                    test.addTaskToTest(taskProgramming);
                    break;
                }
            }
        }
        return testRepository.save(test);
    }
}
