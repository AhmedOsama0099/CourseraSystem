package com.oop.DAO.Users;

import com.oop.Model.User;

public interface UserDAO {
    boolean insertUser(User user);
//    boolean deleteUser(User user);
    boolean updateUser(User user);
    User getUser(User user);
    int getUserID(User user);
}
