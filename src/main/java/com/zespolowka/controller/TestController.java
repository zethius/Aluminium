package com.zespolowka.controller;

import com.zespolowka.entity.createTest.Test;
import com.zespolowka.forms.CreateTestForm;
import com.zespolowka.forms.TaskForm;
import com.zespolowka.service.TestFormService;
import com.zespolowka.service.inteface.TestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/test")
public class TestController {
    private static final Logger logger = LoggerFactory.getLogger(TestController.class);
    private TestFormService testFormService;
    private TestService testService;
    private CreateTestForm createTestForm;

    @Autowired
    public TestController(final TestFormService testFormService,TestService testService) {
        this.testFormService = testFormService;
        this.testService=testService;
    }

    @RequestMapping(value = "create")
    public String createTest(final Model model) {
        logger.info("Metoda - createTest");
        createTestForm = this.testFormService.getTestFromSession();
        model.addAttribute("createTestForm", createTestForm);
        return "tmpCreateTest";
    }

    @RequestMapping(value = "create/add", method = RequestMethod.POST)
    public String addQuestion(@RequestParam(value = "questionId", defaultValue = "0") int questionId, final CreateTestForm createTestForm, final Model model) {
        testFormService.updateTestFormInSession(createTestForm);
        logger.info("Metoda - addQuestion");
        switch (questionId) {
            case 0: {
                testFormService.addTaskFormToTestForm(new TaskForm(TaskForm.CLOSEDTASK));
                break;
            }
            case 1: {
                testFormService.addTaskFormToTestForm(new TaskForm(TaskForm.OPENTASK));
                break;
            }
            case 2: {
                testFormService.addTaskFormToTestForm(new TaskForm(TaskForm.PROGRAMMINGTASK));
                break;
            }
        }
        logger.info(createTestForm.toString());

        model.addAttribute("createTestForm", testFormService.getTestFromSession());
        return "redirect:/test/create";
    }

    @RequestMapping(value = "create", method = RequestMethod.POST)
    public String save(final CreateTestForm createTestForm, final BindingResult result) {
        logger.info("Metoda - save");
        testFormService.updateTestFormInSession(createTestForm);
        Test test=testService.create(createTestForm);
        testFormService.updateTestFormInSession(new CreateTestForm());
        return "redirect:/test/create";
    }
}
