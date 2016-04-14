package com.zespolowka.entity.user;

import javax.persistence.*;

/**
 * Created by Pitek on 2015-11-29.
 */
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue
    private Long id;

    private boolean enabled;

    private String name;

    private String lastName;

    private String email;

    private String passwordHash;

    private int login_tries;

    private boolean accountNonLocked = true;

    @Enumerated(EnumType.STRING)
    private Role role;

    public User() {
        super();
        this.enabled = false;
        this.login_tries = 3;
    }

    public User(String name, String lastName, String email, String passwordHash) {
        super();
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.passwordHash = passwordHash;
        this.role = Role.USER;
        this.enabled = false;
        this.login_tries = 3;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public int getLogin_tries() {
        return login_tries;
    }

    public void setLogin_tries(int login_tries) {
        this.login_tries = login_tries;
    }

    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    public void setAccountNonLocked(boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", enabled=" + enabled +
                ", name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", passwordHash='" + passwordHash + '\'' +
                ", login_tries=" + login_tries +
                ", accountNonLocked=" + accountNonLocked +
                ", role=" + role +
                '}';
    }
}


