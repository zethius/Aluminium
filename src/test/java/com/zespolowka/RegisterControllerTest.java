package com.zespolowka;

import com.zespolowka.service.inteface.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by Pitek on 2015-11-30.
 */
@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = AluminiumApplication.class)
@WebAppConfiguration
public class RegisterControllerTest {
    private static final Logger logger = LoggerFactory.getLogger(RegisterControllerTest.class);

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private UserService userService;

    private MockMvc mvc;


    @Before
    public void setUp() throws Exception {
        this.mvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }


    @Test
    public void shoud_show_register_page() throws Exception {
        mvc.perform(get("/register"))
                .andExpect(status().isOk())
                .andExpect(view().name("register"));
    }

    @Test
    public void should_process_registration() throws Exception {
        mvc.perform(post("/register")
                .param("name", "adam")
                .param("lastName", "malysz")
                .param("email", "a1@o2.pl")
                .param("password", "zaq1@WSX")
                .param("confirmPassword", "zaq1@WSX")
                .with(csrf()))
                .andExpect(model().errorCount(0));
    }

    @Test
    public void should_failed_registration() throws Exception {
        mvc.perform(post("/register"))
                .andExpect(status().isOk())
                .andExpect(view().name("register"))
                .andExpect(model().errorCount(6));
    }

    @Test
    public void should_failed_password_confirmation() throws Exception {
        mvc.perform(post("/register")
                .param("name", "adam")
                .param("lastName", "malysz")
                .param("email", "a2@o2.pl")
                .param("password", "zaq1@WSX")
                .param("confirmPassword", "zaq1@WSXa"))
                .andExpect(status().isOk())
                .andExpect(view().name("register"))
                .andExpect(model().errorCount(1));
    }

    @Test
    public void should_failed_with_easy_password() throws Exception {
        mvc.perform(post("/register")
                .param("name", "adam")
                .param("lastName", "malysz")
                .param("email", "a3@o2.pl")
                .param("password", "11111111")
                .param("confirmPassword", "11111111"))
                .andExpect(status().isOk())
                .andExpect(view().name("register"))
                .andExpect(model().errorCount(1));
    }

    @Test
    public void shoud_failed_with_existed_email() throws Exception {
        logger.info(userService.getAllUsers().toString());
        mvc.perform(post("/register")
                .param("name", "adam")
                .param("lastName", "malysz")
                .param("email", "aaa3@o2.pl")
                .param("password", "zaq1@WSX")
                .param("confirmPassword", "zaq1@WSX"))
                .andExpect(status().isOk())
                .andExpect(view().name("register"))
                .andExpect(model().errorCount(1));
    }

}
