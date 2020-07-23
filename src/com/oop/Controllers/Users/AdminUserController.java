package com.oop.Controllers.Users;

import com.oop.DAO.Users.AdminUserDAO;
import com.oop.Model.User;
import javafx.util.Pair;

import java.util.ArrayList;

public class AdminUserController implements UserController {
    AdminUserDAO adminUserDAO;

    public AdminUserController() {
        adminUserDAO = new AdminUserDAO();
    }

    @Override
    public User getUser(User user) {
        return adminUserDAO.getUser(user);
    }

    @Override
    public boolean insertUser(User user) {
        return adminUserDAO.insertUser(user);
    }

    @Override
    public int getUserID(User user) {
        return adminUserDAO.getUserID(user);
    }

    public ArrayList<Pair<String, Integer>> getNumberOfUsersJoinedInAllCourses() {
        return adminUserDAO.getNumberOfUsersJoinedInAllCourses();
    }
}
