package com.oop.DAO.Users;

import com.oop.DAO.Course.CourseDAO;
import com.oop.Model.Admin;
import com.oop.Model.Course;
import com.oop.Model.User;
import com.oop.SQLiteConnection;
import javafx.util.Pair;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AdminUserDAO implements  UserDAO{
    @Override
    public boolean insertUser(User user) {

        String sqlQuery="Insert Into user(UserName,Email,Password) values (?,?,?)";
        try {
            Connection connection= SQLiteConnection.getConnection();
            PreparedStatement preparedStatement=connection.prepareStatement(sqlQuery);
            preparedStatement.setString(1,user.getUserName());
            preparedStatement.setString(2,user.getEmail());
            preparedStatement.setString(3,user.getPassword());
            preparedStatement.executeUpdate();

            sqlQuery="select * from user where UserName =? ";
            preparedStatement=connection.prepareStatement(sqlQuery);
            preparedStatement.setString(1,user.getUserName());
            ResultSet rs=preparedStatement.executeQuery();
            rs.next();
            int id=rs.getInt("id");

            sqlQuery="Insert Into admin_user(admin_id) values (?)";
            preparedStatement=connection.prepareStatement(sqlQuery);
            preparedStatement.setInt(1,id);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            //e.printStackTrace();
            return false;
        }
    }

    public boolean deleteUser(User user) {

        return false;
    }

    @Override
    public boolean updateUser(User user) {

        return false;
    }

    @Override
    public User getUser(User user) {
        String sqlQuery="select * from user where UserName =? ";

        try {
            Connection connection= SQLiteConnection.getConnection();
            PreparedStatement preparedStatement=connection.prepareStatement(sqlQuery);
            preparedStatement.setString(1,user.getUserName());
            ResultSet rs=preparedStatement.executeQuery();
            if(rs.next()){
                String userName=rs.getString("UserName");
                String email=rs.getString("Email");
                String password=rs.getString("Password");
                int id=rs.getInt("id");

                sqlQuery="select * from admin_user where admin_id =? ";
                preparedStatement=connection.prepareStatement(sqlQuery);
                preparedStatement.setInt(1,id);
                rs=preparedStatement.executeQuery();
                if(rs.next())
                    return new Admin(userName,email,password);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int getUserID(User user) {
        String sqlQuery="select id from user where UserName =? ";

        try {
            Connection connection= SQLiteConnection.getConnection();
            PreparedStatement preparedStatement=connection.prepareStatement(sqlQuery);
            preparedStatement.setString(1,user.getUserName());
            ResultSet rs=preparedStatement.executeQuery();
            if(rs.next()){
                return rs.getInt("id");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
    public ArrayList<Pair<String,Integer>>getNumberOfUsersJoinedInAllCourses(){
        ArrayList<Pair<String,Integer>>chart=new ArrayList<>();
        CourseDAO courseDAO=new CourseDAO();
        ArrayList<Course>courses=courseDAO.getAllCourses();
        Connection connection= SQLiteConnection.getConnection();
        String sqlQuery;
        for(Course course:courses){
            sqlQuery="SELECT count(*) from joined_courses where course_code='"+course.getCode()+"'";
            try {

                PreparedStatement preparedStatement=connection.prepareStatement(sqlQuery);
                ResultSet rs=preparedStatement.executeQuery();
                if(rs.next())
                    chart.add(new Pair<>(course.getCode(),rs.getInt(1)));

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }


        return chart;
    }
}
