package com.zespolowka.service;


import com.zespolowka.forms.CreateTestForm;
import com.zespolowka.forms.TaskForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service
public class TestFormService {
    private static final Logger logger = LoggerFactory.getLogger(TestFormService.class);
    private static final String TEST_ATTRIBUTE_NAME = "testSession";
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

    public void updateTestFormInSession(final CreateTestForm createTestForm) {
        logger.info("Metoda - updateTestFormInSession");
        this.httpSession.setAttribute(TEST_ATTRIBUTE_NAME, createTestForm);
    }

    public void addTaskFormToTestForm(final TaskForm taskForm) {
        logger.info("Metoda - addTaskFormToTestForm");
        final CreateTestForm createTestForm = getTestFromSession();
        createTestForm.addTask(taskForm);
        updateTestFormInSession(createTestForm);
    }

}
