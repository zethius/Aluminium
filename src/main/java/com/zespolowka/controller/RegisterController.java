package com.zespolowka.controller;


import com.zespolowka.User;
import javax.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
/**
 * Created by Zethius on 2015-11-29.
 *
 * Controller odpowiadajacy za wyswietlenie strony z rejestracja
 */
@Controller
public class RegisterController {

    @RequestMapping(value="/register", method=RequestMethod.GET)
    public String regForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }
 
    

    @RequestMapping(value="/register", method=RequestMethod.POST)
    public String regSubmit(@Valid @ModelAttribute User user, BindingResult result, ModelMap model) {
        if (result.hasFieldErrors()) {
                return "regError";
        }
        model.addAttribute("user", user);    
        return "regSuccess";
    }        

}
