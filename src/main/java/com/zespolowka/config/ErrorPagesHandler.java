package com.zespolowka.config;

import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by peps on 24.04.2016.
 */
@ControllerAdvice
public class ErrorPagesHandler extends RuntimeException {

    private static final Logger logger = LoggerFactory.getLogger(ErrorPagesHandler.class);
    public static final String DEFAULT_ERROR_VIEW = "error";

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(value= HttpStatus.NOT_FOUND)  // 404
    public String pageNotFound() {
        logger.info("Page Not Found");
        return "404";
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(value= HttpStatus.UNAUTHORIZED)  // 403
    public String accessDenied () {
        return "403";
    }

    ///@ExceptionHandler() // nie moglem znalezc klasy + nie wiem jak sprawdzic ten error
    @ResponseStatus(value= HttpStatus.INTERNAL_SERVER_ERROR)  // 403
    public String internalServerError () {
        return "500";
    }



    @ExceptionHandler(value = Exception.class)
    public ModelAndView defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {
        if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null)
            throw e;
        ModelAndView mav = new ModelAndView();
        mav.addObject("exception", e);
        mav.addObject("url", req.getRequestURL());
        mav.setViewName(DEFAULT_ERROR_VIEW);
        return mav;
    }
}

