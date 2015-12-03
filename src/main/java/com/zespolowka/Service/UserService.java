package com.zespolowka.Service;

import com.zespolowka.Entity.User;
import com.zespolowka.Entity.UserCreateForm;
import com.zespolowka.Entity.UserEditForm;

import java.util.Collection;

/**
 * Created by Admin on 2015-12-01.
 */
public interface UserService {

    User getUserById(long id);

    User getUserByEmail(String email);

    Collection<User> getAllUsers();

    User create(UserCreateForm form);

    User editUser(UserEditForm userEditForm);
}