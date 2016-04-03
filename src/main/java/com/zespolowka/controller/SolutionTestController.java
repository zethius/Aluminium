package com.zespolowka.controller;

import com.zespolowka.entity.createTest.Test;
import com.zespolowka.entity.solutionTest.SolutionTest;
import com.zespolowka.entity.solutionTest.TaskTypeChecker;
import com.zespolowka.entity.user.CurrentUser;
import com.zespolowka.entity.user.User;
import com.zespolowka.forms.SolutionTestForm;
import com.zespolowka.service.inteface.SolutionTestService;
import com.zespolowka.service.inteface.TestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;


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

    @RequestMapping(value = "/solutionTest")
    public String solutionTestPage(Model model) {
        CurrentUser currentUser = (CurrentUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = currentUser.getUser();
        Test test = testService.getTestById(1);
        Integer attemptForUser = solutionTestService.getSolutionTestsByUserAndTest(user, test).size();
        logger.info(attemptForUser + "");
        logger.info(test.getAttempts() + "");
        if (attemptForUser == null) attemptForUser = 0;
        if (test.getAttempts().intValue() <= attemptForUser) {
            model.addAttribute("testSolutionError", true);
            logger.info("ni chuja");
            return "testSolution";
        } else {
            SolutionTestForm solutionTestForm = solutionTestService.createForm(test, user);
            model.addAttribute("solutionTest", solutionTestForm);
            return "testSolution";
        }
    }

    @RequestMapping(value = "/solutionTest", method = RequestMethod.POST)
    public String saveSolutionTest(final SolutionTestForm solutionTestForm, Model model, final RedirectAttributes redirectAttributes) {
        logger.info("Metoda - saveSolutionTest");
        SolutionTest solutionTest = (SolutionTest) this.httpSession.getAttribute(TEST_ATTRIBUTE_NAME);
        solutionTest.create(solutionTestForm);
        solutionTestService.create(solutionTest);
        model.addAttribute("solutionTest", solutionTest);
        redirectAttributes.addFlashAttribute("sendModel", solutionTest);
        return "redirect:/solutionTestCheckAnswers";
    }

    @RequestMapping(value = "/solutionTestCheckAnswers")
    public String checkSolutionTestPage(@ModelAttribute("sendModel") final SolutionTest solutionTest, Model model) {
        model.addAttribute(new TaskTypeChecker());
        logger.info(solutionTest + "");
        model.addAttribute("solutionTest", solutionTest);
        return "solutionTestCheckAnswers";
    }

}
