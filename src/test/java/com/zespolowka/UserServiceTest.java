package com.zespolowka;

import com.zespolowka.entity.user.User;
import com.zespolowka.service.inteface.UserService;
import com.zespolowka.service.UserServiceImpl;
import com.zespolowka.builders.UserBuilder;
import com.zespolowka.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * Created by Pitek on 2015-12-03.
 */
@ActiveProfiles("test")
public class UserServiceTest {

    @Mock
    UserRepository userRepository;

    private UserService userService;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        userService = new UserServiceImpl(userRepository);
    }

    @Test
    public void getAllUserShouldReturnAllUsers() throws Exception {


        List<User> users = Arrays.asList(new UserBuilder("Imie", "Nazwisko").build(), new UserBuilder("Imie2", "Nazwisko2").build());
        when(userService.getAllUsers()).thenReturn(users);

        List<User> expectedUsers = Arrays.asList(new UserBuilder("Imie", "Nazwisko").build(), new UserBuilder("Imie2", "Nazwisko2").build());
        assertThat(users.get(0).getName()).isEqualTo(expectedUsers.get(0).getName());
        assertThat(users.get(0).getLastName()).isEqualTo(expectedUsers.get(0).getLastName());
        assertThat(users.get(0).getEmail()).isEqualTo(expectedUsers.get(0).getEmail());
        assertThat(users.get(0).getPasswordHash()).isEqualTo(expectedUsers.get(0).getPasswordHash());
        assertThat(users.get(0).getRole()).isEqualTo(expectedUsers.get(0).getRole());

        assertThat(users.get(1).getName()).isEqualTo(expectedUsers.get(1).getName());
        assertThat(users.get(1).getLastName()).isEqualTo(expectedUsers.get(1).getLastName());
        assertThat(users.get(1).getEmail()).isEqualTo(expectedUsers.get(1).getEmail());
        assertThat(users.get(1).getPasswordHash()).isEqualTo(expectedUsers.get(1).getPasswordHash());
        assertThat(users.get(1).getRole()).isEqualTo(expectedUsers.get(1).getRole());
    }

    @Test
    public void getUserByEmailShouldReturnUser() throws Exception {
        String email = "test@o2.pl";
        User user = new UserBuilder("imie", "nazwisko").withEmail(email).build();
        when(userService.getUserByEmail(email))
                .thenReturn(Optional.of(user));

        User expectedUser = new UserBuilder("imie", "nazwisko").withEmail(email).build();
        assertThat(user.getEmail()).isEqualTo(expectedUser.getEmail());
    }

}
