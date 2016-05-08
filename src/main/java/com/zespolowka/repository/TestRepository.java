package com.zespolowka.repository;

import com.zespolowka.entity.createTest.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Collection;

@Repository
public interface TestRepository extends JpaRepository<Test, Long> {
    Test findTestById(Long id);

    Collection<Test> findByEndDateBefore(LocalDate date);

    Collection<Test> findByEndDateAfter(LocalDate date);
}
