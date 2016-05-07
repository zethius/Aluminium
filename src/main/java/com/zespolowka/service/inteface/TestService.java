package com.zespolowka.service.inteface;

import com.zespolowka.entity.createTest.Test;
import com.zespolowka.forms.CreateTestForm;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.time.LocalDate;
import java.util.Collection;

/**
 * Created by Admin on 2016-03-23.
 */
public interface TestService {
    Test getTestById(long id);

    Collection<Test> getAllTests();

    @Transactional
    @Modifying
    Test create(CreateTestForm form);

    @Transactional
    @Modifying
    Test update(CreateTestForm form, Long id);

    @Transactional
    @Modifying
    void delete(Long id);

    @Transactional
    @Modifying
    Test update(Test test);

    CreateTestForm createForm(Test test);

    Collection<Test> getTestByEndDateBefore(LocalDate date);

    void createPDF(File file, String title, String header[], String body[][]);
}
