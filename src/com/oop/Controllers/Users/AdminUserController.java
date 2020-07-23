package com.oop.Controllers.Users;

import com.oop.DAO.Course.CourseDAO;
import com.oop.DAO.Users.AdminUserDAO;
import com.oop.Model.User;
import com.oop.View.RateView;
import javafx.util.Pair;

import java.util.ArrayList;

public class AdminUserController implements UserController {
    AdminUserDAO adminUserDAO;
    CourseDAO courseDAO;
    public AdminUserController() {
        adminUserDAO = new AdminUserDAO();
        courseDAO=new CourseDAO();
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
    public ArrayList<RateView>getNumberOfAllCoursesRateNumber(){return adminUserDAO.getNumberOfAllCoursesRateNumber();}
    public boolean deleteCourse(String courseCode){return courseDAO.deleteCourse(courseCode);}
}
