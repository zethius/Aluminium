package com.zespolowka.config;

import com.zespolowka.entity.Notification;
import com.zespolowka.entity.createTest.*;
import com.zespolowka.entity.user.Role;
import com.zespolowka.entity.user.User;
import com.zespolowka.repository.NotificationRepository;
import com.zespolowka.repository.SolutionTestRepository;
import com.zespolowka.repository.TestRepository;
import com.zespolowka.repository.UserRepository;
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

    public DevDBConfig() {
    }

    @PostConstruct
    public void populateDatabase() throws ParseException {
        logger.info("ładowanie bazy testowej");
        User user = new User("Imie1", "Nazwisko1", "aaa1@o2.pl", new BCryptPasswordEncoder().encode("aaa"));
        user.setEnabled(true);
        repository.save(user);
        user = new User("Admin", "admin", "aaa2@o2.pl", new BCryptPasswordEncoder().encode("1"));
        user.setEnabled(true);
        user.setRole(Role.ADMIN);
        repository.save(user);
        user = new User("SuperAdmin", "superadmin", "aaa3@o2.pl", new BCryptPasswordEncoder().encode("a"));
        user.setRole(Role.SUPERADMIN);
        user.setEnabled(true);
        repository.save(user);

        user = new User("Adam", "Małysz", "malysz@o2.pl", new BCryptPasswordEncoder().encode("a"));
        user.setEnabled(false);
        repository.save(user);

        user = new User("Mirek", "Mirecki", "mirecki@o2.pl", new BCryptPasswordEncoder().encode("a"));
        user.setAccountNonLocked(false);
        repository.save(user);

        SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
        notificationRepository.save(new Notification("Wiadomosc testowa", "topic", sdf.parse("31-08-1983 10:20:56"), 1L));
        notificationRepository.save(new Notification("Wiad2", "topic2", sdf.parse("31-08-1984 10:20:56"), 1L));
        notificationRepository.save(new Notification("GRUPOWAADMIN", "topic3", sdf.parse("31-08-1985 10:20:56"), Role.ADMIN));
        notificationRepository.save(new Notification("GRUPOWAUSER", "topic3", sdf.parse("31-08-1985 10:20:56"), Role.USER));
        notificationRepository.save(new Notification("Dla:aaa2 topic4", "topic4", sdf.parse("31-08-1986 10:20:56"), 2L));
        notificationRepository.save(new Notification("aaa2", "topic5", sdf.parse("31-08-1987 10:20:56"), 2L));
        notificationRepository.save(new Notification("Wiadomosc3", "topic6", sdf.parse("31-08-1988 10:20:56"), 1L));
        notificationRepository.save(new Notification("Wiadomosc4", "topic7", sdf.parse("31-08-1989 10:20:56"), 1L));
        //notificationRepository.save(new Notification("Morbi elit ex, tristique vestibulum laoreet id, lobortis non enim. Sed purus elit, fringilla eu vehicula at, egestas sit amet dolor. Morbi tortor nisl, sodales nec luctus vitae, ullamcorper vitae orci. Sed ut dignissim ex", data, 2));
        notificationRepository.save(new Notification("Wiadomosc5", "topic8", sdf.parse("31-08-1910 10:20:56"), 1L));
        notificationRepository.save(new Notification("Wiadaaa2", "topic9", sdf.parse("31-08-1911 10:20:56"), 2L));
        notificationRepository.save(new Notification("Wiadomosc7", "topic10", sdf.parse("31-08-1912 10:20:56"), 2L));

        Test test = new Test("TestBHP", 3L, LocalDate.now().minusWeeks(1L), LocalDate.now().plusWeeks(1L), new ArrayList<>());
        test.setTimePerAttempt(90);
        test.setPassword("");
        TaskClosed taskClosed = new TaskClosed("Ile to jest 2+2*2", 6.0f);
        TreeMap<String, Boolean> answer = new TreeMap<>();
        answer.put("8", false);
        answer.put("6", true);
        answer.put("3*2", true);
        taskClosed.setAnswers(answer);
        test.addTaskToTest(taskClosed);
        taskClosed = new TaskClosed("Ile to jest 2+2+2", 6.0f);
        answer = new TreeMap<>();
        answer.put("8", false);
        answer.put("6", true);
        answer.put("4", false);
        taskClosed.setAnswers(answer);
        test.addTaskToTest(taskClosed);
        taskClosed = new TaskClosed("Ile to jest 3*3*3", 6.0f);
        answer = new TreeMap<>();
        answer.put("27", true);
        answer.put("9", false);
        answer.put("aaa", false);
        taskClosed.setAnswers(answer);
        test.addTaskToTest(taskClosed);
        taskClosed = new TaskClosed("Zaznacz wszystko", 6.0f);
        taskClosed.setCountingType(taskClosed.COUNT_NOT_FULL);
        answer = new TreeMap<>();
        answer.put("1", true);
        answer.put("2", true);
        answer.put("3", true);
        answer.put("4", true);
        answer.put("5", true);
        taskClosed.setAnswers(answer);
        test.addTaskToTest(taskClosed);
        taskClosed = new TaskClosed("Zaznacz imie rozpoczynajace sie na M ", 6.0f);
        answer = new TreeMap<>();
        answer.put("Adam", false);
        answer.put("Ewa", false);
        answer.put("Michal", true);
        taskClosed.setAnswers(answer);
        test.addTaskToTest(taskClosed);
        TaskOpen taskOpen = new TaskOpen("Napisz jak ma na imie Adam Małysz", 10.0f);
        taskOpen.setCaseSens(false);
        taskOpen.setAnswer("Adam");
        test.addTaskToTest(taskOpen);

        taskOpen = new TaskOpen("Podaj pierwsze 5 malych liter alfabetu polskiego", 10.0f);
        taskOpen.setCaseSens(true);
        taskOpen.setAnswer("abcde");
        test.addTaskToTest(taskOpen);

        TaskProgramming taskProgramming = new TaskProgramming("fib", 10.0f);
        TaskProgrammingDetail taskProgrammingDetail = new TaskProgrammingDetail();
        taskProgrammingDetail.setLanguage(ProgrammingLanguages.JAVA);
        taskProgrammingDetail.setTestCode("fib");
        taskProgrammingDetail.setWhiteList("aaa");
        taskProgramming.getProgrammingDetailSet().add(taskProgrammingDetail);
        test.addTaskToTest(taskProgramming);
        test = testRepository.save(test);
        logger.info(test.toString());

        /*SolutionTest solutionTest = new SolutionTest(test);
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
        */
    }

    @Override
    public String toString() {
        return "DevDBConfig{" +
                "repository=" + repository +
                ", testRepository=" + testRepository +
                ", solutionTestRepository=" + solutionTestRepository +
                ", notificationRepository=" + notificationRepository +
                '}';
    }
}
