package com.zespolowka.repository;


import com.zespolowka.entity.createTest.Test;
import com.zespolowka.entity.solutionTest.SolutionTest;
import com.zespolowka.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
<<<<<<< HEAD
public interface SolutionTestRepository extends JpaRepository<SolutionTest, Long>, CustomSolutionTestRepository {
=======
public interface SolutionTestRepository extends JpaRepository<SolutionTest, Long> {
>>>>>>> addf63146eadb4865c3e88fc9502c025b3871c1e
    SolutionTest findSolutionTestById(Long id);

    Collection<SolutionTest> findSolutionTestsByUserAndTest(User user, Test test);

    Collection<SolutionTest> findSolutionTestsByUser(User user);
}
