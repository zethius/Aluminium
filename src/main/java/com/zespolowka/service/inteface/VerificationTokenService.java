package com.zespolowka.service.inteface;

import com.zespolowka.entity.VerificationToken;
import com.zespolowka.entity.user.User;

import java.util.Collection;
import java.util.Optional;

public interface VerificationTokenService {
    Optional<VerificationToken> getVerificationTokenById(long id);

    Optional<VerificationToken> getVerificationTokenByToken(String token);

    Collection<VerificationToken> getAllVerificationTokens();

    VerificationToken create(User user, String token);

    void deleteVerificationTokenByUser(User user);
}