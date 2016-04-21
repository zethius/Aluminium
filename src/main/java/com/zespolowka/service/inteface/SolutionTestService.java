package com.zespolowka.service.inteface;

import com.zespolowka.entity.createTest.Test;
import com.zespolowka.entity.solutionTest.SolutionTest;
import com.zespolowka.entity.solutionTest.TaskSolution;
import com.zespolowka.entity.user.User;
import com.zespolowka.forms.CreateTestForm;
import com.zespolowka.forms.SolutionTestForm;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.Collection;

/**
 * Created by Admin on 2016-04-03.
 */
public interface SolutionTestService {
    Collection<SolutionTest> getSolutionTestsByUserAndTest(User user, Test test);

    SolutionTest getSolutionTestById(long id);

    Collection<SolutionTest> getAllTests();

    SolutionTest create(SolutionTest solutionTest);

    SolutionTestForm createForm(Test test, User user);

    String executeCommand(String command);

    void addTaskSolutionToTest(SolutionTest solutionTest, TaskSolution taskSolution) throws IOException, ParseException;

    SolutionTest create(SolutionTest solutionTest, SolutionTestForm solutionTestForm) throws IOException, ParseException;

}
