package com.zespolowka.service.inteface;

import com.zespolowka.entity.createTest.Test;
import com.zespolowka.forms.CreateTestForm;

import java.time.LocalDate;
import java.util.Collection;

/**
 * Created by Admin on 2016-03-23.
 */
public interface TestService {
    Test getTestById(long id);

    Collection<Test> getAllTests();

    Test create(CreateTestForm form);

    Test update(CreateTestForm form, Long id);

    void delete(Long id);

    Test update(Test test);

    CreateTestForm createForm(Test test);

    Collection<Test> getTestByEndDateBefore(LocalDate date);
}
