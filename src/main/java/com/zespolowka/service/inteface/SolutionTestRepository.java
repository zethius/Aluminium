package com.zespolowka.service.inteface;


import com.zespolowka.entity.solutionTest.SolutionTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SolutionTestRepository extends JpaRepository<SolutionTest, Long> {
}
