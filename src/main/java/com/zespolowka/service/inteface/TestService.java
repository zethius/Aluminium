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

<<<<<<< HEAD
    CreateTestForm createForm(Test test);

=======
>>>>>>> addf63146eadb4865c3e88fc9502c025b3871c1e
    Collection<Test> getTestByEndDateBefore(LocalDate date);
}
