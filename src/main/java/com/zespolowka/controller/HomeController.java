package com.zespolowka.controller;

import com.zespolowka.entity.createTest.Test;
import com.zespolowka.service.inteface.SolutionTestService;
import com.zespolowka.service.inteface.TestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDate;


@Controller
public class HomeController {
    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    private TestService testService;

    @Autowired
    private SolutionTestService solutionTestService;


    @Value("${homepage.message}")
    private String pageMessage;


    @RequestMapping(value = "/")
    public String homePage(Model model) {
        logger.info("nazwa metody = homePage");
        try {
            model.addAttribute("pageMessage", this.pageMessage);
            logger.info(testService.getAllTests().toString());
            model.addAttribute("activeTest", testService.getAllTests());
            model.addAttribute("archiveTest", testService.getTestByEndDateBefore(LocalDate.now()));
            logger.info(solutionTestService.getAllTests().toString());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            logger.info(model.toString() + " " + this.pageMessage);
        }
        return "index";
    }


}
