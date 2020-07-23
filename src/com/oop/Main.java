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

    }
    static void test(User user){
        System.out.println(user.getEmail());
    }
}
