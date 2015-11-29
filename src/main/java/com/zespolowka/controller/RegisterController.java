package com.zespolowka.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by Zethius on 2015-11-29.
 *
 * Controller odpowiadajacy za wyswietlenie strony z rejestracja
 */
@Controller
public class RegisterController {

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String registerPage(Model model) {
        model.addAttribute("message", "Hello Spring MVC Framework!");
        return "register";
    }

}
