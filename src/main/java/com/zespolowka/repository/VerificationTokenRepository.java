package com.zespolowka.repository;

import com.zespolowka.Entity.VerificationToken;
import org.springframework.data.repository.CrudRepository;
import java.util.Optional;

/**
 * Created by Pitek on 2016-02-17.
 */
public interface VerificationTokenRepository extends CrudRepository<VerificationToken, Long> {
    Optional<VerificationToken> findVerificationTokenByToken(String token);
}
