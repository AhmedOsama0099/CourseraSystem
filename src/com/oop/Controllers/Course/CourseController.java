package com.oop.Controllers.Course;

import com.oop.DAO.Course.CourseDAO;
import com.oop.Model.Course;

import java.util.ArrayList;

public class CourseController {
    CourseDAO courseDAO;
    public CourseController() {
        courseDAO = new CourseDAO();
    }
    public boolean insertCourse(Course course){
        return courseDAO.insertCourse(course);
    }
    public Course getCourseByCourseCode(String courseCode){
        return courseDAO.getCourseByCourseCode(courseCode);
    }
    public ArrayList<Course> getAllCourses(){
        return courseDAO.getAllCourses();
    }
    public boolean joinCourse(String courseCode,int userId){return courseDAO.joinCourse(courseCode,userId);}
    public ArrayList<Course> getAllJoinedCourses(int userId){return courseDAO.getAllJoinedCourses(userId);}
    public ArrayList<String> getCourseFiles(String courseCode){return courseDAO.getCourseFiles(courseCode);}
    public boolean updateRate(int userId,String courseCode,String rate){return courseDAO.updateRate(userId,courseCode,rate);}
}
