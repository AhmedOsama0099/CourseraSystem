package com.oop.DAO.Course;

import com.oop.Model.Course;
import com.oop.Model.User;
import com.oop.SQLiteConnection;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CourseDAO {
    public boolean insertCourse(Course course) {
        String sqlQuery = "Insert Into course(admin_id,code,name) values (?,?,?)";
        try {
            Connection connection = SQLiteConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setInt(1, course.getAdminId());
            preparedStatement.setString(2, course.getCode());
            preparedStatement.setString(3, course.getName());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean joinCourse(String courseCode,int userId) {
        String sqlQuery="select * from joined_courses where course_code=? and user_id=? ";

        try {
            Connection connection= SQLiteConnection.getConnection();
            PreparedStatement preparedStatement=connection.prepareStatement(sqlQuery);
            preparedStatement.setString(1,courseCode);
            preparedStatement.setInt(2,userId);
            ResultSet rs=preparedStatement.executeQuery();
            if(rs.next()){
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        sqlQuery = "Insert Into joined_courses(course_code,user_id) values (?,?)";
        try {
            Connection connection = SQLiteConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setString(1, courseCode);
            preparedStatement.setInt(2, userId);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public Course getCourseByCourseCode(String courseCode) {
        String sqlQuery="select * from course where code =? ";

        try {
            Connection connection= SQLiteConnection.getConnection();
            PreparedStatement preparedStatement=connection.prepareStatement(sqlQuery);
            preparedStatement.setString(1,courseCode);
            ResultSet rs=preparedStatement.executeQuery();
            if(rs.next()){
                return new Course(rs.getInt("admin_id"),rs.getString("code"),rs.getString("name"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<Course> getAllCourses() {
        ArrayList<Course>courses=new ArrayList<>();
        String sqlQuery="select * from course";
        try {
            Connection connection= SQLiteConnection.getConnection();
            PreparedStatement preparedStatement=connection.prepareStatement(sqlQuery);
            ResultSet rs=preparedStatement.executeQuery();
            while (rs.next()){
                courses.add(new Course(rs.getInt("admin_id"),rs.getString("code"),rs.getString("name")));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return courses;
    }

    public ArrayList<Course> getAllJoinedCourses(int userId) {
        ArrayList<Course>courses=new ArrayList<>();
        String sqlQuery="SELECT * \n" +
                "FROM course \n" +
                "INNER JOIN joined_courses \n" +
                "ON course.code = joined_courses.course_code and joined_courses.user_id="+userId+";";
        try {
            Connection connection= SQLiteConnection.getConnection();
            PreparedStatement preparedStatement=connection.prepareStatement(sqlQuery);
            ResultSet rs=preparedStatement.executeQuery();
            while (rs.next()){
                courses.add(new Course(rs.getInt("admin_id"),rs.getString("code"),rs.getString("name")));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return courses;
    }
    public ArrayList<String> getCourseFiles(String courseCode) {
        ArrayList<String>files=new ArrayList<>();
        String sqlQuery="select * from attached_files where course_code='"+courseCode+"'";
        try {
            Connection connection= SQLiteConnection.getConnection();
            PreparedStatement preparedStatement=connection.prepareStatement(sqlQuery);
            ResultSet rs=preparedStatement.executeQuery();
            File f;
            while (rs.next()){
                f=new File(rs.getString("full_path"));
                files.add(f.getName());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return files;
    }
    public boolean updateRate(int userId,String courseCode,String rate){
        String sqlQuery="UPDATE joined_courses SET course_rate = ? WHERE user_id = ? and course_code=?";
        try {
            Connection connection = SQLiteConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setString(1, rate);
            preparedStatement.setInt(2, userId);
            preparedStatement.setString(3, courseCode);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
    public boolean deleteCourse(String courseCode){

        String sqlQuery="select * from course where code='"+courseCode+"'";
        try {
            Connection connection= SQLiteConnection.getConnection();
            PreparedStatement preparedStatement=connection.prepareStatement(sqlQuery);
            ResultSet rs=preparedStatement.executeQuery();
            if (!rs.next()){
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        sqlQuery = "DELETE FROM course WHERE code='"+courseCode+"'";
        try {
            Connection connection = SQLiteConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

}
