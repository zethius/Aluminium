package com.zespolowka;

import com.zespolowka.builders.UserBuilder;
import com.zespolowka.controller.UsersController;
import com.zespolowka.entity.user.User;
import com.zespolowka.service.inteface.UserService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.ui.Model;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * Created by Pitek on 2015-12-03.
 */
@ActiveProfiles("test")
public class UsersControllerTest {
    @Mock
    private UserService userService;
    @Mock
    private Model model;

    private List<User> userList;

    private UsersController usersController;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
      //  usersController = new UsersController(userService);
    }

    @Test
    public void shouldReturnUsersPage() throws Exception {
        userList = Arrays.asList(new UserBuilder("imie", "nazwisko").build(), new UserBuilder("imie2", "nazwisko2").build());
        when(userService.getAllUsers()).thenReturn(userList);
        //assertEquals("users", usersController.getUsersPage(model));
        assertEquals("users", usersController.getUsersPage(model));

        verify(model).addAttribute("Users", userList);
        verifyNoMoreInteractions(model);

    }
}
