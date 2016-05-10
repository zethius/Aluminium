package com.zespolowka.service.inteface;

import com.zespolowka.entity.VerificationToken;
import com.zespolowka.entity.user.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface VerificationTokenService {

    Optional<VerificationToken> getVerificationTokenByToken(String token);


    @Transactional
    @Modifying
    VerificationToken create(User user, String token);

    @Transactional
    @Modifying
    void deleteVerificationTokenByUser(User user);
}