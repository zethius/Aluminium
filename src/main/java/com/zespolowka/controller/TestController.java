package com.zespolowka.controller;

import com.zespolowka.entity.createTest.ProgrammingLanguages;
import com.zespolowka.entity.createTest.Test;
import com.zespolowka.forms.CreateTestForm;
import com.zespolowka.forms.ProgrammingTaskForm;
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

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

@Controller
@RequestMapping("/test")
public class TestController {
    private static final Logger logger = LoggerFactory.getLogger(TestController.class);
    private TestFormService testFormService;
    private TestService testService;
    private CreateTestForm createTestForm;

    @Autowired
    public TestController(final TestFormService testFormService, TestService testService) {
        this.testFormService = testFormService;
        this.testService = testService;
    }

    @RequestMapping(value = "create")
    public String createTest(final Model model) {
        logger.info("Metoda - createTest");
        createTestForm = this.testFormService.getTestFromSession();
        logger.info(createTestForm.toString());
        model.addAttribute("createTestForm", createTestForm);
        return "tmpCreateTest";
    }

    @RequestMapping(value = "create/add", method = RequestMethod.POST)
    public String addQuestion(@RequestParam(value = "questionId", defaultValue = "0") int questionId, final CreateTestForm createTestForm, final Model model) {
        testFormService.updateTestFormInSession(createTestForm);
        logger.info(testFormService.getTestFromSession().toString());
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

    @RequestMapping(value = "create/change", method = RequestMethod.POST)
    public String changeLanguages(@RequestParam(value = "taskId") int taskId, @RequestParam(value = "selected", defaultValue = "") String selected, final CreateTestForm createTestForm, final Model model) {
        logger.info("Metoda - changeLanguages");
        taskId -= 1;
        testFormService.updateSelectedLanguagesInSession(selected);  ///TODO zmienic by to w sesji jak pizdy nie było
        String languages[] = selected.split(",");
        TaskForm taskForm = createTestForm.getTasks().get(taskId);
        Set<ProgrammingTaskForm> programmingTaskFormSet = taskForm.getProgrammingTaskForms();
        Set<ProgrammingTaskForm> newProgrammingTaskFormSet = new TreeSet<>();


        for (ProgrammingLanguages prLanguage : ProgrammingLanguages.values()) {
            String language = prLanguage.toString();
            if (Arrays.asList(languages).indexOf(language) > -1) {
                if (taskForm.getLanguages().contains(language)) {
                    programmingTaskFormSet.stream().filter(programmingTaskForm -> programmingTaskForm.getLanguage().equals(language)).forEach(programmingTaskForm -> {
                        programmingTaskForm.setHidden(true);
                        newProgrammingTaskFormSet.add(programmingTaskForm);
                    });
                } else {
                    newProgrammingTaskFormSet.add(new ProgrammingTaskForm(language, true));
                }
            } else {
                newProgrammingTaskFormSet.add(new ProgrammingTaskForm(language, false));
            }

        }

        Set<String> lang = new HashSet<>(Arrays.asList(languages));
        taskForm.setLanguages(lang);
        taskForm.setProgrammingTaskForms(newProgrammingTaskFormSet);
        createTestForm.getTasks().set(taskId, taskForm);
        testFormService.updateTestFormInSession(createTestForm);
        model.addAttribute("createTestForm", testFormService.getTestFromSession());
        return "redirect:/test/create";
    }

    @RequestMapping(value = "create/remove", method = RequestMethod.POST)
    public String removeQuestion(@RequestParam(value = "taskId") int taskId,final CreateTestForm createTestForm, final Model model) {
        logger.info("removeQuestion");
        logger.info(createTestForm+"");
        createTestForm.getTasks().remove(taskId);
        testFormService.updateTestFormInSession(createTestForm);
        model.addAttribute("createTestForm", testFormService.getTestFromSession());
        return "redirect:/test/create";
    }

    @RequestMapping(value = "create", method = RequestMethod.POST)
    public String save(final CreateTestForm createTestForm, final BindingResult result) {
        logger.info("Metoda - save");
        logger.info(createTestForm.toString());
        testFormService.updateTestFormInSession(createTestForm);
        Test test = testService.create(createTestForm);
        logger.info(test.toString());
        testFormService.updateTestFormInSession(new CreateTestForm());
        testFormService.updateSelectedLanguagesInSession(new String());
        return "redirect:/test/create";
    }
}
