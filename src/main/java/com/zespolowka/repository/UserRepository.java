package com.zespolowka.repository;

import com.zespolowka.Entity.User;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Admin on 2015-11-29.
 */
public interface UserRepository extends CrudRepository<User, Long> {
    User findUserByEmail(String email);
}
