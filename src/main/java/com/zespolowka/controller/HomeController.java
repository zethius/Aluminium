package com.zespolowka.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Pitek on 2015-11-28.
 * Controller odpowiadajacy za wyswietlenie strony glownej
 */
@Controller
public class HomeController {
    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    private
    @Value("${homepage.message}")
    String pageMessage;


    @RequestMapping(value = "/")
    public String homePage(Model model) {
        logger.info("nazwa metody = homePage");
        try {
            model.addAttribute("pageMessage", this.pageMessage);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            logger.info(model.toString() + " " + this.pageMessage);
        }
        return "index";
    }

}
