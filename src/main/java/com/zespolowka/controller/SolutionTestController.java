package com.zespolowka.controller;

import com.zespolowka.entity.createTest.TaskClosed;
import com.zespolowka.entity.createTest.Test;
import com.zespolowka.entity.solutionTest.SolutionStatus;
import com.zespolowka.entity.solutionTest.SolutionTest;
import com.zespolowka.entity.solutionTest.TaskTypeChecker;
import com.zespolowka.entity.user.CurrentUser;
import com.zespolowka.entity.user.User;
import com.zespolowka.forms.SolutionTestForm;
import com.zespolowka.service.inteface.SolutionTestService;
import com.zespolowka.service.inteface.TestService;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;


@Controller
public class SolutionTestController {
    private static final Logger logger = LoggerFactory.getLogger(SolutionTestController.class);
    private static final String TEST_ATTRIBUTE_NAME = "TestId";
    @Autowired
    private HttpSession httpSession;
    @Autowired
    private SolutionTestService solutionTestService;
    @Autowired
    private TestService testService;

    public SolutionTestController() {
    }

    @RequestMapping(value = "/getSolutionTest", method = RequestMethod.POST)
    public String getSolutionTestPage(@RequestParam(value = "id", required = true) Integer id,
                                      @RequestParam(value = "pass", required = false) String password, final RedirectAttributes redirectAttributes) {
        logger.info("getSoltutionTestPage dla testu o id={}", id);
        Test test = testService.getTestById(id);
        if (test.isOpenTest()) {
            this.httpSession.setAttribute(TEST_ATTRIBUTE_NAME, test.getId());
            return "redirect:/solutionTest";
        } else if (password != null) {
            if (password.equals(test.getPassword())) {
                this.httpSession.setAttribute(TEST_ATTRIBUTE_NAME, test.getId());
                return "redirect:/solutionTest";
            }
        }
        return "redirect:/";
    }


