package com.zespolowka.config;

import com.zespolowka.entity.createTest.TaskClosed;
import com.zespolowka.entity.createTest.TaskOpen;
import com.zespolowka.entity.createTest.Test;
import com.zespolowka.entity.solutionTest.SolutionTest;
import com.zespolowka.entity.solutionTest.TaskClosedSolution;
import com.zespolowka.entity.solutionTest.TaskOpenSolution;
import com.zespolowka.entity.user.Role;
import com.zespolowka.entity.user.User;
import com.zespolowka.repository.TestRepository;
import com.zespolowka.repository.UserRepository;
import com.zespolowka.service.inteface.SolutionTestRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

@Configuration
@Profile("!prod")
public class DevDBConfig {
    private static final Logger logger = LoggerFactory.getLogger(DevDBConfig.class);

    @Autowired
    private UserRepository repository;

    @Autowired
    private TestRepository testRepository;

    @Autowired
    private SolutionTestRepository solutionTestRepository;

    @PostConstruct
    public void populateDatabase() {
        logger.info("ładowanie bazy testowej");

        repository.save(new User("Imie1", "Nazwisko1", "aaa1@o2.pl", new BCryptPasswordEncoder().encode("aaa")));
        User user = new User("Admin", "admin", "aaa2@o2.pl", new BCryptPasswordEncoder().encode("1"));
        user.setEnabled(true);
        user.setRole(Role.ADMIN);
        repository.save(user);
        user = new User("SuperAdmin", "superadmin", "aaa3@o2.pl", new BCryptPasswordEncoder().encode("a"));
        user.setRole(Role.SUPERADMIN);
        user.setEnabled(true);
        repository.save(user);

        Test test = new Test("TestBHP", Long.valueOf(3), LocalDate.now().minusWeeks(1), LocalDate.now().plusWeeks(1), new ArrayList<>());
        TaskClosed taskClosed = new TaskClosed("Ssij pałke", 6);
        TreeMap<String, Boolean> answer = new TreeMap<>();
        answer.put("a", false);
        answer.put("b", true);
        answer.put("c", false);
        taskClosed.setAnswers(answer);
        TaskOpen taskOpen = new TaskOpen("Lubisz to ", 10);
        taskOpen.setAnswer("suko");
        test.addTaskToTest(taskClosed);
        test.addTaskToTest(taskOpen);
        test = testRepository.save(test);
        logger.info(test.toString());

        SolutionTest solutionTest = new SolutionTest(test);
        solutionTest.setAttempt(1);
        solutionTest.setBeginSolution(LocalDate.now());
        solutionTest.setEndSolution(LocalDate.now().plusDays(1));

        SolutionTest solutionTest2 = new SolutionTest(test);
        solutionTest2.setAttempt(2);
        solutionTest2.setBeginSolution(LocalDate.now().plusDays(1));
        solutionTest2.setEndSolution(LocalDate.now().plusDays(2));


        TaskClosedSolution taskClosedSolution=new TaskClosedSolution(test.getTasks().get(0));
        taskClosedSolution.setAnswers(answer);
        solutionTest.addTaskSolutionToTest(taskClosedSolution);

        TaskOpenSolution taskOpenSolution=new TaskOpenSolution(test.getTasks().get(1));
        taskOpenSolution.setAnswer("chuju");
        solutionTest.addTaskSolutionToTest(taskOpenSolution);

        answer = new TreeMap<>();
        answer.put("b", false);
        answer.put("a", true);
        answer.put("c", false);
        TaskClosedSolution taskClosedSolution2=new TaskClosedSolution(test.getTasks().get(0));
        taskClosedSolution2.setAnswers(answer);
        solutionTest2.addTaskSolutionToTest(taskClosedSolution2);

        TaskOpenSolution taskOpenSolution2=new TaskOpenSolution(test.getTasks().get(1));
        taskOpenSolution2.setAnswer("suko");
        solutionTest2.addTaskSolutionToTest(taskOpenSolution2);

        solutionTest=solutionTestRepository.save(solutionTest);
        solutionTest2=solutionTestRepository.save(solutionTest2);

        logger.info(test.toString());
        logger.info(solutionTest.toString());
        logger.info(solutionTest2.toString());
    }

}
