package com.zespolowka.service.inteface;

import com.zespolowka.entity.user.CurrentUser;

/**
 * Created by Admin on 2015-12-12.
 */
public interface CurrentUserService {
    boolean canAccessUser(CurrentUser currentUser, Long userId);

    boolean isVerifiedUser(CurrentUser currentUser);
}
