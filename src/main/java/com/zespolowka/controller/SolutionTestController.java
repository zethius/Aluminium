package com.zespolowka.controller;

import com.zespolowka.entity.createTest.Test;
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
import java.util.List;


@Controller
public class SolutionTestController {
    private static final Logger logger = LoggerFactory.getLogger(SolutionTestController.class);
    private static final String TEST_ATTRIBUTE_NAME = "solutionTestSession";
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
            redirectAttributes.addFlashAttribute("Test", test);
            return "redirect:/solutionTest";
        } else if (password != null) {
            if (password.equals(test.getPassword())) {
                redirectAttributes.addFlashAttribute("Test", test);
                return "redirect:/solutionTest";
            }
        }
        return "redirect:/";
    }


    @RequestMapping(value = "/solutionTest")
    public String solutionTestPage(Model model, @ModelAttribute("Test") Test test2) {
        SolutionTest solutionTest = (SolutionTest) this.httpSession.getAttribute(TEST_ATTRIBUTE_NAME);
        if (solutionTest != null) {
            SolutionTestForm solutionTestForm = solutionTestService.createForm(solutionTest.getTest(), solutionTest.getUser());
            model.addAttribute("solutionTest", solutionTestForm);
            return "testSolution";
        } else {
            CurrentUser currentUser = (CurrentUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User user = currentUser.getUser();
            Long id;
            if (test2 == null) {
                id = 1L;
            } else id = test2.getId();
            Test test = testService.getTestById(id);
            Integer attemptForUser = solutionTestService.getSolutionTestsByUserAndTest(user, test).size();
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

    @RequestMapping(value = "/solutionTest", method = RequestMethod.POST)
    public String saveSolutionTest(final SolutionTestForm solutionTestForm, Model model, final RedirectAttributes redirectAttributes) throws IOException, ParseException {
        logger.info("Metoda - saveSolutionTest");
        SolutionTest solutionTest = (SolutionTest) this.httpSession.getAttribute(TEST_ATTRIBUTE_NAME);
        this.httpSession.removeAttribute(TEST_ATTRIBUTE_NAME);
        solutionTest = solutionTestService.create(solutionTest, solutionTestForm);
        solutionTestService.create(solutionTest);
        return "redirect:/test/showResults";
    }

    @RequestMapping(value = "/solutionTest/{id}", method = RequestMethod.GET)
    public String solutionTestPage(@PathVariable("id") Long id,Model model) {
        model.addAttribute(new TaskTypeChecker());
        model.addAttribute("solutionTest", solutionTestService.getSolutionTestById(id));
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
