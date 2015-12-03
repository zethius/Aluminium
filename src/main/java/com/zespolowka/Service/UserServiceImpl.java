package com.zespolowka.Service;

import com.zespolowka.Entity.User;
import com.zespolowka.Entity.UserCreateForm;
import com.zespolowka.Entity.UserEditForm;
import com.zespolowka.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * Created by Admin on 2015-12-01.
 */
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User getUserById(long id) {
        return userRepository.findOne(id);
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    @Override
    public Collection<User> getAllUsers() {
        return (Collection<User>) userRepository.findAll();
    }

    @Override
    /**
     * Tworzy nowego uzytkownika przez formularz do rejestacji
     */
    public User create(UserCreateForm form) {
        User user = new User();
        user.setName(form.getName());
        user.setLastName(form.getLastName());
        user.setEmail(form.getEmail());
        //user.setPasswordHash(new BCryptPasswordEncoder().encode(form.getPassword()));
        user.setRole(form.getRole());
        return userRepository.save(user);
    }

    /**
     * Edytuje uzytkownika
     */
    public User editUser(UserEditForm userEditForm) {
        User user = getUserById(userEditForm.getId());
        user.setName(userEditForm.getName());
        user.setLastName(userEditForm.getLastName());
        user.setEmail(userEditForm.getEmail());
        user.setRole(userEditForm.getRole());
        return userRepository.save(user);
    }

}
