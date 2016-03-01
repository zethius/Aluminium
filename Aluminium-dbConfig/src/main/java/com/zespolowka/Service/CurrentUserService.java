package com.zespolowka.Service;

import com.zespolowka.Entity.CurrentUser;

/**
 * Created by Admin on 2015-12-12.
 */
public interface CurrentUserService {
    boolean canAccessUser(CurrentUser currentUser, Long userId);
}
