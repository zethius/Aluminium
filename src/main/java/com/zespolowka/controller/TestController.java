package com.zespolowka.controller;

import com.zespolowka.entity.createTest.ProgrammingLanguages;
import com.zespolowka.entity.createTest.Test;
import com.zespolowka.entity.user.CurrentUser;
import com.zespolowka.entity.user.User;
import com.zespolowka.forms.CreateTestForm;
import com.zespolowka.forms.ProgrammingTaskForm;
import com.zespolowka.forms.TaskForm;
import com.zespolowka.service.TestFormService;
import com.zespolowka.service.inteface.SolutionTestService;
import com.zespolowka.service.inteface.TestService;
import com.zespolowka.service.inteface.UserService;
import com.zespolowka.validators.CreateTestValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.*;

@Controller
@RequestMapping("/test")
public class TestController {
    private static final Logger logger = LoggerFactory.getLogger(TestController.class);
    private final TestFormService testFormService;
    private final TestService testService;
    private final CreateTestValidator createTestValidator;
    private final SolutionTestService solutionTestService;
    private final UserService userService;
    private CreateTestForm createTestForm;

    @Autowired
    public TestController(final TestFormService testFormService, TestService testService, CreateTestValidator createTestValidator, SolutionTestService solutionTestService, UserService userService) {
        this.testFormService = testFormService;
        this.testService = testService;
        this.createTestValidator = createTestValidator;
        this.solutionTestService = solutionTestService;
        this.userService = userService;
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SUPERADMIN')")
    @RequestMapping(value = "create")
    public String createTest(final Model model) {
        logger.info("Metoda - createTest");
        createTestForm = this.testFormService.getTestFromSession();
        model.addAttribute("createTestForm", createTestForm);
        return "tmpCreateTest";
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SUPERADMIN')")
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
            case 3: {
                testFormService.addTaskFormToTestForm(new TaskForm(TaskForm.SQLTASK));
                break;
            }
        }
        model.addAttribute("createTestForm", testFormService.getTestFromSession());
        return "redirect:/test/create";
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SUPERADMIN')")
    @RequestMapping(value = "create/change", method = RequestMethod.POST)
    public String changeLanguages(@RequestParam(value = "taskId", defaultValue = "0") Integer taskId, @RequestParam(value = "selected", defaultValue = "java") String selected, final CreateTestForm createTestForm, final Model model) {
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

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SUPERADMIN')")
    @RequestMapping(value = "create/remove", method = RequestMethod.POST)
    public String removeQuestion(@RequestParam(value = "taskId") int taskId, final CreateTestForm createTestForm, final Model model) {
        logger.info("removeQuestion");
        createTestForm.getTasks().remove(taskId);
        testFormService.updateTestFormInSession(createTestForm);
        model.addAttribute("createTestForm", testFormService.getTestFromSession());
        return "redirect:/test/create";
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SUPERADMIN')")
    @RequestMapping(value = "create", method = RequestMethod.POST)
    public String save(final @Valid CreateTestForm createTestForm, final BindingResult result,RedirectAttributes redirectAttributes) {
        logger.info("Metoda - save");
        createTestValidator.validate(createTestForm, result);
        if (result.hasErrors()) {
            logger.info(result.getAllErrors().toString());
            return "tmpCreateTest";
        }
        testFormService.updateTestFormInSession(createTestForm);
        Test test = testService.create(createTestForm);
        logger.info(test.toString());
        testFormService.updateTestFormInSession(new CreateTestForm());
        testFormService.updateSelectedLanguagesInSession("");
        redirectAttributes.addFlashAttribute("sukces", true);
        return "redirect:/test/create";
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SUPERADMIN')")
    @RequestMapping("/all")
    public String showAll(Model model) {
        logger.info("metoda - showAll");
        try {
            model.addAttribute("Tests", testService.getAllTests());
        } catch (RuntimeException e) {
            logger.error(e.getMessage(), e);
        }
        return "tests";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String showUserTests(@PathVariable final Long id, final Model model) {
        logger.info("nazwa metody = showUserTests");
        try {
            User user = userService.getUserById(id)
                    .orElseThrow(() -> new NoSuchElementException(String.format("Uzytkownik o id =%s nie istnieje", id)));
            model.addAttribute("Tests", solutionTestService.getSolutionTestsByUser(user));
        } catch (final Exception e) {
            logger.error(e.getMessage(), e);
            logger.info(id.toString() + "\n" + model);
        }
        return "userTests";
    }

    @RequestMapping(value = "/showResults", method = RequestMethod.GET)
    public String showCurrentUserTests(final Model model) {
        logger.info("nazwa metody = showCurrentUserTests");
        try {
            final CurrentUser currentUser = (CurrentUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            final User user = currentUser.getUser();
            model.addAttribute("Tests", solutionTestService.getSolutionTestsByUser(user));
            model.addAttribute("BestTest", solutionTestService.getSolutionsWithTheBestResult(user));
        } catch (final RuntimeException e) {
            logger.error(e.getMessage(), e);
        }
        return "userTests";
    }


    @Override
    public String toString() {
        return "TestController{" +
                "testFormService=" + testFormService +
                ", testService=" + testService +
                ", createTestValidator=" + createTestValidator +
                ", createTestForm=" + createTestForm +
                '}';
    }
}
