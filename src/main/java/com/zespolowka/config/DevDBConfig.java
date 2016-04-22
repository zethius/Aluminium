package com.zespolowka.config;

import com.zespolowka.entity.Notification;
import com.zespolowka.entity.createTest.*;
<<<<<<< HEAD
import com.zespolowka.entity.solutionTest.SolutionTest;
import com.zespolowka.entity.solutionTest.TaskClosedSolution;
=======
>>>>>>> addf63146eadb4865c3e88fc9502c025b3871c1e
import com.zespolowka.entity.user.Role;
import com.zespolowka.entity.user.User;
import com.zespolowka.repository.NotificationRepository;
import com.zespolowka.repository.SolutionTestRepository;
import com.zespolowka.repository.TestRepository;
import com.zespolowka.repository.UserRepository;
<<<<<<< HEAD
import com.zespolowka.service.inteface.SolutionTestService;
import com.zespolowka.service.inteface.VerificationTokenService;
=======
>>>>>>> addf63146eadb4865c3e88fc9502c025b3871c1e
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.annotation.PostConstruct;
<<<<<<< HEAD
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Random;
import java.util.TreeMap;
import java.util.UUID;
=======
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.TreeMap;
>>>>>>> addf63146eadb4865c3e88fc9502c025b3871c1e

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

