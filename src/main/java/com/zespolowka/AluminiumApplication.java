package com.zespolowka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan
@EnableCaching
public class AluminiumApplication {
    private static final Logger logger = LoggerFactory.getLogger(AluminiumApplication.class);

    public AluminiumApplication() {
    }

    public static void main(String... args) {
        SpringApplication.run(AluminiumApplication.class, args);
        logger.info("Aplikacja uruchomiona");
    }
}
