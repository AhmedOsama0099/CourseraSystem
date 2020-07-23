package com.oop;

import com.oop.DAO.Users.AdminUserDAO;
import com.oop.DAO.Users.UserDAO;
import com.oop.Model.Admin;
import com.oop.Model.User;
import javafx.util.Pair;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws IOException {
//        //test(new Admin("a","a","a"));
//        File source = new File("AttachedFiles/last-phase.zargo");
//        /*File dest = new File("AttachedFiles/last-phase.zargo");
//
//        Files.copy(source.toPath(), dest.toPath(),
//                StandardCopyOption.REPLACE_EXISTING);*/
//        System.out.println(source.getName());
        String sqlQuery = "DELETE FROM course";
        try {
            Connection connection = SQLiteConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
        }
    }
    static void test(User user){
        System.out.println(user.getEmail());
    }
}
