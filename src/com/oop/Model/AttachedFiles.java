package com.oop.Model;

public class AttachedFiles {
    int id;
    String courseCode;
    String fullPath;

    public AttachedFiles(int id, String courseCode, String fullPath) {
        this.id = id;
        this.courseCode = courseCode;
        this.fullPath = fullPath;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getFullPath() {
        return fullPath;
    }

    public void setFullPath(String fullPath) {
        this.fullPath = fullPath;
    }
}
