package com.zespolowka.repository;


import com.zespolowka.entity.createTest.Test;
import com.zespolowka.entity.solutionTest.SolutionTest;
import com.zespolowka.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface SolutionTestRepository extends JpaRepository<SolutionTest, Long> {
    SolutionTest findSolutionTestById(Long id);

    Collection<SolutionTest> findSolutionTestsByUserAndTest(User user, Test test);
}
