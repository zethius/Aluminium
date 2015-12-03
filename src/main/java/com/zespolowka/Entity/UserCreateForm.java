package com.zespolowka.Entity;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by Pitek on 2015-11-29.
 */
public class UserCreateForm {

    @NotNull
    @Size(min = 4, max = 15)
    private String name;

    @NotNull
    @Size(min = 4, max = 20)
    private String lastName;

    @Email
    @NotBlank
    private String email;

    @NotNull
    @Size(min = 8, max = 25)
    private String password;

    @NotNull
    @Size(min = 8, max = 25)
    private String confirmPassword;

    private Role role = Role.USER;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
