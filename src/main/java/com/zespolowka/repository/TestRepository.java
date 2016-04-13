package com.zespolowka.repository;

import com.zespolowka.entity.createTest.Test;
import com.zespolowka.entity.solutionTest.SolutionTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface TestRepository extends JpaRepository<Test, Long> {
    Test findTestById(Long id);
}
