package com.zespolowka.controller;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.zespolowka.entity.createTest.Test;
import com.zespolowka.entity.solutionTest.SolutionTest;
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

import javax.validation.Valid;
import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
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
    @RequestMapping(value = "/edit/{id}")
    public String editTest(@PathVariable("id") Long id, final Model model) {
        logger.info("Metoda - editTest");
        if (testFormService.getEditTestIdFromSession() != null) {
            createTestForm = testFormService.getEditTestFromSession();
        } else {
            createTestForm = testService.createForm(testService.getTestById(id));
            testFormService.setEditTestIdInSession(id);
            testFormService.updateEditTestFormInSession(createTestForm);
        }
        model.addAttribute("createTestForm", createTestForm);
        return "editTest";
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
    @RequestMapping(value = "edit/add", method = RequestMethod.POST)
    public String addEditQuestion(@RequestParam(value = "questionId", defaultValue = "0") int questionId, final CreateTestForm createTestForm, final Model model) {
        testFormService.updateEditTestFormInSession(createTestForm);
        logger.info("Metoda - addEditQuestion");
        switch (questionId) {
            case 0: {
                testFormService.addTaskFormToEditTestForm(new TaskForm(TaskForm.CLOSEDTASK));
                break;
            }
            case 1: {
                testFormService.addTaskFormToEditTestForm(new TaskForm(TaskForm.OPENTASK));
                break;
            }
            case 2: {
                testFormService.addTaskFormToEditTestForm(new TaskForm(TaskForm.PROGRAMMINGTASK));
                break;
            }
            case 3: {
                testFormService.addTaskFormToEditTestForm(new TaskForm(TaskForm.SQLTASK));
                break;
            }
        }
        model.addAttribute("createTestForm", testFormService.getEditTestFromSession());
        return "redirect:/test/edit/" + testFormService.getEditTestIdFromSession();
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SUPERADMIN')")
    @RequestMapping(value = "create/change", method = RequestMethod.POST)
    public String changeLanguages(@RequestParam(value = "taskId", defaultValue = "0") Integer taskId, @RequestParam(value = "selected", defaultValue = "java") String selected, final CreateTestForm createTestForm, final Model model) {
        logger.info("Metoda - changeLanguages");
        taskId -= 1;
        testFormService.updateSelectedLanguagesInSession(selected);
        String languages[] = selected.split(",");
        TaskForm taskForm = createTestForm.getTasks().get(taskId);
        Set<ProgrammingTaskForm> programmingTaskFormSet = taskForm.getProgrammingTaskForms();
        Set<String> lang = new HashSet<>(Arrays.asList(languages));
        taskForm.setLanguages(lang);
        taskForm.setProgrammingTaskForms(testFormService.createProgrammingTaskSet(programmingTaskFormSet, languages, taskForm));

        createTestForm.getTasks().set(taskId, taskForm);
        testFormService.updateTestFormInSession(createTestForm);
        model.addAttribute("createTestForm", testFormService.getTestFromSession());
        return "redirect:/test/create";
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SUPERADMIN')")
    @RequestMapping(value = "edit/change", method = RequestMethod.POST)
    public String changeEditLanguages(@RequestParam(value = "taskId", defaultValue = "0") Integer taskId, @RequestParam(value = "selected", defaultValue = "java") String selected, final CreateTestForm createTestForm, final Model model) {
        logger.info("Metoda - changeEditLanguages");
        testFormService.updateSelectedLanguagesInSession(selected);
        String languages[] = selected.split(",");
        TaskForm taskForm = createTestForm.getTasks().get(taskId);
        Set<ProgrammingTaskForm> programmingTaskFormSet = taskForm.getProgrammingTaskForms();
        Set<String> lang = new HashSet<>(Arrays.asList(languages));
        taskForm.setLanguages(lang);
        taskForm.setProgrammingTaskForms(testFormService.createProgrammingTaskSet(programmingTaskFormSet, languages, taskForm));

        createTestForm.getTasks().set(taskId, taskForm);
        testFormService.updateEditTestFormInSession(createTestForm);
        model.addAttribute("createTestForm", testFormService.getTestFromSession());
        return "redirect:/test/edit/" + testFormService.getEditTestIdFromSession();
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
    @RequestMapping(value = "edit/remove", method = RequestMethod.POST)
    public String removeEditQuestion(@RequestParam(value = "taskId") int taskId, final CreateTestForm createTestForm, final Model model) {
        logger.info("removeEditQuestion");
        createTestForm.getTasks().remove(taskId);
        testFormService.updateEditTestFormInSession(createTestForm);
        model.addAttribute("createTestForm", testFormService.getEditTestFromSession());
        return "redirect:/test/edit/" + testFormService.getEditTestIdFromSession();
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SUPERADMIN')")
    @RequestMapping(value = "create", method = RequestMethod.POST)
    public String save(final @Valid CreateTestForm createTestForm, final BindingResult result) {
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
        return "redirect:/test/create";
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SUPERADMIN')")
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String saveEdit(final @Valid CreateTestForm createTestForm, final BindingResult result) {
        logger.info("Metoda - save");
        createTestValidator.validate(createTestForm, result);
        if (result.hasErrors()) {
            logger.info(result.getAllErrors().toString());
            return "editTest";
        }
        testService.update(createTestForm, testFormService.getEditTestIdFromSession());
        testFormService.setEditTestIdInSession(null);
        testFormService.updateEditTestFormInSession(null);
        return "redirect:/test/all";
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SUPERADMIN')")
    @RequestMapping(value = "/delete/{id}")
    public String deleteTest(@PathVariable("id") Long id, final Model model) {
        logger.info("Metoda - deleteTest");
        testService.delete(id);
        return "redirect:/test/all";
    }


    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SUPERADMIN')")
    @RequestMapping("/all")
    public String showAll(Model model) {
        logger.info("metoda - showAll");
        try {
            model.addAttribute("Tests", testService.getAllTests());
            testFormService.removeEditTestIdInSession();
            testFormService.removeEditTestFormInSession();

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

    public void createPDF(String title,String header[], String body[][]){
        logger.info("createSamplePDF");
        Document documento = new Document();
        //Create new File
        File file = new File("D:/raport.pdf");
        try {
            file.createNewFile();
            FileOutputStream fop = new FileOutputStream(file);
            PdfWriter.getInstance(documento, fop);
            documento.open();
            //Fonts
            Font fontHeader = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
            Font fontBody = new Font(Font.FontFamily.COURIER, 12, Font.NORMAL);
            //Table for header
            PdfPTable cabetabla = new PdfPTable(header.length);
            for (int j = 0; j < header.length; j++) {
                Phrase frase = new Phrase(header[j], fontHeader);
                PdfPCell cell = new PdfPCell(frase);
                cell.setBackgroundColor(new BaseColor(Color.lightGray.getRGB()));
                cabetabla.addCell(cell);
            }
            documento.add(cabetabla);
            //Table for body
            PdfPTable tabla = new PdfPTable(header.length);
            for (int i = 0; i < body.length; i++) {
                for (int j = 0; j < body[i].length; j++) {
                    tabla.addCell(new Phrase(body[i][j], fontBody));
                }
            }
            documento.add(tabla);
            documento.addTitle(title);
            documento.addHeader("name","content");
            documento.close();
            fop.flush();
            fop.close();
        } catch (Exception e) {
            logger.info("PDF BLAD");
        }
    }


    @RequestMapping("/pdf/{id}")
    public String PDF(@PathVariable final Long id, final Model model){
        String[] header=new String[5];
        header[0]="Pozycja";
        header[1]="Osoba";
        header[2]="Wyniki testu";
        header[3]="%";
        header[4]="Data rozwiazania testu";

        Collection<SolutionTest> tests=solutionTestService.getSolutionTestsByTest(testService.getTestById(id));
        String[][] body=new String[tests.size()][5];
        int i=0;
        String title=tests.iterator().next().getTest().getName();

        logger.info("PDF SIZE:"+tests.size());
        for (SolutionTest test : tests) {
            body[i][0]=""+test.getId();
            body[i][1]=""+test.getUser().getName()+" "+test.getUser().getLastName();
            body[i][2]=""+test.getPoints()+" / "+ test.getTest().getMaxPoints();
            float procent=(test.getPoints()/test.getTest().getMaxPoints())*100;
            body[i][3]=""+procent+" %";
            body[i][4]=""+test.getEndSolution();
            i++;
        }
        createPDF(title,header,body);
        return "redirect:/test/all";
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
