package com.zespolowka.repository;

import com.zespolowka.Entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * Created by Pitek on 2015-11-29.
 */
public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findUserByEmail(String email);
}
