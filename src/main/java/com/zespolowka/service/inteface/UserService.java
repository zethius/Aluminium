package com.zespolowka.service.inteface;

import com.zespolowka.entity.user.User;
import com.zespolowka.forms.UserCreateForm;
import com.zespolowka.forms.UserEditForm;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;

/**
 * Created by Admin on 2015-12-01.
 */
public interface UserService {

    Optional<User> getUserById(long id);

    Optional<User> getUserByEmail(String email);

    Collection<User> getAllUsers();

    Collection<User> findUsersByEmailIgnoreCaseContainingOrNameIgnoreCaseContainingOrLastNameIgnoreCaseContaining(String like);

    @Transactional
    @Modifying
    User create(UserCreateForm form);

    User editUser(UserEditForm userEditForm);

    @Transactional
    @Modifying
    User update(User user);

    @Transactional
    @Modifying
    void delete(long id);
}