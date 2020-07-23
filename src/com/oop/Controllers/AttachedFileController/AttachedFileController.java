package com.oop.Controllers.AttachedFileController;

import com.oop.DAO.AttachedFile.AttachedFileDAO;

import java.io.File;

public class AttachedFileController {
    AttachedFileDAO attachedFileDAO;
    public AttachedFileController(){
        attachedFileDAO=new AttachedFileDAO();
    }
    public boolean insertFilesToCourse(File[]files, String courseCode){
        return attachedFileDAO.insertFilesToCourse(files,courseCode);
    }
}
