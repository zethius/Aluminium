package com.zespolowka.forms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ChangePasswordForm {

    private static final Logger logger = LoggerFactory.getLogger(ChangePasswordForm.class);

    private Long id;

    @NotNull
    @Size(min = 8, max = 25)
    private String password;

    @NotNull
    @Size(min = 8, max = 25)
    private String confirmPassword;

<<<<<<< HEAD
    public ChangePasswordForm() {
    }

=======
>>>>>>> addf63146eadb4865c3e88fc9502c025b3871c1e
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
<<<<<<< HEAD

    @Override
    public String toString() {
        return "ChangePasswordForm{" +
                "id=" + id +
                ", password='" + password + '\'' +
                ", confirmPassword='" + confirmPassword + '\'' +
                '}';
    }
=======
>>>>>>> addf63146eadb4865c3e88fc9502c025b3871c1e
}
