package com.zespolowka.config;

import com.zespolowka.entity.Notification;
import com.zespolowka.entity.createTest.TaskClosed;
import com.zespolowka.entity.createTest.TaskOpen;
import com.zespolowka.entity.createTest.Test;
import com.zespolowka.entity.solutionTest.SolutionTest;
import com.zespolowka.entity.solutionTest.TaskClosedSolution;
import com.zespolowka.entity.solutionTest.TaskOpenSolution;
import com.zespolowka.entity.user.Role;
import com.zespolowka.entity.user.User;
import com.zespolowka.repository.NotificationRepository;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
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

    @Autowired
    private NotificationRepository notificationRepository;

    @PostConstruct
    public void populateDatabase() throws ParseException {
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

        SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
        notificationRepository.save(new Notification("Wiadomosc testowa", sdf.parse("31-08-1983 10:20:56"), 1));
        notificationRepository.save(new Notification("Wiad2", sdf.parse("31-08-1984 10:20:56"), 1));
        notificationRepository.save(new Notification("GRUPOWA", sdf.parse("31-08-1985 10:20:56"), Role.USER));
        notificationRepository.save(new Notification("Wiadomosc1", sdf.parse("31-08-1986 10:20:56"), 2));
        notificationRepository.save(new Notification("Wiadomosc2", sdf.parse("31-08-1987 10:20:56"), 2));
        notificationRepository.save(new Notification("Wiadomosc3", sdf.parse("31-08-1988 10:20:56"), 2));
        notificationRepository.save(new Notification("Wiadomosc4", sdf.parse("31-08-1989 10:20:56"), 2));
        //notificationRepository.save(new Notification("Morbi elit ex, tristique vestibulum laoreet id, lobortis non enim. Sed purus elit, fringilla eu vehicula at, egestas sit amet dolor. Morbi tortor nisl, sodales nec luctus vitae, ullamcorper vitae orci. Sed ut dignissim ex", data, 2));
        notificationRepository.save(new Notification("Wiadomosc5", sdf.parse("31-08-1910 10:20:56"), 2));
        notificationRepository.save(new Notification("Wiadomosc6", sdf.parse("31-08-1911 10:20:56"), 2));
        notificationRepository.save(new Notification("Wiadomosc7", sdf.parse("31-08-1912 10:20:56"), 2));

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


        TaskClosedSolution taskClosedSolution = new TaskClosedSolution(test.getTasks().get(0));
        taskClosedSolution.setAnswers(answer);
        solutionTest.addTaskSolutionToTest(taskClosedSolution);

        TaskOpenSolution taskOpenSolution = new TaskOpenSolution(test.getTasks().get(1));
        taskOpenSolution.setAnswer("chuju");
        solutionTest.addTaskSolutionToTest(taskOpenSolution);

        answer = new TreeMap<>();
        answer.put("b", false);
        answer.put("a", true);
        answer.put("c", false);
        TaskClosedSolution taskClosedSolution2 = new TaskClosedSolution(test.getTasks().get(0));
        taskClosedSolution2.setAnswers(answer);
        solutionTest2.addTaskSolutionToTest(taskClosedSolution2);

        TaskOpenSolution taskOpenSolution2 = new TaskOpenSolution(test.getTasks().get(1));
        taskOpenSolution2.setAnswer("suko");
        solutionTest2.addTaskSolutionToTest(taskOpenSolution2);

        solutionTest = solutionTestRepository.save(solutionTest);
        solutionTest2 = solutionTestRepository.save(solutionTest2);

        logger.info(test.toString());
        logger.info(solutionTest.toString());
        logger.info(solutionTest2.toString());
    }

}
