package com.zespolowka;

import com.zespolowka.Service.UserService;
import com.zespolowka.builders.UserCreateFormBuilder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

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
                .param("confirmPassword", "zaq1@WSX"))
                .andExpect(redirectedUrl("/user/5"));
    }

    @Test
    public void should_failed_registration() throws Exception {
        mvc.perform(post("/register"))
                .andExpect(status().isOk())
                .andExpect(view().name("register"))
                .andExpect(model().errorCount(5));
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
        userService.create(new UserCreateFormBuilder("adam", "malysz").withEmail("aaaaa@o2.pl").build());
        mvc.perform(post("/register")
                .param("name", "adam")
                .param("lastName", "malysz")
                .param("email", "aaaaa@o2.pl")
                .param("password", "zaq1@WSX")
                .param("confirmPassword", "zaq1@WSX"))
                .andExpect(status().isOk())
                .andExpect(view().name("register"))
                .andExpect(model().errorCount(1));
    }

}
