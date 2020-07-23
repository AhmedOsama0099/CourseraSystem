package com.oop.Controllers.Users;

import com.oop.DAO.Users.NormalUserDAO;
import com.oop.Model.User;

public class NormalUserController implements UserController {
    NormalUserDAO normalUserDAO;
    public NormalUserController(){
        normalUserDAO=new NormalUserDAO();
    }
    @Override
    public User getUser(User user) {
        return normalUserDAO.getUser(user);
    }

    @Override
    public boolean insertUser(User user) {
        return normalUserDAO.insertUser(user);
    }

    @Override
    public int getUserID(User user) {
        return normalUserDAO.getUserID(user);
    }
}
