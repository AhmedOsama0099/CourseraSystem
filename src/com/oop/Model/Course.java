package com.oop.Model;

import java.util.ArrayList;

public class Course {
    int adminId;
    String code;
    String name;

    public Course(int adminId, String code, String name) {
        this.adminId = adminId;
        this.code = code;
        this.name = name;
    }

    public int getAdminId() {
        return adminId;
    }

    public void setAdminId(int adminId) {
        this.adminId = adminId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
