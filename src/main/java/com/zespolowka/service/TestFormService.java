package com.zespolowka.service;


import com.zespolowka.entity.createTest.ProgrammingLanguages;
import com.zespolowka.forms.CreateTestForm;
import com.zespolowka.forms.ProgrammingTaskForm;
import com.zespolowka.forms.TaskForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

@Service
public class TestFormService {
    private static final Logger logger = LoggerFactory.getLogger(TestFormService.class);
    private static final String TEST_ATTRIBUTE_NAME = "testSession";
    private static final String EDIT_TEST_ATTRIBUTE_NAME = "editTestSession";
    private static final String EDIT_TEST_ID_ATTRIBUTE_NAME = "EditTestId";
    private static final String TESTID_ATTRIBUTE_NAME = "TestId";
    private final HttpSession httpSession;

    @Autowired
    public TestFormService(final HttpSession httpSession) {
        this.httpSession = httpSession;
    }

    public CreateTestForm getTestFromSession() {
        logger.info("Metoda - getTestFromSession");
        CreateTestForm createTestForm = (CreateTestForm) this.httpSession.getAttribute(TEST_ATTRIBUTE_NAME);
        if (createTestForm == null) {
            createTestForm = new CreateTestForm();
            this.httpSession.setAttribute(TEST_ATTRIBUTE_NAME, createTestForm);
        }
        return createTestForm;
    }

    public CreateTestForm getEditTestFromSession() {
        logger.info("Metoda - getEditTestFromSession");
        CreateTestForm createTestForm = (CreateTestForm) this.httpSession.getAttribute(EDIT_TEST_ATTRIBUTE_NAME);
        if (createTestForm == null) {
            createTestForm = new CreateTestForm();
            this.httpSession.setAttribute(EDIT_TEST_ATTRIBUTE_NAME, createTestForm);
        }
        return createTestForm;
    }


    public void updateTestFormInSession(final CreateTestForm createTestForm) {
        logger.info("Metoda - updateTestFormInSession");
        this.httpSession.setAttribute(TEST_ATTRIBUTE_NAME, createTestForm);
    }

    public void updateEditTestFormInSession(final CreateTestForm createTestForm) {
        logger.info("Metoda - updateEditTestFormInSession");
        this.httpSession.setAttribute(EDIT_TEST_ATTRIBUTE_NAME, createTestForm);
    }


    public void updateSelectedLanguagesInSession(final String selected) {
        logger.info("Metoda - updateSelectedLanguagesInSession");
        this.httpSession.setAttribute("updateSelectedLanguagesInSession", selected);
    }

    public void addTaskFormToTestForm(final TaskForm taskForm) {
        logger.info("Metoda - addTaskFormToTestForm");
        final CreateTestForm createTestForm = getTestFromSession();
        createTestForm.addTask(taskForm);
        updateTestFormInSession(createTestForm);
    }

    public void addTaskFormToEditTestForm(final TaskForm taskForm) {
        final CreateTestForm createTestForm = getEditTestFromSession();
        createTestForm.addTask(taskForm);
        updateTestFormInSession(createTestForm);
    }

    public Set<ProgrammingTaskForm> createProgrammingTaskSet(Set<ProgrammingTaskForm> programmingTaskFormSet, String languages[], TaskForm taskForm) {
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
        return newProgrammingTaskFormSet;
    }

    public void setEditTestIdInSession(final Long id) {
        this.httpSession.setAttribute(EDIT_TEST_ID_ATTRIBUTE_NAME, id);
    }

    public Long getEditTestIdFromSession() {
        return (Long) this.httpSession.getAttribute(EDIT_TEST_ID_ATTRIBUTE_NAME);
    }

    public void removeEditTestIdInSession() {
        this.httpSession.removeAttribute(EDIT_TEST_ID_ATTRIBUTE_NAME);
    }

    public void removeEditTestFormInSession() {
        this.httpSession.removeAttribute(EDIT_TEST_ATTRIBUTE_NAME);
    }


    @Override
    public String toString() {
        return "TestFormService{" +
                "httpSession=" + httpSession +
                '}';
    }


}
