package com.zespolowka.Service;

import com.zespolowka.Entity.User;
import com.zespolowka.Entity.VerificationToken;

import java.util.Collection;
import java.util.Optional;

/**
 * Created by Admin on 2016-02-17.
 */
public interface VerificationTokenService {
    Optional<VerificationToken> getVerificationTokenById(long id);

    Optional<VerificationToken> getVerificationTokenByToken(String token);

    Collection<VerificationToken> getAllVerificationTokens();

    VerificationToken create(User user, String token);
}
