package com.zespolowka.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by Pitek on 2015-11-28.
 *
 * Controller odpowiadajacy za wyswietlenie strony glownej
 */
@Controller
public class HomeController {

    private  @Value("${homepage.message}") String pageMessage;
    @RequestMapping(value = "/")
    public String homePage(Model model){
        model.addAttribute("pageMessage",this.pageMessage);
        return "index";
    }

}
