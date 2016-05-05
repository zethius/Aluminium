package com.zespolowka.service.inteface;

import com.zespolowka.entity.createTest.Test;
import com.zespolowka.entity.solutionTest.SolutionStatus;
import com.zespolowka.entity.solutionTest.SolutionTest;
import com.zespolowka.entity.solutionTest.TaskSolution;
import com.zespolowka.entity.user.User;
import com.zespolowka.forms.SolutionTestForm;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * Created by Admin on 2016-04-03.
 */
public interface SolutionTestService {
    Collection<SolutionTest> getSolutionTestsByUserAndTest(User user, Test test);

    Integer countSolutionTestsByUserAndTest(User user, Test test);

    SolutionTest getSolutionTestById(long id);

    Collection<SolutionTest> getAllTests();

    SolutionTest create(SolutionTest solutionTest, SolutionStatus solutionStatus);

    SolutionTestForm createForm(Test test, User user);

    String executeCommand(String command);

    void addTaskSolutionToTest(SolutionTest solutionTest, TaskSolution taskSolution) throws IOException, ParseException;

    SolutionTest create(SolutionTest solutionTest, SolutionTestForm solutionTestForm) throws IOException, ParseException;

    Collection<SolutionTest> getSolutionTestsByUser(User user);

    List<SolutionTest> getSolutionsWithTheBestResult(User user);

    Collection<SolutionTest> getSolutionTestsByTest(Test test);

    Optional<SolutionTest> findSolutionTestByTestAndUserAndSolutionStatus(Test test, User user, SolutionStatus solutionStatus);

    SolutionTestForm createFormWithExistingSolution(SolutionTest solutionTest);

    Integer countSolutionTestsByTestAndSolutionStatus(Test test, SolutionStatus solutionStatus);
}
