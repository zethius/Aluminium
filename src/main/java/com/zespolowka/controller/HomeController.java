package com.zespolowka.controller;

import com.zespolowka.service.inteface.SolutionTestService;
import com.zespolowka.service.inteface.TestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;


@Controller
public class HomeController {
    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    private final TestService testService;
    private final SolutionTestService solutionTestService;

    @Autowired
    public HomeController(TestService testService, SolutionTestService solutionTestService) {
        this.testService = testService;
        this.solutionTestService = solutionTestService;
    }


    @RequestMapping(value = "/")
    public String homePage(Model model) {
        logger.info("nazwa metody = homePage");
        try {
            model.addAttribute("activeTest", testService.getAllTests());
            model.addAttribute("archiveTest", testService.getTestByEndDateBefore(LocalDate.now()));
        } catch (RuntimeException e) {
            logger.error(e.getMessage(), e);

        }
        return "index";
    }

    @Override
    public String toString() {
        return "HomeController{" +
                "testService=" + testService +
                ", solutionTestService=" + solutionTestService +
                '}';
    }
}
