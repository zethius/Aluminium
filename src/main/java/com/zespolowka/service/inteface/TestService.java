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

    Collection<Test> getTestByEndDateBefore(LocalDate date);
}