<<<<<<< HEAD
    @Autowired
    private VerificationTokenService verificationTokenService;

    @Autowired
    private SolutionTestService solutionTestService;

    private User user;

    private SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");

    public DevDBConfig() {
    }

    @PostConstruct
    public void populateDatabase() throws ParseException {
        logger.info("ładowanie bazy testowej");
        User user = new User("Uzytkownik", "Ambitny", "aaa1@o2.pl", new BCryptPasswordEncoder().encode("aaa"));
=======
    @PostConstruct
    public void populateDatabase() throws ParseException {
        logger.info("ładowanie bazy testowej");
        User user = new User("Imie1", "Nazwisko1", "aaa1@o2.pl", new BCryptPasswordEncoder().encode("aaa"));
>>>>>>> addf63146eadb4865c3e88fc9502c025b3871c1e
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

<<<<<<< HEAD

        notificationRepository.save(new Notification("Wiadomosc testowa", "topic", sdf.parse("31-08-1983 10:20:56"), 1L));
        notificationRepository.save(new Notification("Wiad2", "topic2", sdf.parse("31-08-1984 10:20:56"), 1L));
        notificationRepository.save(new Notification("GRUPOWAADMIN", "topic3", sdf.parse("31-08-1985 10:20:56"), Role.ADMIN));
        notificationRepository.save(new Notification("GRUPOWASUPERADMIN", "topic3", sdf.parse("31-08-1985 10:20:56"), Role.SUPERADMIN));
        notificationRepository.save(new Notification("GRUPOWAUSER", "topic3", sdf.parse("31-08-1985 10:20:56"), Role.USER));
        notificationRepository.save(new Notification("Dla:aaa2 topic4", "topic4", sdf.parse("31-08-1986 10:20:56"), 2L));
        notificationRepository.save(new Notification("aaa2", "topic5", sdf.parse("31-08-1987 10:20:56"), 2L));
        notificationRepository.save(new Notification("Wiadomosc3", "topic6", sdf.parse("31-08-1988 10:20:56"), 1L));
        notificationRepository.save(new Notification("Wiadomosc4", "topic7", sdf.parse("31-08-1989 10:20:56"), 1L));
        notificationRepository.save(new Notification("Morbi elit ex, tristique vestibulum laoreet id, lobortis non enim. Sed purus elit, fringilla eu vehicula at, egestas sit amet dolor. Morbi tortor nisl, sodales nec luctus vitae, ullamcorper vitae orci. Sed ut dignissim ex", "Dluga wiadomosc", sdf.parse("31-08-2015 10:20:56"), 2));
        notificationRepository.save(new Notification("Wiadomosc5", "topic8", sdf.parse("31-08-1910 10:20:56"), 1L));
        notificationRepository.save(new Notification("Wiadaaa2", "topic9", sdf.parse("31-08-1911 10:20:56"), 2L));
        notificationRepository.save(new Notification("Wiadomosc7", "topic10", sdf.parse("31-08-1912 10:20:56"), 2L));

        createTests();
        logger.info("Testy stworzone");
        /*createFakeUsers();
        logger.info("Userzy stworzeni");
        createFakeNotEnabledUsers();
        logger.info("Userzy nieaktywni stworzeni");
        createFakeLockedUsers();
        logger.info("Userzy zablokowani stworzeni");
        createFakeNotifications();
        logger.info("Wiadomosci stworzone");
        createFakeTestsAndSolutionTestsWithClosedTask();
        logger.info("Testy z rozwiazaniami stworzone");*/
    }

    public void createTests() {
        Test test = new Test("TestBHP", 3L, LocalDate.now().minusWeeks(1L), LocalDate.now().plusWeeks(1L), new ArrayList<>());
        test.setTimePerAttempt(90);
        test.setPassword("");
        TaskClosed taskClosed = new TaskClosed("Ile to jest 2+2*2", 6.0f);
=======
        SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
        notificationRepository.save(new Notification("Wiadomosc testowa", "topic", sdf.parse("31-08-1983 10:20:56"), 1));
        notificationRepository.save(new Notification("Wiad2", "topic2", sdf.parse("31-08-1984 10:20:56"), 1));
        notificationRepository.save(new Notification("GRUPOWAADMIN", "topic3", sdf.parse("31-08-1985 10:20:56"), Role.ADMIN));
        notificationRepository.save(new Notification("GRUPOWAUSER", "topic3", sdf.parse("31-08-1985 10:20:56"), Role.USER));
        notificationRepository.save(new Notification("Dla:aaa2 topic4", "topic4", sdf.parse("31-08-1986 10:20:56"), 2));
        notificationRepository.save(new Notification("aaa2", "topic5", sdf.parse("31-08-1987 10:20:56"), 2));
        notificationRepository.save(new Notification("Wiadomosc3", "topic6", sdf.parse("31-08-1988 10:20:56"), 1));
        notificationRepository.save(new Notification("Wiadomosc4", "topic7", sdf.parse("31-08-1989 10:20:56"), 1));
        //notificationRepository.save(new Notification("Morbi elit ex, tristique vestibulum laoreet id, lobortis non enim. Sed purus elit, fringilla eu vehicula at, egestas sit amet dolor. Morbi tortor nisl, sodales nec luctus vitae, ullamcorper vitae orci. Sed ut dignissim ex", data, 2));
        notificationRepository.save(new Notification("Wiadomosc5", "topic8", sdf.parse("31-08-1910 10:20:56"), 1));
        notificationRepository.save(new Notification("Wiadaaa2", "topic9", sdf.parse("31-08-1911 10:20:56"), 2));
        notificationRepository.save(new Notification("Wiadomosc7", "topic10", sdf.parse("31-08-1912 10:20:56"), 2));

        com.zespolowka.entity.createTest.Test test = new com.zespolowka.entity.createTest.Test("TestBHP", 3L, LocalDate.now().minusWeeks(1), LocalDate.now().plusWeeks(1), new ArrayList<>());
        test.setTimePerAttempt(90);
        test.setPassword("");
        com.zespolowka.entity.createTest.TaskClosed taskClosed = new com.zespolowka.entity.createTest.TaskClosed("Ile to jest 2+2*2", 6f);
>>>>>>> addf63146eadb4865c3e88fc9502c025b3871c1e
        TreeMap<String, Boolean> answer = new TreeMap<>();
        answer.put("8", false);
        answer.put("6", true);
        answer.put("3*2", true);
        taskClosed.setAnswers(answer);
        test.addTaskToTest(taskClosed);
<<<<<<< HEAD
        taskClosed = new TaskClosed("Ile to jest 2+2+2", 6.0f);
=======
        taskClosed = new com.zespolowka.entity.createTest.TaskClosed("Ile to jest 2+2+2", 6f);
>>>>>>> addf63146eadb4865c3e88fc9502c025b3871c1e
        answer = new TreeMap<>();
        answer.put("8", false);
        answer.put("6", true);
        answer.put("4", false);
        taskClosed.setAnswers(answer);
        test.addTaskToTest(taskClosed);
<<<<<<< HEAD
        taskClosed = new TaskClosed("Ile to jest 3*3*3", 6.0f);
=======
        taskClosed = new com.zespolowka.entity.createTest.TaskClosed("Ile to jest 3*3*3", 6f);
>>>>>>> addf63146eadb4865c3e88fc9502c025b3871c1e
        answer = new TreeMap<>();
        answer.put("27", true);
        answer.put("9", false);
        answer.put("aaa", false);
        taskClosed.setAnswers(answer);
        test.addTaskToTest(taskClosed);
<<<<<<< HEAD
        taskClosed = new TaskClosed("Zaznacz wszystko", 6.0f);
=======
        taskClosed = new com.zespolowka.entity.createTest.TaskClosed("Zaznacz wszystko", 6f);
>>>>>>> addf63146eadb4865c3e88fc9502c025b3871c1e
        taskClosed.setCountingType(taskClosed.COUNT_NOT_FULL);
        answer = new TreeMap<>();
        answer.put("1", true);
        answer.put("2", true);
        answer.put("3", true);
        answer.put("4", true);
        answer.put("5", true);
        taskClosed.setAnswers(answer);
        test.addTaskToTest(taskClosed);
<<<<<<< HEAD
        taskClosed = new TaskClosed("Zaznacz imie rozpoczynajace sie na M ", 6.0f);
=======
        taskClosed = new com.zespolowka.entity.createTest.TaskClosed("Zaznacz imie rozpoczynajace sie na M ", 6f);
>>>>>>> addf63146eadb4865c3e88fc9502c025b3871c1e
        answer = new TreeMap<>();
        answer.put("Adam", false);
        answer.put("Ewa", false);
        answer.put("Michal", true);
        taskClosed.setAnswers(answer);
        test.addTaskToTest(taskClosed);
<<<<<<< HEAD
        TaskOpen taskOpen = new TaskOpen("Napisz jak ma na imie Adam Małysz", 10.0f);
=======
        com.zespolowka.entity.createTest.TaskOpen taskOpen = new com.zespolowka.entity.createTest.TaskOpen("Napisz jak ma na imie Adam Małysz", 10f);
>>>>>>> addf63146eadb4865c3e88fc9502c025b3871c1e
        taskOpen.setCaseSens(false);
        taskOpen.setAnswer("Adam");
        test.addTaskToTest(taskOpen);

<<<<<<< HEAD
        taskOpen = new TaskOpen("Podaj pierwsze 5 malych liter alfabetu polskiego", 10.0f);
        taskOpen.setCaseSens(true);
        taskOpen.setAnswer("abcde");
        test.addTaskToTest(taskOpen);
        test = testRepository.save(test);
        Test test2 = new Test("KurzeTesty", 10L, LocalDate.now().minusWeeks(1L), LocalDate.now().plusWeeks(1L), new ArrayList<>());
        TaskProgramming taskProgramming = new TaskProgramming("Zadanie z Javy", 100.0f);
        TaskProgrammingDetail taskProgrammingDetail = new TaskProgrammingDetail();
        taskProgrammingDetail.setLanguage(ProgrammingLanguages.JAVA);
        taskProgrammingDetail.setTestCode("import static org.junit.Assert.*;\n" +
                "import org.junit.Test;\n" +
                "\n" +
                "public class MyTests {\n" +
                "\n" +
                "\t@Test\n" +
                "\tpublic void test1() {\n" +
                "\t\tDodawanie dodaj = new Dodawanie();\n" +
                "\t\tassertEquals(4,dodaj.my_add(2,3));\n" +
                "\t}\n" +
                "\t@Test\n" +
                "\tpublic void test2(){\n" +
                "\t\tDodawanie dodaj = new Dodawanie();\n" +
                "\t\tassertEquals(5,dodaj.my_add(5,0));\n" +
                "\t}\n" +
                "\t@Test\n" +
                "\tpublic void test3(){\n" +
                "\t\tDodawanie dodaj = new Dodawanie();\n" +
                "\t\tassertEquals(3,dodaj.my_add(4, 3));\n" +
                "\t}\n" +
                "\n" +
                "\t@Test\n" +
                "\tpublic void test4(){\n" +
                "\t\tDodawanie dodaj = new Dodawanie();\n" +
                "\t\tassertEquals(10,dodaj.my_add(5,5));\n" +
                "\t}\n" +
                "\n" +
                "\t@Test\n" +
                "\tpublic void test5(){\n" +
                "\t\tDodawanie dodaj = new Dodawanie();\n" +
                "\t\tassertEquals(10,dodaj.my_add(5,5));\n" +
                "\t}\n" +
                "\n" +
                "\t@Test\n" +
                "\tpublic void test6(){\n" +
                "\t\tDodawanie dodaj = new Dodawanie();\n" +
                "\t\tassertEquals(10,dodaj.my_add(5,5));\n" +
                "\t}\n" +
                "\n" +
                "\t@Test\n" +
                "\tpublic void test7(){\n" +
                "\t\tDodawanie dodaj = new Dodawanie();\n" +
                "\t\tassertEquals(10,dodaj.my_add(7,5));\n" +
                "\t}\n" +
                "}");
        taskProgrammingDetail.setWhiteList("fork\n" +
                "kill\n" +
                "pkill\n" +
                "drop\n" +
                "system\n" +
                "Process");
        taskProgrammingDetail.setTestClassName("MyTests.java");
        taskProgrammingDetail.setSolutionClassName("Dodawanie.java");
        taskProgramming.getProgrammingDetailSet().add(taskProgrammingDetail);
        test2.addTaskToTest(taskProgramming);
        taskProgramming = new TaskProgramming("Zadanie z CPP", 100.0f);
        taskProgrammingDetail = new TaskProgrammingDetail();
        taskProgrammingDetail.setLanguage(ProgrammingLanguages.CPP);
        taskProgrammingDetail.setTestCode("#define BOOST_TEST_DYN_LINK\n" +
                "#define BOOST_TEST_MODULE TESTS\n" +
                "#include <boost/test/unit_test.hpp>\n" +
                "#include <boost/test/framework.hpp>\n" +
                "#include <boost/test/results_collector.hpp>\n" +
                "#include <vector>\n" +
                "#include <cmath>\n" +
                "#include <cstdio>\n" +
                "#include \"mathematic.cpp\"\n" +
                "\n" +
                "\n" +
                "\n" +
                "using namespace std;\n" +
                "using boost::unit_test::results_collector;\n" +
                "using boost::unit_test::framework::current_test_case;\n" +
                "using boost::unit_test::test_case;\n" +
                "using boost::unit_test::test_results;\n" +
                "\n" +
                "BOOST_AUTO_TEST_SUITE(MatematykaSuite)\n" +
                "   \n" +
                "BOOST_AUTO_TEST_CASE(testAdd){\n" +
                "    BOOST_CHECK_EQUAL(add(2,2),44);\n" +
                "}\n" +
                "BOOST_AUTO_TEST_CASE(testMultiply){\n" +
                "    BOOST_CHECK_EQUAL(multiply(2,2), 4);\n" +
                "}\n" +
                "BOOST_AUTO_TEST_CASE(testPower){\n" +
                "    BOOST_CHECK_EQUAL(power(3,3), 327);\n" +
                "}\n" +
                "BOOST_AUTO_TEST_CASE(testDivide){\n" +
                "    BOOST_CHECK_EQUAL(divide(3,3),1);\n" +
                "}\n" +
                "BOOST_AUTO_TEST_CASE(testSubstract){\n" +
                "    BOOST_CHECK_EQUAL(substract(3,3),0);\n" +
                "}\n" +
                "BOOST_AUTO_TEST_CASE(testIsPrime){\n" +
                "    BOOST_CHECK_EQUAL(isPrime(5),true);\n" +
                "}\n" +
                "BOOST_AUTO_TEST_SUITE_END()\n" +
                "\n" +
                "BOOST_AUTO_TEST_SUITE(ArraysSuite)\n" +
                "    BOOST_AUTO_TEST_CASE(testSortArrays){\n" +
                "        int tablica[5] = {5,4,3,2,1};\n" +
                "        sortArray(tablica,5);\n" +
                "        int tablica_wyn[5] = {1,2,3,4,5};\n" +
                "        BOOST_CHECK_EQUAL_COLLECTIONS(tablica, tablica+5,tablica_wyn, tablica_wyn+4);\n" +
                "    }\n" +
                "BOOST_AUTO_TEST_SUITE_END()\n" +
                "\n" +
                "\n" +
                "BOOST_AUTO_TEST_SUITE(CollectionsSuite)\n" +
                "    BOOST_AUTO_TEST_CASE(testReverseVector){\n" +
                "        vector<int> daneWe;\n" +
                "        for(int i=0;i<50;i++){\n" +
                "            daneWe.push_back(i);\n" +
                "        }\n" +
                "        vector<int> daneWy(reverseVector(daneWe));\n" +
                "        for(int i=0;i<daneWe.size();i++){\n" +
                "            daneWe.at(i)=50-i-1;\n" +
                "        }\n" +
                "        daneWe.at(daneWe.size()-1)=10;\n" +
                "        BOOST_CHECK_EQUAL_COLLECTIONS(daneWy.begin(), daneWy.end(), daneWe.begin(), daneWe.end());\n" +
                "    }\n" +
                "BOOST_AUTO_TEST_CASE(testGet3FirstElementFromVector){\n" +
                "    vector<int> daneWe;\n" +
                "    daneWe.push_back(10);\n" +
                "    daneWe.push_back(5);\n" +
                "    daneWe.push_back(32);\n" +
                "    daneWe.push_back(45);\n" +
                "    daneWe.push_back(25);\n" +
                "    daneWe.push_back(12);\n" +
                "\n" +
                "    vector<int> daneWy(get3FirstElementFromVector(daneWe));\n" +
                "    BOOST_CHECK_EQUAL_COLLECTIONS(daneWy.begin(), daneWy.end(),daneWe.begin(), daneWe.begin()+3);\n" +
                "}\n" +
                "\n" +
                "BOOST_AUTO_TEST_CASE(testAddTwoVectors){\n" +
                "    vector<vector<int> > daneWe1;\n" +
                "    vector<int> dane1;\n" +
                "    vector<int> dane2;\n" +
                "\n" +
                "    dane1.push_back(55);\n" +
                "    dane1.push_back(21);\n" +
                "    dane1.push_back(45);\n" +
                "\n" +
                "    dane2.push_back(77);\n" +
                "    dane2.push_back(88);\n" +
                "    dane2.push_back(99);\t\n" +
                "\n" +
                "    daneWe1.push_back(dane1);\n" +
                "    daneWe1.push_back(dane2);\n" +
                "\n" +
                "    vector<int> daneWy(addTwoVectors(daneWe1));\n" +
                "\n" +
                "    vector<int> daneWe2(dane1);\n" +
                "    daneWe2.push_back(77);\n" +
                "    daneWe2.push_back(88);\n" +
                "    daneWe2.push_back(99);\n" +
                "\n" +
                "    BOOST_CHECK_EQUAL_COLLECTIONS(daneWy.begin(), daneWy.end(), daneWe2.begin(), daneWe2.end());\t\n" +
                "}\n" +
                "BOOST_AUTO_TEST_SUITE_END()");
        taskProgrammingDetail.setWhiteList("__asm\n" +
                "__asm__\n" +
                "__getdelim\n" +
                "__getpgid\n" +
                "__off64t\n" +
                "_exit\n" +
                "access\n" +
                "acct\n" +
                "alarm\n" +
                "asm\n" +
                "brk\n" +
                "chdir\n" +
                "chown\n" +
                "chroot\n" +
                "clearerr\n" +
                "clearerr_unlocked\n" +
                "close\n" +
                "confstr\n" +
                "creat\n" +
                "crypt\n" +
                "ctermid\n" +
                "daemon\n" +
                "dup\n" +
                "dup2\n" +
                "encrypt\n" +
                "endusershell\n" +
                "euidaccess\n" +
                "execl\n" +
                "execle\n" +
                "execlp\n" +
                "execv\n" +
                "execve\n" +
                "execvp\n" +
                "fchdir\n" +
                "fchown\n" +
                "fclose\n" +
                "fcloseall\n" +
                "fdatasync\n" +
                "fdopen\n" +
                "feof_unlocked\n" +
                "ferror\n" +
                "ferror_unlocked\n" +
                "fexecve\n" +
                "fflush_unlocked\n" +
                "fgetc\n" +
                "fgetc_unlocked\n" +
                "fgetpos\n" +
                "fgetpos64\n" +
                "fgets_unlocked\n" +
                "fileno\n" +
                "fileno_unlocked\n" +
                "flockfile\n" +
                "fmemopen\n" +
                "fopen\n" +
                "fopen64\n" +
                "fopencookie\n" +
                "fork\n" +
                "fork\n" +
                "fpathconf\n" +
                "fprintf\n" +
                "fputc\n" +
                "fputc_unlocked\n" +
                "fputs\n" +
                "fputs_unlocked\n" +
                "fread\n" +
                "fread_unlocked\n" +
                "freopen\n" +
                "freopen64\n" +
                "fscanf\n" +
                "fseek\n" +
                "fseeko\n" +
                "fseeko64\n" +
                "fsetpos\n" +
                "fsetpos64\n" +
                "ftell\n" +
                "ftello\n" +
                "ftello64\n" +
                "ftruncate\n" +
                "ftruncate64\n" +
                "ftrylockfile\n" +
                "funlockfile\n" +
                "fwrite\n" +
                "fwrite_unlocked\n" +
                "get_current_dir_name\n" +
                "getc\n" +
                "getc_unlocked\n" +
                "getcwd\n" +
                "getdelim\n" +
                "getdomainname\n" +
                "getegid\n" +
                "geteuid\n" +
                "getgid\n" +
                "getgroups\n" +
                "gethostid\n" +
                "gethostname\n" +
                "getlogin\n" +
                "getlogin_r\n" +
                "getpagesize\n" +
                "getpass\n" +
                "getpgid\n" +
                "getpgrp\n" +
                "getpid\n" +
                "getppid\n" +
                "getsid\n" +
                "getuid\n" +
                "getusershell\n" +
                "getw\n" +
                "getwd\n" +
                "group_member\n" +
                "isatty\n" +
                "lchown\n" +
                "link\n" +
                "lockf\n" +
                "lockf64\n" +
                "lseek\n" +
                "mkfifo\n" +
                "nice\n" +
                "open\n" +
                "open_memstream\n" +
                "pathconf\n" +
                "pause\n" +
                "pclose\n" +
                "pipe\n" +
                "popen\n" +
                "pread\n" +
                "pread64\n" +
                "profil\n" +
                "pthread_\n" +
                "pthread_atfork\n" +
                "putc\n" +
                "putc_unlocked\n" +
                "putw\n" +
                "pwrite\n" +
                "pwrite64\n" +
                "read\n" +
                "readlink\n" +
                "remove\n" +
                "rename\n" +
                "revoke\n" +
                "rewind\n" +
                "rmdir\n" +
                "sbrk\n" +
                "setbuf\n" +
                "setbuffer\n" +
                "setdomainname\n" +
                "setegid\n" +
                "seteuid\n" +
                "setgid\n" +
                "sethostid\n" +
                "sethostname\n" +
                "setlinebuf\n" +
                "setlogin\n" +
                "setpgid\n" +
                "setpgrp\n" +
                "setregid\n" +
                "setreuid\n" +
                "setsid\n" +
                "setuid\n" +
                "setusershell\n" +
                "setvbuf\n" +
                "signal\n" +
                "stdout\n" +
                "stderr\n" +
                "swab\n" +
                "symlink\n" +
                "sync\n" +
                "sysconf\n" +
                "system\n" +
                "tcgetpgrp\n" +
                "tcsetpgrp\n" +
                "tempnam\n" +
                "tmpfile\n" +
                "tmpfile64\n" +
                "tmpnam\n" +
                "tmpnam_r\n" +
                "truncate\n" +
                "truncate64\n" +
                "ttyname\n" +
                "ttyname_r\n" +
                "ttyslot\n" +
                "ualarm\n" +
                "ungetc\n" +
                "unlink\n" +
                "usleep\n" +
                "vfork\n" +
                "vfprintf\n" +
                "vfscanf\n" +
                "vhangup\n" +
                "write\n" +
                "system");
        taskProgrammingDetail.setTestClassName("testMath.java");
        taskProgrammingDetail.setSolutionClassName("mathematic.java");
        taskProgramming.getProgrammingDetailSet().add(taskProgrammingDetail);
        test2.addTaskToTest(taskProgramming);

        taskProgramming = new TaskProgramming("Zadanie z Pythona", 100.0f);
        taskProgrammingDetail = new TaskProgrammingDetail();
        taskProgrammingDetail.setLanguage(ProgrammingLanguages.PYTHON);
        taskProgrammingDetail.setTestCode("import unittest\n" +
                "import my_example0\n" +
                "import time\n" +
                "\n" +
                "class TestUserFunctions(unittest.TestCase):\n" +
                "    def test_adding(self):\n" +
                "        self.assertEqual(my_example0.my_add(2, 3), 5)\n" +
                "\n" +
                "    def test_power(self):\n" +
                "       self.assertEqual(my_example0.my_power(1, 1), 1)\n" +
                "\n" +
                "    def test_method(self):\n" +
                "        self.assertEqual(my_example0.my_power(2, 2), 2)\n" +
                "\n" +
                "\n" +
                "\n" +
                "if __name__ == '__main__':\n" +
                "    unittest.main()");
        taskProgrammingDetail.setWhiteList("fork");
        taskProgrammingDetail.setTestClassName("my_tests.py");
        taskProgrammingDetail.setSolutionClassName("my_example0.py");
        taskProgramming.getProgrammingDetailSet().add(taskProgrammingDetail);
        test2.addTaskToTest(taskProgramming);
        test2.setTimePerAttempt(90);
        test2.setPassword("");
        test2 = testRepository.save(test2);
    }

    public void createFakeUsers() {
        for (int i = 0; i < 10; i++) {
            user = new User("Imie" + i, "Nazwisko" + i, "przykladowyEmail" + i + "@o2.pl", new BCryptPasswordEncoder().encode("password"));
            repository.save(user);
        }
    }

    public void createFakeNotEnabledUsers() {
        for (int i = 1000; i < 20; i++) {
            user = new User("Imie" + i, "Nazwisko" + i, "przykladowyEmail" + i + "@o2.pl", new BCryptPasswordEncoder().encode("password"));
            user.setEnabled(false);
            user = repository.save(user);
            verificationTokenService.create(user, UUID.randomUUID().toString());


        }
    }

    public void createFakeLockedUsers() {
        for (int i = 2000; i < 30; i++) {
            user = new User("Imie" + i, "Nazwisko" + i, "przykladowyEmail" + i + "@o2.pl", new BCryptPasswordEncoder().encode("password"));
            user.setAccountNonLocked(false);
            repository.save(user);
        }
    }

    public void createFakeNotifications() {
        for (int i = 0; i < 30; i++) {
            try {
                notificationRepository.save(new Notification("WiadomoscPrzykladowaTresc" + i, "PrzykladowyTematWiadomosci" + i, this.sdf.parse("31-08-2005 10:20:56"), i));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    public void createFakeTestsAndSolutionTestsWithClosedTask() {
        for (int i = 0; i < 10; i++) {
            Test test = new Test("PrzykladowyTest" + i, 3L, LocalDate.now().minusWeeks(1L), LocalDate.now().plusWeeks(1L), new ArrayList<>());
            test.setTimePerAttempt(i);
            if (i % 3 == 0) test.setPassword("password");
            else test.setPassword("");
            TaskClosed taskClosed = new TaskClosed("Zaznacz wszystko", 6.0f);
            TreeMap<String, Boolean> answer;
            if (i % 2 == 0) taskClosed.setCountingType(taskClosed.COUNT_NOT_FULL);
            else taskClosed.setCountingType(TaskClosed.WRONG_RESET);
            answer = new TreeMap<>();
            answer.put("1", true);
            answer.put("2", true);
            answer.put("3", true);
            answer.put("4", true);
            answer.put("5", true);
            taskClosed.setAnswers(answer);
            test.addTaskToTest(taskClosed);
            testRepository.save(test);
            Random random = new Random();
            for (int j = 0; j < 5; j++) {
                SolutionTest solutionTest = new SolutionTest(test, repository.findOne((long) (random.nextDouble() * (10))));
                solutionTest.setAttempt(1);
                solutionTest.setBeginSolution(LocalDateTime.now());
                solutionTest.setEndSolution(LocalDateTime.now().plusDays(1));
                TaskClosedSolution taskClosedSolution = new TaskClosedSolution(taskClosed);
                answer = new TreeMap<>();
                answer.put("1", true);
                answer.put("2", true);
                answer.put("3", true);
                answer.put("4", true);
                answer.put("5", false);
                taskClosedSolution.setAnswers(answer);
                solutionTestService.create(solutionTest);
                try {
                    solutionTestService.addTaskSolutionToTest(solutionTest, taskClosedSolution);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (org.json.simple.parser.ParseException e) {
                    e.printStackTrace();
                }
            }

        }
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
=======
        taskOpen = new com.zespolowka.entity.createTest.TaskOpen("Podaj pierwsze 5 malych liter alfabetu polskiego", 10f);
        taskOpen.setCaseSens(true);
        taskOpen.setAnswer("abcde");
        test.addTaskToTest(taskOpen);

        com.zespolowka.entity.createTest.TaskProgramming taskProgramming = new com.zespolowka.entity.createTest.TaskProgramming("fib", 10f);
        com.zespolowka.entity.createTest.TaskProgrammingDetail taskProgrammingDetail = new com.zespolowka.entity.createTest.TaskProgrammingDetail();
        taskProgrammingDetail.setLanguage(com.zespolowka.entity.createTest.ProgrammingLanguages.JAVA);
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

        com.zespolowka.entity.createTest.Test test2 = new com.zespolowka.entity.createTest.Test("test drugi archiwalny", 3L, LocalDate.now().minusWeeks(2), LocalDate.now().minusWeeks(1), new ArrayList<>());
        test2.setTimePerAttempt(90);
        test2.setPassword("22");
        com.zespolowka.entity.createTest.TaskClosed taskClosed2 = new com.zespolowka.entity.createTest.TaskClosed("Ile to jest 2+2*2", 6f);
        TreeMap<String, Boolean> answer2 = new TreeMap<>();
        answer2.put("8", false);
        answer2.put("6", true);
        answer2.put("3*2", true);
        taskClosed2.setAnswers(answer2);
        test2.addTaskToTest(taskClosed2);
        taskClosed2 = new com.zespolowka.entity.createTest.TaskClosed("Ile to jest 2+2+2", 6f);
        answer2 = new TreeMap<>();
        answer2.put("8", false);
        answer2.put("6", true);
        answer2.put("4", false);
        taskClosed2.setAnswers(answer2);
        test2.addTaskToTest(taskClosed2);
        taskClosed2 = new com.zespolowka.entity.createTest.TaskClosed("Ile to jest 3*3*3", 6f);
        answer2 = new TreeMap<>();
        answer2.put("27", true);
        answer2.put("9", false);
        answer2.put("aaa", false);
        taskClosed2.setAnswers(answer2);
        test2.addTaskToTest(taskClosed2);
        taskClosed2 = new com.zespolowka.entity.createTest.TaskClosed("Zaznacz wszystko", 6f);
        taskClosed2.setCountingType(taskClosed.COUNT_NOT_FULL);
        answer2 = new TreeMap<>();
        answer2.put("1", true);
        answer2.put("2", true);
        answer2.put("3", true);
        answer2.put("4", true);
        answer2.put("5", true);
        taskClosed2.setAnswers(answer2);
        test2.addTaskToTest(taskClosed2);
        taskClosed2 = new com.zespolowka.entity.createTest.TaskClosed("Zaznacz imie rozpoczynajace sie na M ", 6f);
        answer2 = new TreeMap<>();
        answer2.put("Adam", false);
        answer2.put("Ewa", false);
        answer2.put("Michal", true);
        taskClosed2.setAnswers(answer2);
        test2.addTaskToTest(taskClosed2);
        com.zespolowka.entity.createTest.TaskOpen taskOpen2 = new com.zespolowka.entity.createTest.TaskOpen("Napisz jak ma na imie Adam Małysz", 10f);
        taskOpen2.setCaseSens(false);
        taskOpen2.setAnswer("Adam");
        test2.addTaskToTest(taskOpen2);

        taskOpen2 = new com.zespolowka.entity.createTest.TaskOpen("Podaj pierwsze 5 malych liter alfabetu polskiego", 10f);
        taskOpen2.setCaseSens(true);
        taskOpen2.setAnswer("abcde");
        test2.addTaskToTest(taskOpen2);

        test2 = testRepository.save(test2);
        logger.info(test2.toString());
        ////////////////
        /////////////////

        com.zespolowka.entity.createTest.Test test3 = new com.zespolowka.entity.createTest.Test("czeci test hehe", 3L, LocalDate.now().minusWeeks(4), LocalDate.now().minusWeeks(2), new ArrayList<>());
        test3.setTimePerAttempt(90);
        test3.setPassword("");
        com.zespolowka.entity.createTest.TaskClosed taskClosed3 = new com.zespolowka.entity.createTest.TaskClosed("Ile to jest 2+2*2", 6f);
        TreeMap<String, Boolean> answer3 = new TreeMap<>();
        answer3.put("8", false);
        answer3.put("6", true);
        answer3.put("3*2", true);
        taskClosed3.setAnswers(answer3);
        test3.addTaskToTest(taskClosed3);
        taskClosed3 = new com.zespolowka.entity.createTest.TaskClosed("Ile to jest 2+2+2", 6f);
        answer3 = new TreeMap<>();
        answer3.put("8", false);
        answer3.put("6", true);
        answer3.put("4", false);
        taskClosed3.setAnswers(answer3);
        test3.addTaskToTest(taskClosed3);
        taskClosed3 = new com.zespolowka.entity.createTest.TaskClosed("Ile to jest 3*3*3", 6f);
        answer3 = new TreeMap<>();
        answer3.put("27", true);
        answer3.put("9", false);
        answer3.put("aaa", false);
        taskClosed3.setAnswers(answer3);
        test3.addTaskToTest(taskClosed3);
        taskClosed3 = new com.zespolowka.entity.createTest.TaskClosed("Zaznacz wszystko", 6f);
        taskClosed3.setCountingType(taskClosed.COUNT_NOT_FULL);
        answer3 = new TreeMap<>();
        answer3.put("1", true);
        answer3.put("2", true);
        answer3.put("3", true);
        answer3.put("4", true);
        answer3.put("5", true);
        taskClosed3.setAnswers(answer3);
        test3.addTaskToTest(taskClosed3);
        taskClosed3 = new com.zespolowka.entity.createTest.TaskClosed("Zaznacz imie rozpoczynajace sie na M ", 6f);
        answer3 = new TreeMap<>();
        answer3.put("Adam", false);
        answer3.put("Ewa", false);
        answer3.put("Michal", true);
        taskClosed3.setAnswers(answer3);
        test3.addTaskToTest(taskClosed3);
        com.zespolowka.entity.createTest.TaskOpen taskOpen3 = new com.zespolowka.entity.createTest.TaskOpen("Napisz jak ma na imie Adam Małysz", 10f);
        taskOpen3.setCaseSens(false);
        taskOpen3.setAnswer("Adam");
        test3.addTaskToTest(taskOpen3);

        taskOpen3 = new com.zespolowka.entity.createTest.TaskOpen("Podaj pierwsze 5 malych liter alfabetu polskiego", 10f);
        taskOpen3.setCaseSens(true);
        taskOpen3.setAnswer("abcde");
        test3.addTaskToTest(taskOpen3);

        test3 = testRepository.save(test3);
        logger.info(test.toString());
    }

>>>>>>> addf63146eadb4865c3e88fc9502c025b3871c1e
}
