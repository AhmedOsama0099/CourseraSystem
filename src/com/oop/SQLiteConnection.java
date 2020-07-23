package com.oop;

import java.io.File;
import java.sql.*;

public class SQLiteConnection {
    public static Connection myCon = null;
    public static String sqlLiteServer = "jdbc:sqlite:";

    public static boolean isDataBaseExists(String dbFilePath){
        File dpFile=new File(dbFilePath);
        return  dpFile.exists();
    }
    public static Connection getConnection(){
        String dbPath="CourseraSystem.db";
        if(isDataBaseExists(dbPath)){
            try {
                if(myCon==null){
                    myCon=DriverManager.getConnection(sqlLiteServer+dbPath);
                    System.out.println("Connection has been established!");

                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        else{
            //createNewDataBase();
        }
        return myCon;
    }
}
