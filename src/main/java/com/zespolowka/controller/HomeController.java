package com.zespolowka.controller;

import com.zespolowka.service.inteface.TestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class HomeController {
    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    private TestService testService;


    @Value("${homepage.message}")
    private String pageMessage;


    @RequestMapping(value = "/")
    public String homePage(Model model) {
        logger.info("nazwa metody = homePage");
        try {
            model.addAttribute("pageMessage", this.pageMessage);
            logger.info(testService.getAllTests().toString());
            model.addAttribute("activeTest",testService.getAllTests());
            model.addAttribute("archiveTest",testService.getAllTests());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            logger.info(model.toString() + " " + this.pageMessage);
        }
        return "index";
    }

    @RequestMapping(value = "/messages")
    public String messagesPage(){
        return "messages";
    }

}
