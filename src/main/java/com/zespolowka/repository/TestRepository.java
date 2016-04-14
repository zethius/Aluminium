package com.zespolowka.repository;

import com.zespolowka.entity.createTest.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestRepository extends JpaRepository<Test, Long> {
    Test findTestById(Long id);
}
