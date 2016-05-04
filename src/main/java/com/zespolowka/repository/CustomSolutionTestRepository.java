package com.zespolowka.repository;

import com.zespolowka.entity.createTest.Test;
import com.zespolowka.entity.solutionTest.SolutionStatus;
import com.zespolowka.entity.solutionTest.SolutionTest;
import com.zespolowka.entity.user.User;

import java.util.List;

public interface CustomSolutionTestRepository {
    SolutionTest getSolutionWithTheBestResult(Test test, User user);

    List<SolutionTest> getSolutionsWithTheBestResult(User user, SolutionStatus solutionStatus);
}
