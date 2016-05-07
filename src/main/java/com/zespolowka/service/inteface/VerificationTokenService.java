package com.zespolowka.service.inteface;

import com.zespolowka.entity.VerificationToken;
import com.zespolowka.entity.user.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;

public interface VerificationTokenService {
    Optional<VerificationToken> getVerificationTokenById(long id);

    Optional<VerificationToken> getVerificationTokenByToken(String token);

    Collection<VerificationToken> getAllVerificationTokens();

    @Transactional
    @Modifying
    VerificationToken create(User user, String token);

    @Transactional
    @Modifying
    void deleteVerificationTokenByUser(User user);
}