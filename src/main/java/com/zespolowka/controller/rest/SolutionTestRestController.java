package com.zespolowka.controller.rest;

import com.zespolowka.entity.solutionTest.SolutionTest;
import com.zespolowka.service.inteface.SolutionTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;


@RestController
@RequestMapping("/api/getTime/solutionTest")
public class SolutionTestRestController {
    @Autowired
    private SolutionTestService solutionTestService;

    @CrossOrigin
    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Integer getTotalPages(@PathVariable("id") Long id) {
        SolutionTest solutionTest = solutionTestService.getSolutionTestById(id);
        return solutionTest.secondsToEnd().intValue();
    }

}
