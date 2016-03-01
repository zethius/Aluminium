package com.zespolowka;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = AluminiumApplication.class)
@WebAppConfiguration
@IntegrationTest
@ActiveProfiles("test")
public class AluminiumApplicationTests {

    @Test
    public void contextLoads() {
    }

}
