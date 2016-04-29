package com.zespolowka.repository;

import com.zespolowka.entity.VerificationToken;
import com.zespolowka.entity.user.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Created by Pitek on 2016-02-17.
 */
public interface VerificationTokenRepository extends CrudRepository<VerificationToken, Long> {
    Optional<VerificationToken> findVerificationTokenByToken(String token);

    @Transactional
    void deleteVerificationTokenByUser(User user);
}
