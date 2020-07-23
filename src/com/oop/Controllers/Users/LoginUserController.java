package com.oop.Controllers.Users;

import com.oop.DAO.Users.AdminUserDAO;
import com.oop.DAO.Users.NormalUserDAO;
import com.oop.Model.Admin;
import com.oop.Model.NormalUser;
import com.oop.Model.User;

public class LoginUserController {
    AdminUserController adminUserController;
    NormalUserController normalUserController;
    public LoginUserController(){
        adminUserController=new AdminUserController();
        normalUserController=new NormalUserController();
    }
    public User login(User user){
        User adminUser=adminUserController.getUser(user);
        User normalUser=normalUserController.getUser(user);
        if(adminUser instanceof Admin){
            if(user.password.equals(adminUser.password))
                return adminUser;
        }
        else if(normalUser instanceof NormalUser){
            if(user.password.equals(normalUser.password))
                return normalUser;
        }
        return null;
    }
}
