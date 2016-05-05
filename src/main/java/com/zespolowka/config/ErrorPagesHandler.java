package com.zespolowka.config;

import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;


@ControllerAdvice
public class ErrorPagesHandler extends RuntimeException {

    public static final String DEFAULT_ERROR_VIEW = "error";
    private static final Logger logger = LoggerFactory.getLogger(ErrorPagesHandler.class);

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)  // 404
    public String pageNotFound() {
        logger.info("Page Not Found");
        return "404";
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)  // 403
    public String accessDenied() {
        return "403";
    }

    ///@ExceptionHandler() // nie moglem znalezc klasy + nie wiem jak sprawdzic ten error
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)  // 500
    public String internalServerError() {
        return "500";
    }



}