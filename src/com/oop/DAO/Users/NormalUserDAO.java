package com.oop.DAO.Users;

import com.oop.Model.Admin;
import com.oop.Model.NormalUser;
import com.oop.Model.User;
import com.oop.SQLiteConnection;


import java.sql.*;

public class NormalUserDAO implements UserDAO{
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

            sqlQuery="Insert Into normal_user(normal_user_id) values (?)";
            preparedStatement=connection.prepareStatement(sqlQuery);
            preparedStatement.setInt(1,id);
            preparedStatement.executeUpdate();


            return true;
        } catch (SQLException e) {
            return false;
        }
        //return false;
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

                sqlQuery="select * from normal_user where normal_user_id =? ";
                preparedStatement=connection.prepareStatement(sqlQuery);
                preparedStatement.setInt(1,id);
                rs=preparedStatement.executeQuery();
                if(rs.next())
                    return new NormalUser(userName,email,password);
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
}
