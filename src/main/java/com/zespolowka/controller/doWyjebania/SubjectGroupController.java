package com.zespolowka.controller.doWyjebania;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
@RequestMapping("/sg")
public class SubjectGroupController {


    @RequestMapping(value = "add")
    public String add(final SubjectGroup subjectGroup) {
        return "add";
    }

    @RequestMapping(value = "add", params = {"addOption"})
    public String addOption(final SubjectGroup subjectGroup, final BindingResult result) {
        subjectGroup.getOptions().add(new SubjectGroupOption());
        System.out.println(subjectGroup.toString());
        return "add";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String save(final SubjectGroup subjectGroup, final BindingResult result) {
        System.out.println(subjectGroup.toString());
        return "redirect:/sg/add";
    }
}


