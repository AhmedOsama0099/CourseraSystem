package com.oop.Controllers.Users;

import com.oop.Model.User;

public interface UserController {
    User getUser(User user);
    boolean insertUser(User user);
    int getUserID(User user);
}
