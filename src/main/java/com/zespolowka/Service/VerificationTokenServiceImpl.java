package com.zespolowka.service;

import com.zespolowka.entity.user.User;
import com.zespolowka.entity.VerificationToken;
import com.zespolowka.repository.VerificationTokenRepository;
import com.zespolowka.service.inteface.VerificationTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

/**
 * Created by Pitek on 2016-02-17.
 */
@Service
public class VerificationTokenServiceImpl implements VerificationTokenService {
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(VerificationTokenService.class);

    private final VerificationTokenRepository verificationTokenRepository;

    @Autowired
    public VerificationTokenServiceImpl(VerificationTokenRepository verificationTokenRepository) {
        this.verificationTokenRepository = verificationTokenRepository;
    }


    public Optional<VerificationToken> getVerificationTokenById(long id) {
        logger.info("Pobieranie Tokena o id = {}", id);
        return Optional.ofNullable(verificationTokenRepository.findOne(id));
    }

    public Optional<VerificationToken> getVerificationTokenByToken(String token) {
        logger.info("Pobieranie Tokena o tokenie = {}", token);
        return verificationTokenRepository.findVerificationTokenByToken(token);
    }

    public Collection<VerificationToken> getAllVerificationTokens() {
        logger.info("Pobieranie wszystkich tokenow");
        return (Collection<VerificationToken>) verificationTokenRepository.findAll();
    }

    public VerificationToken create(User user, String token) {
        VerificationToken verificationToken=new VerificationToken(token,user);
        logger.info("Stworzono token"+token);
        return verificationTokenRepository.save(verificationToken);
    }
}