    @RequestMapping(value = "/solutionTest")
    public String solutionTestPage(Model model) {

        CurrentUser currentUser = (CurrentUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = currentUser.getUser();
        Long id = (Long) this.httpSession.getAttribute(TEST_ATTRIBUTE_NAME);
        Test test = testService.getTestById(id);
        Optional<SolutionTest> solutionTest2 = solutionTestService.findSolutionTestByTestAndUserAndSolutionStatus(test, user, SolutionStatus.OPEN);
        if (solutionTest2.isPresent()) {
            SolutionTest solutionTest = solutionTest2.get();
            SolutionTestForm solutionTestForm = solutionTestService.createForm(solutionTest.getTest(), solutionTest.getUser());
            model.addAttribute("solutionTest", solutionTestForm);
            return "testSolution";
        } else {
            solutionTest2 = solutionTestService.findSolutionTestByTestAndUserAndSolutionStatus(test, user, SolutionStatus.DURING);
            if (solutionTest2.isPresent()) {
                SolutionTest solutionTest = solutionTest2.get();
                SolutionTestForm solutionTestForm = solutionTestService.createFormWithExistingSolution(solutionTest);
                model.addAttribute("solutionTest", solutionTestForm);
                return "testSolution";
            } else {
                Integer attemptForUser = solutionTestService.countSolutionTestsByUserAndTest(user, test);
                if (test.getAttempts().intValue() <= attemptForUser) {
                    model.addAttribute("testSolutionError", true);
                    return "testSolution";
                } else {
                    SolutionTestForm solutionTestForm = solutionTestService.createForm(test, user);
                    model.addAttribute("solutionTest", solutionTestForm);
                    return "testSolution";
                }
            }

        }
    }

    @RequestMapping(value = "/solutionTest", method = RequestMethod.POST)
    public String saveSolutionTest(SolutionTestForm solutionTestForm) throws IOException, ParseException {
        logger.info("Metoda - saveSolutionTest");
        Long id = (Long) this.httpSession.getAttribute(TEST_ATTRIBUTE_NAME);
        Test test = testService.getTestById(id);
        CurrentUser currentUser = (CurrentUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = currentUser.getUser();
        SolutionTest solutionTest;
        Optional<SolutionTest> solutionTest2 = solutionTestService.findSolutionTestByTestAndUserAndSolutionStatus(test, user, SolutionStatus.OPEN);
        if (solutionTest2.isPresent()) {
            solutionTest = solutionTestService.create(solutionTest2.get(), solutionTestForm);
        } else {
            solutionTest2 = solutionTestService.findSolutionTestByTestAndUserAndSolutionStatus(test, user, SolutionStatus.DURING);
            solutionTest = solutionTestService.create(solutionTest2.get(), solutionTestForm);
        }
        solutionTestService.create(solutionTest, SolutionStatus.FINISHED);
        return "redirect:/showResults";
    }

    @RequestMapping(value = "/solutionTest/save", method = RequestMethod.POST)
    public String saveTmpSolutionTest(SolutionTestForm solutionTestForm, Model model) throws
            IOException, ParseException {
        logger.info("Metoda - saveTmpSolutionTest");
        Long id = (Long) this.httpSession.getAttribute(TEST_ATTRIBUTE_NAME);
        Test test = testService.getTestById(id);
        CurrentUser currentUser = (CurrentUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = currentUser.getUser();
        SolutionTest solutionTest;
        Optional<SolutionTest> solutionTest2 = solutionTestService.findSolutionTestByTestAndUserAndSolutionStatus(test, user, SolutionStatus.OPEN);
        if (solutionTest2.isPresent()) {
            solutionTest = solutionTestService.create(solutionTest2.get(), solutionTestForm);
        } else {
            solutionTest2 = solutionTestService.findSolutionTestByTestAndUserAndSolutionStatus(test, user, SolutionStatus.DURING);
            solutionTest = solutionTestService.create(solutionTest2.get(), solutionTestForm);
        }
        solutionTest = solutionTestService.create(solutionTest, SolutionStatus.DURING);
        solutionTestForm = solutionTestService.createFormWithExistingSolution(solutionTest);
        model.addAttribute("solutionTest", solutionTestForm);
        model.addAttribute("tmpSolutionTest", true);
        return "testSolution";
    }

    @RequestMapping(value = "/solutionTestAfterTime", method = RequestMethod.POST)
    public String saveSolutionTestAfterTime(SolutionTestForm solutionTestForm, Model model) throws
            IOException, ParseException {
        logger.info("Metoda - saveSolutionTestAfterTime");
        Long id = (Long) this.httpSession.getAttribute(TEST_ATTRIBUTE_NAME);
        Test test = testService.getTestById(id);
        CurrentUser currentUser = (CurrentUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = currentUser.getUser();
        SolutionTest solutionTest;
        Optional<SolutionTest> solutionTest2 = solutionTestService.findSolutionTestByTestAndUserAndSolutionStatus(test, user, SolutionStatus.OPEN);
        if (solutionTest2.isPresent()) {
            solutionTest = solutionTestService.create(solutionTest2.get(), solutionTestForm);
        } else {
            solutionTest2 = solutionTestService.findSolutionTestByTestAndUserAndSolutionStatus(test, user, SolutionStatus.DURING);
            solutionTest = solutionTestService.create(solutionTest2.get(), solutionTestForm);
        }
        solutionTestService.create(solutionTest, SolutionStatus.FINISHED);
        model.addAttribute("testAfterTime", true);
        return "testSolution";
    }

    @RequestMapping(value = "/solutionTest/{id}", method = RequestMethod.GET)
    public String solutionTestPage(@PathVariable("id") Long id, Model model) {
        model.addAttribute(new TaskTypeChecker());
        SolutionTest solutionTest = solutionTestService.getSolutionTestById(id);
        model.addAttribute("solutionTest", solutionTest);
        return "solutionTestCheckAnswers";
    }


    @RequestMapping(value = "/solutionTestCheckAnswers")
    public String checkSolutionTestPage(@ModelAttribute("sendModel") final SolutionTest solutionTest, Model model) {
        model.addAttribute(new TaskTypeChecker());
        model.addAttribute("solutionTest", solutionTest);
        return "solutionTestCheckAnswers";
    }

    @ResponseBody
    @RequestMapping(value = "/solutionTest/loadEntity/{id}", method = RequestMethod.GET)
    public Test loadEntity(@PathVariable("id") Long id) {
        return testService.getTestById(id);
    }

    @ResponseBody
    @RequestMapping(value = "/solutionTest/loadResultEntity/{id}", method = RequestMethod.GET)
    public List<SolutionTest> loadResultEntity(@PathVariable("id") Long id) {
        logger.info("metoda=SolutionTestController.loadResultEntity");
        return (List<SolutionTest>) solutionTestService.getSolutionTestsByTest(testService.getTestById(id));
    }

    @RequestMapping(value = "/setTestDate", method = RequestMethod.POST)
    public String changeTestDate(@RequestParam(value = "id", required = true) Integer id,
                                 @RequestParam(value = "date", required = true) String date,
                                 final RedirectAttributes redirectAttributes) {
        logger.info("setTestDate dla testu o id={}; date={}", id, date);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date2 = LocalDate.parse(date, formatter);

        Test test = testService.getTestById(id);
        if (test.getEndDate().isAfter(LocalDate.now())) {
            redirectAttributes.addFlashAttribute("testOtwarty", true);
        } else {
            if (date2.isBefore(test.getBeginDate()) || date2.isBefore(test.getEndDate())) {
                redirectAttributes.addFlashAttribute("dataStarsza", true);
            } else {
                redirectAttributes.addFlashAttribute("sukces", true);
                test.setEndDate(date2);
                testService.update(test);
            }
        }
        return "redirect:/test/all";
    }


    @RequestMapping(value = "/showResults", method = RequestMethod.GET)
    public String showCurrentUserTests(final Model model) {
        logger.info("nazwa metody = showCurrentUserTests");
        try {
            final CurrentUser currentUser = (CurrentUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            final User user = currentUser.getUser();
            model.addAttribute("Tests", solutionTestService.getSolutionTestsByUser(user));
            model.addAttribute("BestTest", solutionTestService.getSolutionsWithTheBestResult(user));
        } catch (final RuntimeException e) {
            logger.error(e.getMessage(), e);
        }
        return "userTests";
    }


    @Override
    public String toString() {
        return "SolutionTestController{" +
                "httpSession=" + httpSession +
                ", solutionTestService=" + solutionTestService +
                ", testService=" + testService +
                '}';
    }
}
