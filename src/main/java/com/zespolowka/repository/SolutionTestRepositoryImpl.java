package com.zespolowka.repository;

import com.zespolowka.entity.createTest.Test;
import com.zespolowka.entity.solutionTest.SolutionStatus;
import com.zespolowka.entity.solutionTest.SolutionTest;
import com.zespolowka.entity.user.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;


public class SolutionTestRepositoryImpl implements CustomSolutionTestRepository {
    @PersistenceContext
    private EntityManager em;

    public SolutionTestRepositoryImpl() {
    }


    @Override
    public SolutionTest getSolutionWithTheBestResult(Test test, User user) {
        String sql = "select s from SolutionTest s where s.points=(select max(g.points) from SolutionTest g where (g.test=:test and g.user=:user))";
        Query query = em.createQuery(sql).setMaxResults(1);
        query.setParameter("test", test);
        query.setParameter("user", user);
        return (SolutionTest) query.getSingleResult();
    }

    @Override
    public List<SolutionTest> getSolutionsWithTheBestResult(User user, SolutionStatus solutionStatus) {
        String sql =
                "select s " + "from SolutionTest s where s.attempt = " +
                        "(" +
                        "select min(f.attempt) " +
                        "from SolutionTest f " +
                        "where (f.user=:user and f.test=s.test and f.solutionStatus=:solutionStatus) " +
                        "and f.points = " +
                        "(" +
                        "select max(g.points) " +
                        "from SolutionTest g " +
                        "where (g.user=:user and g.test=f.test and g.solutionStatus=:solutionStatus) " +
                        "group by g.test" +
                        ") group by f.test" +
                        ") and s.user=:user and s.solutionStatus=:solutionStatus";

        Query query = em.createQuery(sql);
        query.setParameter("user", user);
        query.setParameter("solutionStatus", solutionStatus);
        return query.getResultList();
    }

    @Override
    @Transactional
    @Modifying
    public SolutionTest update(SolutionTest solutionTest, Test test) {
        solutionTest.setTest(test);
        solutionTest = em.merge(solutionTest);
        return solutionTest;
    }

    public List<Integer> getNumberOfAttempts(Test test) {
        String sql =
                "SELECT test, SUM(attempts)" +
                        "FROM SolutionTest" +
                        "where ";
        Query query = em.createQuery(sql);
        query.setParameter("test", test);
        List<Integer> numberOfAttempts = query.getResultList();

        return numberOfAttempts;

    }

}

