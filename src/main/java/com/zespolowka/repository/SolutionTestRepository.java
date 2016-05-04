package com.zespolowka.repository;


import com.zespolowka.entity.createTest.Test;
import com.zespolowka.entity.solutionTest.SolutionStatus;
import com.zespolowka.entity.solutionTest.SolutionTest;
import com.zespolowka.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface SolutionTestRepository extends JpaRepository<SolutionTest, Long>, CustomSolutionTestRepository {
    SolutionTest findSolutionTestById(Long id);

    Collection<SolutionTest> findSolutionTestsByUserAndTestAndSolutionStatus(User user, Test test, SolutionStatus solutionStatus);

    Integer countSolutionTestsByUserAndTestAndSolutionStatus(User user, Test test, SolutionStatus solutionStatus);

    Collection<SolutionTest> findSolutionTestsByUserAndSolutionStatus(User user, SolutionStatus solutionStatus);

    Collection<SolutionTest> findSolutionTestsByTestAndSolutionStatusOrderByPointsDesc(Test test, SolutionStatus solutionStatus);

    Optional<SolutionTest> findSolutionTestByTestAndUserAndSolutionStatus(Test test, User user, SolutionStatus solutionStatus);

    @Transactional
    void deleteSolutionTestsByTest(Test test);
}
